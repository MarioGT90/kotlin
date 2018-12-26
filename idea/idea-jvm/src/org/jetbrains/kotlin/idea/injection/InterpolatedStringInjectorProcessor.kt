/*
 * Copyright 2010-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.idea.injection

import com.intellij.openapi.util.TextRange
import com.intellij.openapi.util.Trinity
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLanguageInjectionHost
import org.intellij.plugins.intelliLang.inject.InjectedLanguage
import org.intellij.plugins.intelliLang.inject.InjectorUtils
import org.intellij.plugins.intelliLang.inject.config.BaseInjection
import org.jetbrains.kotlin.idea.caches.resolve.analyze
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator
import org.jetbrains.kotlin.types.TypeUtils
import org.jetbrains.kotlin.utils.addToStdlib.safeAs
import java.util.*

data class InjectionSplitResult(val isUnparsable: Boolean, val ranges: List<Trinity<PsiLanguageInjectionHost, InjectedLanguage, TextRange>>)

fun splitLiteralToInjectionParts(injection: BaseInjection, literal: KtStringTemplateExpression): InjectionSplitResult? {
    InjectorUtils.getLanguage(injection) ?: return null

    val children = literal.children.toList()

    if (children.isEmpty()) return null

    val result = ArrayList<Trinity<PsiLanguageInjectionHost, InjectedLanguage, TextRange>>()

    fun addInjectionRange(range: TextRange, prefix: String, suffix: String) {
        TextRange.assertProperRange(range, injection)
        val injectedLanguage = InjectedLanguage.create(injection.injectedLanguageId, prefix, suffix, true)!!
        result.add(Trinity.create(literal, injectedLanguage, range))
    }

    tailrec fun walkChildren(children: List<PsiElement>, pendingPrefix: String, unparseble: Boolean): Boolean {
        val child = children.firstOrNull() ?: return unparseble
        val tail = children.subList(1, children.size)
        val partOffsetInParent = child.startOffsetInParent

        val suffix = if (tail.isEmpty()) injection.suffix else ""


        when (child) {
            is KtLiteralStringTemplateEntry, is KtEscapeStringTemplateEntry -> {
                val consequentStringsCount = tail.asSequence()
                    .takeWhile { it is KtLiteralStringTemplateEntry || it is KtEscapeStringTemplateEntry }
                    .count()

                val lastChild = children[consequentStringsCount]
                val remainingAfter = tail.subList(consequentStringsCount, tail.size)

                addInjectionRange(
                    TextRange.create(partOffsetInParent, lastChild.startOffsetInParent + lastChild.textLength),
                    pendingPrefix,
                    if (remainingAfter.isEmpty()) injection.suffix else suffix
                )

                return walkChildren(remainingAfter, "", unparseble)
            }

            is KtSimpleNameStringTemplateEntry, is KtBlockStringTemplateEntry -> {
                if (!pendingPrefix.isEmpty()) {
                    // Store part with prefix before replacing it
                    addInjectionRange(TextRange.from(partOffsetInParent, 0), pendingPrefix, suffix)
                }

                val (prefix, myUnparsable) = when (child) {
                    is KtSimpleNameStringTemplateEntry ->
                        tryEvaluateConstant(child.expression)?.let { it to true } ?: ((child.expression?.text ?: NO_VALUE_NAME) to false)
                    is KtBlockStringTemplateEntry ->
                        tryEvaluateConstant(child.expression)?.let { it to true } ?: (NO_VALUE_NAME to false)
                    else ->
                        error("Child type should be KtSimpleNameStringTemplateEntry or KtBlockStringTemplateEntry")
                }

                if (tail.isEmpty() && !prefix.isEmpty()) {
                    // There won't be more elements, so create part with prefix right away
                    addInjectionRange(TextRange.from(partOffsetInParent + child.textLength, 0), prefix, suffix)
                }
                return walkChildren(tail, prefix, unparseble || myUnparsable)
            }

            else -> {
                addInjectionRange(TextRange.create(partOffsetInParent, child.startOffsetInParent + child.textLength), pendingPrefix, suffix)
                return walkChildren(tail, "", true)
            }
        }

    }


    val firstChild = children.firstOrNull()
    val unparsable = if (firstChild is KtSimpleNameStringTemplateEntry || firstChild is KtBlockStringTemplateEntry) {

        // Store part with prefix before replacing it
        addInjectionRange(
            TextRange.from(firstChild.startOffsetInParent, 0),
            injection.prefix,
            if (children.size == 1) injection.suffix else ""
        )

        walkChildren(children, "", false)
    } else
        walkChildren(children, injection.prefix, false)

    return InjectionSplitResult(unparsable, result)
}

private fun tryEvaluateConstant(ktExpression: KtExpression?) =
    ktExpression?.let { expression ->
        ConstantExpressionEvaluator.getConstant(expression, expression.analyze())
            ?.takeUnless { it.isError }
            ?.getValue(TypeUtils.NO_EXPECTED_TYPE)
            ?.safeAs<String>()
    }

private val NO_VALUE_NAME = "missingValue"
