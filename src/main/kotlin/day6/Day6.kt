package day6

import java.io.File

fun part1(file: File) {
    val times: List<Int>
    val records: List<Int>
    val bufferedReader = file.bufferedReader()

    times = bufferedReader.readLine()
        .split(Regex(": +"))[1]
            .split(Regex(" +"))
            .map { number -> number.toInt() }

    records = bufferedReader.readLine()
        .split(Regex(": +"))[1]
            .split(Regex(" +"))
            .map { number -> number.toInt() }

    val possibleWins: MutableList<Int> = MutableList(times.size) { 0 }

    for ((index, raceTime) in times.withIndex()) {
        for (charge in 0..raceTime) {
            if (records[index] < ((raceTime - charge) * charge)) {
                possibleWins[index]++
            }
        }
    }

    var result = 1
    possibleWins.forEach { result *= it }

    println(result)
}

fun part2(file: File) {
    val time: Long
    val record: Long
    var possibleWins: Long = 0
    val bufferedReader = file.bufferedReader()

    time = bufferedReader.readLine()
        .split(Regex(": +"))[1]
            .replace(Regex(" +"), "")
            .toLong()

    record = bufferedReader.readLine()
        .split(Regex(": +"))[1]
            .replace(Regex(" +"), "")
            .toLong()

    for (charge in 0..time) {
        if (record < ((time - charge) * charge)) {
            possibleWins++
        }
    }

    println(possibleWins)
}

fun main() {
    val file = File("src/main/kotlin/day6/input.txt")

    part1(file)
    part2(file)
}