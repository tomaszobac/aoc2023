package day4

import java.io.File

fun part1(file: File): List<Int> {
    val pilePoints: MutableList<Int> = mutableListOf()
    val part2AmountOfNumbers: MutableList<Int> = mutableListOf()
    var index = 0

    file.bufferedReader().useLines { readLines -> readLines.forEach { line ->
        val sets = (line.split(Regex(": +"))[1]).split(Regex(" +\\| +"))
        val winNumbers = (sets[0].split(Regex(" +")).map { it.toInt() }).toSet()
        val myNumbers = (sets[1].split(Regex(" +")).map { it.toInt() }).toSet()

        var points = 0

        part2AmountOfNumbers.add(index,0)

        for (number in myNumbers) {
            if (winNumbers.contains(number)) {
                points = if (points == 0) 1 else points * 2
                part2AmountOfNumbers[index] += 1
            }
        }

        index++
        pilePoints += points
    }}

    println(pilePoints.sum())

    return part2AmountOfNumbers.toList()
}

fun part2(file: File) {
    val amountOfNumbers: List<Int> = part1(file)
    val numberOfCopies: MutableList<Int> = MutableList(amountOfNumbers.size) { 1 }

    for ((card, amount) in amountOfNumbers.withIndex()) {
        if (amount == 0) continue

        for (repeat in 1..numberOfCopies[card]) {
            for (index in card + 1..card + amount) {
                numberOfCopies[index]++
            }
        }
    }

    println(numberOfCopies.sum())
}

fun main() {
    val file = File("src/main/kotlin/day4/input.txt")

    part2(file)
}