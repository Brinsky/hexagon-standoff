package hexagon

// Solver for Hexagon Standoff from "Professor Puzzle"

// Glossary:
//
// Piece:  the individual, moveable game pieces with symbols on them
// Slot:   the board locations where pieces may be placed
// Offset: the number of increments by which a piece has been rotated (counterclockwise) from its
//         "default" orientation
// Index:  a particular symbol location within a piece, but fixed to a particular "side" regardless
//         of offset (e.g. the bottom edge will always be 0 even after rotation)

val NUM_PIECES = 7
val PIECE_SIZE = 6

val PIECES =
        arrayOf(
                arrayOf("γ", "χ", "α", "ε", "π", "λ"), // 0
                arrayOf("γ", "ε", "χ", "π", "λ", "α"), // 1
                arrayOf("λ", "ε", "α", "π", "γ", "χ"), // 2
                arrayOf("α", "χ", "π", "γ", "ε", "λ"), // 3
                arrayOf("π", "χ", "α", "λ", "ε", "γ"), // 4
                arrayOf("α", "π", "γ", "λ", "χ", "ε"), // 5
                arrayOf("α", "χ", "λ", "π", "γ", "ε"), // 6
        )

data class SlotIndex(val slot: Int, val index: Int)

val EQUALITY_POINTS =
        mapOf(
                SlotIndex(0, 4) to SlotIndex(1, 1),
                SlotIndex(0, 5) to SlotIndex(4, 2),
                SlotIndex(0, 0) to SlotIndex(3, 3),
                SlotIndex(1, 0) to SlotIndex(4, 3),
                SlotIndex(1, 5) to SlotIndex(2, 2),
                SlotIndex(4, 4) to SlotIndex(2, 1),
                SlotIndex(4, 1) to SlotIndex(3, 4),
                SlotIndex(4, 0) to SlotIndex(6, 3),
                SlotIndex(4, 5) to SlotIndex(5, 2),
                SlotIndex(2, 0) to SlotIndex(5, 3),
                SlotIndex(3, 5) to SlotIndex(6, 2),
                SlotIndex(6, 4) to SlotIndex(5, 1),
        )

fun main() {
    var numSolutions = 0

    // Iterate over factoriadic numbers, then convert them to permutations - we are doing this to
    // find every permutation of piece placements
    //
    // Note that generating permutations recursively is much easier and possibly more performant,
    // but the ability to iterate over the problem space without recursion was prioritized
    for (slotsFactoriadic in factoriadicIterator(NUM_PIECES)) {
        val slots = factoriadicToPermutation(slotsFactoriadic)

        // Iterate over the possible rotations taking place for each slot (for the piece within
        // that slot)
        for (offsets in fixedBaseIterator(PIECE_SIZE, NUM_PIECES)) {
            if (isSolution(slots, offsets, EQUALITY_POINTS)) {
                println(
                        "Solution found with slottings " +
                                slots.contentToString() +
                                " and offsets " +
                                offsets.contentToString()
                )
                numSolutions++
            }
        }
    }
}

fun isSolution(
        slots: IntArray,
        offsets: IntArray,
        equalityPoints: Map<SlotIndex, SlotIndex>
): Boolean {
    // Check each edge that occurs between two pieces for equality of symbol
    for ((k, v) in equalityPoints) {
        if (getRotation(slots[k.slot], offsets[k.slot], k.index) !=
                        getRotation(slots[v.slot], offsets[v.slot], v.index)
        ) {
            return false
        }
    }

    return true
}

// https://en.wikipedia.org/wiki/Factorial_number_system#Permutations
fun factoriadicToPermutation(factoriadic: IntArray): IntArray {
    val permutation = IntArray(factoriadic.size) { -1 }

    for (i in factoriadic.indices) {
        // "i" should go at relative position "factoriadic[i]"
        val targetRelativePos = factoriadic[i]
        var currentRelativePos = 0
        var j = 0
        while (permutation[j] != -1 || currentRelativePos < targetRelativePos) {
            if (permutation[j] == -1) {
                currentRelativePos++
            }
            j++
        }

        permutation[j] = i
    }

    return permutation
}

fun getRotation(piece: Int, offset: Int, index: Int): String {
    return PIECES[piece][(index + offset) % PIECE_SIZE]
}
