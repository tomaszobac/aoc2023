package day3

import java.io.File

data class LineData(val symbolIndexes: List<Int>, val numberIndexes: MutableMap<Int, MutableList<List<Int>>>)

data class Part2LineData(val starIndexes: List<Int>, var correctNumberIndexes: MutableMap<Int, MutableList<List<Int>>>)

fun part1(file: File): MutableList<Part2LineData> {
    val correctNumbers: MutableList<Int> = mutableListOf()
    val lines: MutableList<LineData> = mutableListOf()
    val part2Lines: MutableList<Part2LineData> = mutableListOf()

    file.bufferedReader().useLines { readLines -> readLines.forEach { line ->
        val stringBuilder = StringBuilder(line)
        val symbolIndexes: MutableList<Int> = mutableListOf()
        val numberIndexes: MutableMap<Int, MutableList<List<Int>>> = mutableMapOf()
        val starIndexes: MutableList<Int> = mutableListOf()

        for ((index, character) in line.withIndex()) {
            if (character != '.' && !(character.isDigit())) {
                if (character == '*') starIndexes.add(index)

                symbolIndexes.add(index)
                stringBuilder.setCharAt(index, '.')
            }
        }

        var copyLine = stringBuilder.toString()

        val numbers = copyLine.split(Regex("\\.+"))

        for (number in numbers) {
            if (number.isEmpty()) continue

            var firstIndex = copyLine.indexOf(number)
            var lastIndex = firstIndex + (number.length - 1)

            for (character in firstIndex..lastIndex)
                stringBuilder.setCharAt(character, '.')

            copyLine = stringBuilder.toString()

            if (firstIndex > 0) firstIndex--
            if (lastIndex < copyLine.length - 1) lastIndex++

            numberIndexes.getOrPut(number.toInt()) { mutableListOf() }.add((firstIndex..lastIndex).toList())
        }

        lines.add(LineData(symbolIndexes, numberIndexes))
        part2Lines.add(Part2LineData(starIndexes, mutableMapOf()))
    }}

    for ((lineNumber, line) in lines.withIndex()) {
        val part2LineCorrectNumbers: MutableMap<Int, MutableList<List<Int>>> = mutableMapOf()
        val symbolIndexesForLine = mutableListOf<Int>()

        if (lineNumber > 0) {
            symbolIndexesForLine.addAll(lines[lineNumber - 1].symbolIndexes)
        }

        symbolIndexesForLine.addAll(lines[lineNumber].symbolIndexes)

        if (lineNumber < lines.size - 1) {
            symbolIndexesForLine.addAll(lines[lineNumber + 1].symbolIndexes)
        }


        for ((number, coordinates) in line.numberIndexes.entries) {
            for (indexes in coordinates) {
                for (index in indexes) {
                    if (symbolIndexesForLine.contains(index)) {
                        correctNumbers.add(number)
                        part2LineCorrectNumbers.getOrPut(number) { mutableListOf() }.add(indexes)

                        break
                    }
                }
            }
        }

        part2Lines[lineNumber].correctNumberIndexes = part2LineCorrectNumbers
    }

    println(correctNumbers.sum())

    return part2Lines
}

fun part2(file: File) {
    val lines = part1(file)
    val ratios = mutableListOf<Int>()

    for ((lineNumber, line) in lines.withIndex()) {
        val numbersAndIndexesForLine: MutableMap<Int, MutableList<List<Int>>> = mutableMapOf()

        if (lineNumber > 0) {
            for ((number, coordinates) in lines[lineNumber - 1].correctNumberIndexes.entries) {
                for (indexes in coordinates) {
                    numbersAndIndexesForLine.getOrPut(number) { mutableListOf() }.add(indexes)
                }
            }
        }

        for ((number, coordinates) in lines[lineNumber].correctNumberIndexes.entries) {
            for (indexes in coordinates) {
                numbersAndIndexesForLine.getOrPut(number) { mutableListOf() }.add(indexes)
            }
        }

        if (lineNumber < lines.size - 1) {
            for ((number, coordinates) in lines[lineNumber + 1].correctNumberIndexes.entries) {
                for (indexes in coordinates) {
                    numbersAndIndexesForLine.getOrPut(number) { mutableListOf() }.add(indexes)
                }
            }
        }

        for (star in line.starIndexes) {
            var adjacentCount = 0
            val adjacentNumbers = mutableListOf<Int>()

            for ((number, coordinates) in numbersAndIndexesForLine.entries) {
                for (indexes in coordinates) {
                    if (indexes.contains(star)) {
                        adjacentCount++
                        adjacentNumbers.add(number)
                    }
                }
            }

            if (adjacentCount != 2) continue

            ratios.add(adjacentNumbers[0] * adjacentNumbers[1])
        }
    }

    println(ratios.sum())
}

fun main() {
    val file = File("src/main/kotlin/day3/input.txt")

    part2(file)
}