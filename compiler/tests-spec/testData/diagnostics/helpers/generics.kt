fun <K> select(vararg x: K): K = x[0]

class Inv<T>(val x: T) {
    fun test() {}
    fun get() = x
    fun put(x: T) {}
    fun getNullable(): T? = select(x, null)
}

class In<in T>() {
    fun put(x: T) {}
    fun <K : T> getWithUpperBoundT(): K = x <!UNCHECKED_CAST!>as K<!>
}

class Out<out T>(val x: T) {
    fun get() = x
}

class _ClassWithThreeTypeParameters<K, L, M>(
    val x: K,
    val y: L,
    val z: M
)

class _ClassWithSixTypeParameters<K, in L, out M, O, in P, out R>

fun <K> expandInv(vararg x: Inv<K>): K = x[0] as K
fun <K> expandIn(vararg x: In<K>): K = x[0] as K
fun <K> expandOut(vararg x: Out<K>): K = x[0] as K

fun <K> expandInvWithRemoveNullable(vararg x: Inv<K?>): K = x[0] as K
fun <K> expandInWithRemoveNullable(vararg x: In<K?>): K = x[0] as K
fun <K> expandOutWithRemoveNullable(vararg x: Out<K?>): K = x[0] as K

fun <K> removeNullable(vararg x: K?): K = x as K

interface _InterfaceWithTypeParameter1<T> {
    fun itest() {}
    fun itest1() {}
}

interface _InterfaceWithTypeParameter2<T> {
    fun itest() {}
    fun itest2() {}
}

interface _InterfaceWithTypeParameter3<T> {
    fun itest() {}
    fun itest3() {}
}

interface _InterfaceWithFiveTypeParameters1<T1, T2, T3, T4, T5> {
    fun itest() {}
    fun itest1() {}
}

interface _InterfaceWithFiveTypeParameters2<T1, T2, T3, T4, T5> {
    fun itest() {}
    fun itest2() {}
}

interface _InterfaceWithFiveTypeParameters3<T1, T2, T3, T4, T5> {
    fun itest() {}
    fun itest3() {}
}

interface _InterfaceWithTwoTypeParameters<T, K> {
    fun itest() {}
}