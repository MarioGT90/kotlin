// !LANGUAGE: +NewInference
// !DIAGNOSTICS: -UNUSED_EXPRESSION
// SKIP_TXT

/*
 * KOTLIN DIAGNOSTICS SPEC TEST (POSITIVE)
 *
 * SPEC VERSION: 0.1-draft
 * PLACE: type-inference, smart-casts, smart-casts-sources -> paragraph 6 -> sentence 1
 * NUMBER: 10
 * DESCRIPTION: Nullability condition, if, complex types with projections
 * HELPERS: generics
 */

// TESTCASE NUMBER: 1
fun case_1() {
    val x = expandInv(Inv(select(10, null)))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
    }
}

// TESTCASE NUMBER: 2
fun case_2() {
    val x = expandOut(Out(select(10, null)))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
    }
}

// TESTCASE NUMBER: 3
fun case_3() {
    val x = expandInv(Inv(select(10, null)))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
    }
}

// TESTCASE NUMBER: 4
fun case_4() {
    val x = expandOut(Out(select(10, null)))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
    }
}

// TESTCASE NUMBER: 5
fun case_5() {
    val x = expandIn(In<Number?>())

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Number & kotlin.Number?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Number? & kotlin.Number"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
    }
}

// TESTCASE NUMBER: 6
fun case_6() {
    val x = expandIn(In<Number?>())

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Number & kotlin.Number?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Number? & kotlin.Number"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
    }
}

// TESTCASE NUMBER: 7
fun <T> case_7(x: MutableMap<T?, out T?>) = select(x.values.first(), x.keys.first())

fun case_7() {
    val x = case_7(mutableMapOf(10 to 10))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
    }
}

// TESTCASE NUMBER: 8
fun <T> case_8(x: MutableMap<T, out T>) = select(x.values.first(), x.keys.first())

fun case_8() {
    val x = case_8(mutableMapOf(10 to null))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
    }
}

// TESTCASE NUMBER: 9
fun <T>case_9(x: MutableMap<T, out T>) = select(x.values.first(), x.keys.first())

fun case_9() {
    val x = case_9(mutableMapOf(null to 10))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
    }
}

// TESTCASE NUMBER: 10
fun <T> case_10(x: MutableMap<T?, out T>) = select(x.values.first(), x.keys.first())

fun case_10() {
    val x = case_10(mutableMapOf(10 to 10))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
    }
}

// TESTCASE NUMBER: 11
fun <T> case_11(x: MutableMap<T, out T?>) = select(x.values.first(), x.keys.first())

fun case_11() {
    val x = case_11(mutableMapOf(10 to 10))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
    }
}

// TESTCASE NUMBER: 12
fun <T, K: T, M: K> case_12(x: MutableMap<M, K?>) = select(x.values.first(), x.keys.first())

fun case_12() {
    val x = case_12(mutableMapOf(10 to 11))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
    }
}

/*
 * TESTCASE NUMBER: 13
 * ISSUES: KT-28334
 * NOTE: before fix of the issue type is inferred to {Int? & Byte? & Short? & Long?} (smart cast from {Int? & Byte? & Short? & Long?}?)
 */
fun <T> case_13(x: MutableList<T?>?, y: List<T>) = select(x, y)

fun case_13() {
    val x = case_13(mutableListOf(1), listOf(1))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int?> & kotlin.collections.List<kotlin.Int?>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int?>? & kotlin.collections.List<kotlin.Int?>"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        val y = <!DEBUG_INFO_SMARTCAST!>x<!>[0]
        if (y != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>y<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>y<!>.equals(y)
        }
    }
}

// TESTCASE NUMBER: 14
fun <T> case_14(x: MutableList<T>?, y: List<T?>) = select(x, y)

fun case_14() {
    val x = case_14(mutableListOf(1), listOf(1))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int?> & kotlin.collections.List<kotlin.Int?>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int?>? & kotlin.collections.List<kotlin.Int?>"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        val y = <!DEBUG_INFO_SMARTCAST!>x<!>[0]
        if (y != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>y<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>y<!>.equals(y)
        }
    }
}

// TESTCASE NUMBER: 15
fun <T> case_15(x: MutableList<T>, y: List<T>?) = select(x, y)

fun case_15() {
    val x = case_15(mutableListOf(null), listOf(1))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int?> & kotlin.collections.List<kotlin.Int?>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int?>? & kotlin.collections.List<kotlin.Int?>"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        val y = <!DEBUG_INFO_SMARTCAST!>x<!>[0]
        if (y != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>y<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>y<!>.equals(y)
        }
    }
}

