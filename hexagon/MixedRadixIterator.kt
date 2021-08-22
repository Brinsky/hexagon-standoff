package hexagon

// Allows representing a number/vector where every digit/component has a different radix
class MixedRadixIterator(val mods: IntArray) : Iterator<IntArray> {
    val vector = IntArray(mods.size)

    val maxSum = mods.sum() - mods.size
    var sum = 0
        private set

    var hasNext = true

    override fun next(): IntArray {
        if (hasNext) {
            val current = vector.copyOf()
            hasNext = sum < maxSum
            increment()
            return current
        }
        return vector.copyOf()
    }

    override fun hasNext(): Boolean {
        return hasNext
    }

    fun increment() {
        for (i in vector.indices) {
            vector[i] = (vector[i] + 1) % mods[i]
            sum++

            if (vector[i] != 0) {
                break
            } else {
                sum -= mods[i]
            }
        }
    }
}

// https://en.wikipedia.org/wiki/Factorial_number_system
fun factoriadicIterator(n: Int): MixedRadixIterator {
    return MixedRadixIterator(IntArray(n) { n - it })
}

fun fixedBaseIterator(n: Int, size: Int): MixedRadixIterator {
    return MixedRadixIterator(IntArray(size) { n })
}
