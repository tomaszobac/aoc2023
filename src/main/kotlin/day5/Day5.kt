package day5

import java.io.File

fun part1(file: File) {
    var valuesToProcess: MutableList<Long> = mutableListOf()
    val processedValues: MutableList<Long> = mutableListOf()

    file.bufferedReader().useLines {
        for (line in it) {
            if (line.isEmpty()) continue

            if (line.contains("seeds")) {
                valuesToProcess = (line.split(": ")[1])
                    .split(" ")
                    .map { seed -> seed.toLong() }
                    .toMutableList()

                continue
            }

            if (line.contains("map")) {
                valuesToProcess.addAll(processedValues)
                processedValues.clear()

                continue
            }

            val mapData = line.split(' ').map { value -> value.toLong() }
            val lowerLimit = mapData[1]
            val upperLimit = mapData[1] + mapData[2] - 1
            val leftover: MutableList<Long> = mutableListOf()

            for (value in valuesToProcess) {
                if (value in lowerLimit..upperLimit) {
                    processedValues.add(mapData[0] + (value - lowerLimit))
                } else leftover.add(value)
            }

            valuesToProcess = leftover
        }
    }

    valuesToProcess.addAll(processedValues)
    println(valuesToProcess.min())
}

fun part2(file: File) {
    return
}

fun main() {
    val file = File("src/main/kotlin/day5/input.txt")

    part1(file)
    //part2(file)
}