/*
 * TESTCASE NUMBER: 16
 * ISSUES: KT-28334
 * NOTE: before fix of the issue type is inferred to {Int? & Byte? & Short? & Long?} (smart cast from {Int? & Byte? & Short? & Long?}?)
 */
fun <T> case_16(x: MutableList<T?>, y: List<T>) = select(x, select(y, null))

fun case_16() {
    val x = case_16(mutableListOf(1), listOf(1))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int?> & kotlin.collections.List<kotlin.Int?>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int?>? & kotlin.collections.List<kotlin.Int?>"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        val y = <!DEBUG_INFO_SMARTCAST!>x<!>[0]
        if (y != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>y<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>y<!>.equals(y)
        }
    }
}

// TESTCASE NUMBER: 17
fun <T> case_17(x: MutableList<T>, y: List<T?>) = select(x, select(y, null))

fun case_17() {
    val x = case_17(mutableListOf(1), listOf(1))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int?> & kotlin.collections.List<kotlin.Int?>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int?>? & kotlin.collections.List<kotlin.Int?>"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        val y = <!DEBUG_INFO_SMARTCAST!>x<!>[0]
        if (y != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>y<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>y<!>.equals(y)
        }
    }
}

/*
 * TESTCASE NUMBER: 18
 * ISSUES: KT-28334
 * NOTE: before fix of the issue type is inferred to {Int? & Byte? & Short? & Long?} (smart cast from {Int? & Byte? & Short? & Long?}?)
 */
fun <T> case_18(x: MutableList<T?>, y: List<T>) = select(x, y)

fun case_18() {
    val x = case_18(mutableListOf(1), listOf(1))

    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int?>")!>x<!>
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int?>")!>x<!>.equals(x)
    val y = x[0]
    if (y != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>y<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>y<!>.equals(y)
    }
}

// TESTCASE NUMBER: 19
fun <T> case_19(x: MutableList<T>, y: List<T>) = select(x, y)

fun case_19() {
    val x = case_19(mutableListOf(1), listOf(null))

    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int?>")!>x<!>
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int?>")!>x<!>.equals(x)
    val y = x[0]
    if (y != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>y<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>y<!>.equals(y)
    }
}

// TESTCASE NUMBER: 20
fun <T> case_20(x: MutableList<T>, y: List<T>) = select(x.first(), y.last())

fun case_20(y: Int?) {
    val x = case_20(mutableListOf(y), listOf(1))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
    }
}

// TESTCASE NUMBER: 21
fun <T> case_21(x: MutableList<T?>, y: List<T>) = select(x.first(), y.last())

fun case_21(y: Int) {
    val x = case_21(mutableListOf(y), listOf(1))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
    }
}

// TESTCASE NUMBER: 22
fun <T> case_22(x: MutableList<T?>, y: List<T>): T? = select(x.first(), y.last())

fun case_22(y: Int) {
    val x = case_22(mutableListOf(y), listOf(1))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
    }
}

// TESTCASE NUMBER: 23
fun <T> case_23(x: MutableList<T>, y: List<T>): T = select(x.first(), y.last())

fun case_23(y: Int?) {
    val x = case_23(mutableListOf(y), listOf(1))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
    }
}

// TESTCASE NUMBER: 24
fun <A, B : A, C: B, D: C, E: D, F> case_24(x: MutableList<A>, y: List<F>) where F : E? = select(x.first(), y.last())

fun case_24(y: Int) {
    val x = case_24(mutableListOf(y), listOf(1))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
    }
}

// TESTCASE NUMBER: 25
fun <A, B : List<A>, C: List<B>, D: List<C>, E: List<C>, F> case_25(x: F, y: MutableList<C>) where F : List<E?> =
    select(x.first()?.first()?.first()?.first(), y.last().last().last())

fun case_25(y: Int) {
    val x = case_25(mutableListOf(listOf(mutableListOf(mutableListOf(y)))), mutableListOf(listOf(mutableListOf(y))))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
    }
}

/*
 * TESTCASE NUMBER: 26
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-26816
 */
fun <A, B : List<A>, C: List<B>, D: List<C>, E: List<C>, F> case_26(x: F, y: MutableList<C>) where F : List<E?> =
    select(x.first()?.first()?.first()?.first(), y.last().last().last())

fun case_26(y: Int) {
    val x = case_26(<!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.MutableList<kotlin.collections.List<kotlin.collections.List<kotlin.Nothing>>>")!>mutableListOf(<!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.collections.List<kotlin.collections.MutableList<kotlin.Int>>>"), TYPE_MISMATCH, TYPE_MISMATCH!>listOf(listOf(mutableListOf(y)))<!>)<!>, mutableListOf(listOf(mutableListOf(y))))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
    }
}
