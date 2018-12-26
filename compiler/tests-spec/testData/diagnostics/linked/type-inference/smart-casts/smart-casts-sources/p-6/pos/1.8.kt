// !LANGUAGE: +NewInference
// !DIAGNOSTICS: -UNUSED_EXPRESSION
// SKIP_TXT

/*
 * KOTLIN DIAGNOSTICS SPEC TEST (POSITIVE)
 *
 * SPEC VERSION: 0.1-draft
 * PLACE: type-inference, smart-casts, smart-casts-sources -> paragraph 6 -> sentence 1
 * NUMBER: 8
 * DESCRIPTION: Nullability condition, if, complex types
 * UNEXPECTED BEHAVIOUR
 * HELPERS: properties, generics
 */

// TESTCASE NUMBER: 1
fun case_1(x: List<Int>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int> & kotlin.collections.List<kotlin.Int>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int>? & kotlin.collections.List<kotlin.Int>"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
    }
}

// TESTCASE NUMBER: 2
fun case_2(a: List<List<List<List<List<List<Int?>?>?>?>?>?>?) {
    if (a != null) {
        val b = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.Int?>?>?>?>?>?>? & kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.Int?>?>?>?>?>?>"), DEBUG_INFO_SMARTCAST!>a<!>[0]
        if (b != null) {
            val c = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.Int?>?>?>?>?>? & kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.Int?>?>?>?>?>"), DEBUG_INFO_SMARTCAST!>b<!>[0]
            if (c != null) {
                val d = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.Int?>?>?>?>? & kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.Int?>?>?>?>"), DEBUG_INFO_SMARTCAST!>c<!>[0]
                if (d != null) {
                    val e = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.Int?>?>?>? & kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.Int?>?>?>"), DEBUG_INFO_SMARTCAST!>d<!>[0]
                    if (e != null) {
                        val f = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.collections.List<kotlin.Int?>?>? & kotlin.collections.List<kotlin.collections.List<kotlin.Int?>?>"), DEBUG_INFO_SMARTCAST!>e<!>[0]
                        if (f != null) {
                            val g = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int?>? & kotlin.collections.List<kotlin.Int?>"), DEBUG_INFO_SMARTCAST!>f<!>[0]
                            if (g != null) {
                                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>g<!>
                                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int? & kotlin.Int"), DEBUG_INFO_SMARTCAST!>g<!>.equals(g)
                            }
                        }
                    }
                }
            }
        }
    }
}

// TESTCASE NUMBER: 3
fun case_3(a: List<Int>?) {
    if (a != null) {
        val b = a
        if (<!SENSELESS_COMPARISON!>a == null<!>) {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int> & kotlin.collections.List<kotlin.Int>?")!>b<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int>? & kotlin.collections.List<kotlin.Int>"), DEBUG_INFO_SMARTCAST!>b<!>.equals(b)
        }
    }
}

// TESTCASE NUMBER: 4
fun case_4(a: List<Int>?, b: List<Int> = if (a != null) <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int> & kotlin.collections.List<kotlin.Int>?"), DEBUG_INFO_SMARTCAST!>a<!> else listOf()) {
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int>?")!>a<!>
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int>")!>b<!>
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int>")!>b<!>.equals(b)
}

// TESTCASE NUMBER: 5
fun case_5() {
    if (nullableList != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int> & kotlin.collections.List<kotlin.Int>?")!>nullableList<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int>? & kotlin.collections.List<kotlin.Int>"), DEBUG_INFO_SMARTCAST!>nullableList<!>.equals(nullableList)
    }
}

// TESTCASE NUMBER: 6
fun case_6() {
    val x: List<Int>? = null

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int> & kotlin.collections.List<kotlin.Int>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int>? & kotlin.collections.List<kotlin.Int>"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
    }
}

// TESTCASE NUMBER: 7
fun case_7() {
    var x: List<Int>? = null

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int> & kotlin.collections.List<kotlin.Int>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int>? & kotlin.collections.List<kotlin.Int>"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
    }
}

// TESTCASE NUMBER: 8
fun case_8(x: _ClassWithThreeTypeParameters<Int?, Short?, _ClassWithThreeTypeParameters<Int?, Short?, String?>?>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("_ClassWithThreeTypeParameters<kotlin.Int?, kotlin.Short?, _ClassWithThreeTypeParameters<kotlin.Int?, kotlin.Short?, kotlin.String?>?> & _ClassWithThreeTypeParameters<kotlin.Int?, kotlin.Short?, _ClassWithThreeTypeParameters<kotlin.Int?, kotlin.Short?, kotlin.String?>?>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("_ClassWithThreeTypeParameters<kotlin.Int?, kotlin.Short?, _ClassWithThreeTypeParameters<kotlin.Int?, kotlin.Short?, kotlin.String?>?>? & _ClassWithThreeTypeParameters<kotlin.Int?, kotlin.Short?, _ClassWithThreeTypeParameters<kotlin.Int?, kotlin.Short?, kotlin.String?>?>"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        if (<!DEBUG_INFO_SMARTCAST!>x<!>.x != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!><!DEBUG_INFO_SMARTCAST!>x<!>.x<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int"), DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!>x<!>.x<!>.equals(<!DEBUG_INFO_SMARTCAST!>x<!>.x)
        }
        if (<!DEBUG_INFO_SMARTCAST!>x<!>.y != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Short & kotlin.Short?")!><!DEBUG_INFO_SMARTCAST!>x<!>.y<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Short"), DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!>x<!>.y<!>.equals(<!DEBUG_INFO_SMARTCAST!>x<!>.y)
        }
        if (<!DEBUG_INFO_SMARTCAST!>x<!>.z != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("_ClassWithThreeTypeParameters<kotlin.Int?, kotlin.Short?, kotlin.String?> & _ClassWithThreeTypeParameters<kotlin.Int?, kotlin.Short?, kotlin.String?>?")!><!DEBUG_INFO_SMARTCAST!>x<!>.z<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("_ClassWithThreeTypeParameters<kotlin.Int?, kotlin.Short?, kotlin.String?>"), DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!>x<!>.z<!>.equals(<!DEBUG_INFO_SMARTCAST!>x<!>.z)
            if (<!DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!>x<!>.z<!>.x != null) {
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!><!DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!>x<!>.z<!>.x<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int"), DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!>x<!>.z<!>.x<!>.equals(<!DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!>x<!>.z<!>.x)
            }
            if (<!DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!>x<!>.z<!>.y != null && <!DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!>x<!>.z<!>.z != null) {
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Short & kotlin.Short?")!><!DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!>x<!>.z<!>.y<!>
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Short"), DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!>x<!>.z<!>.y<!>.equals(<!DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!>x<!>.z<!>.y)
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.String & kotlin.String?")!><!DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!>x<!>.z<!>.z<!>
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.String"), DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!>x<!>.z<!>.z<!>.equals(<!DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!>x<!>.z<!>.z)
            }
        }
    }
}
