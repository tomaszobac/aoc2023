package day2

import java.io.File

fun part1(file: File) {
    var correctGames: Array<Int> = arrayOf()

    lineLoop@
    for ((currentGame, line) in file.readLines().withIndex()) {
        val sets = (line.split(": ")[1]).split("; ")

        for (set in sets) {
            val cubes = set.split(", ")

            for (colorAmount in cubes) {
                val amount = colorAmount.split(' ')[0].toInt()
                val color = colorAmount.split(' ')[1]

                if (amount > 14) continue@lineLoop
                if (amount > 13 && (color == "red" || color == "green")) continue@lineLoop
                if (amount > 12 && color == "red") continue@lineLoop
            }
        }

        correctGames += currentGame + 1
    }

    println(correctGames.sum())
}

fun part2(file: File) {
    var gamePower: Array<Int> = arrayOf()

    for (line in file.readLines()) {
        val sets = (line.split(": ")[1]).split("; ")
        val fewest = mutableMapOf(Pair("red", 1), Pair("green", 1), Pair("blue", 1))
        var power = 1

        for (set in sets) {
            val cubes = set.split(", ")

            for (colorAmount in cubes) {
                val amount = colorAmount.split(' ')[0].toInt()
                val color = colorAmount.split(' ')[1]

                if (amount > fewest[color]!!) fewest[color] = amount
            }
        }

        fewest.values.forEach { power *= it }
        gamePower += power
    }

    println(gamePower.sum())
}

fun main() {
    val file = File("src/main/kotlin/day2/input.txt")

    part1(file)
    part2(file)
}