package day5

import java.io.File
import kotlin.concurrent.thread

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
    var map: MutableList<MutableList<Long>> = mutableListOf()
    val fullMaps: MutableList<MutableList<MutableList<Long>>> = mutableListOf() //AllMaps<Map<MapLine>>
    val seedRanges: MutableMap<Long, Long> = mutableMapOf()

    file.bufferedReader().useLines {
        for (line in it) {
            if (line.isEmpty()) continue

            if (line.contains("seeds")) {
                val seedsToProcess = (line.split(": ")[1])
                    .split(" ")
                    .map { seed -> seed.toLong() }
                    .toMutableList()

                for (seed in 0..<seedsToProcess.size step 2)
                    seedRanges[seedsToProcess[seed]] = seedsToProcess[seed] + (seedsToProcess[seed + 1] - 1)

                continue
            }

            if (line.contains("map")) {
                if (map.isNotEmpty()) {
                    fullMaps.add(map)
                    map = mutableListOf()
                }

                continue
            }

            val mapData = line.split(' ').map { value -> value.toLong() }
            val mapLine = mutableListOf<Long>()

            val mappingValue = mapData[0]
            val lowerLimit = mapData[1]
            val upperLimit = mapData[1] + mapData[2] - 1

            mapLine.add(mappingValue)
            mapLine.add(lowerLimit)
            mapLine.add(upperLimit)

            map.add(mapLine)
        }
    }

    fullMaps.add(map)
    map = mutableListOf()

    val locations: MutableSet<Long> = mutableSetOf()
    val threads: MutableList<Thread> = mutableListOf()

    for (seedRange in seedRanges) {
        val thread = thread(start = true) {
            locations.add(findLowestLocation(seedRange.key, seedRange.value, fullMaps))
        }
        threads.add(thread)
    }

    threads.forEach { it.join() }

    println(locations.min())
}

fun findLowestLocation(seedMin: Long, seedMax: Long, fullMaps: MutableList<MutableList<MutableList<Long>>>): Long {
    var lowestLocation: Long = Long.MAX_VALUE

    for (seed in seedMin..seedMax) {
        var value = seed

        mapLoop@
        for (currentMap in fullMaps) {
            for (mapLine in currentMap) {
                if (value in mapLine[1]..mapLine[2]) {
                    value = mapLine[0] + (value - mapLine[1])

                    continue@mapLoop
                }
            }
        }

        if (value < lowestLocation) lowestLocation = value
    }

    return lowestLocation
}

fun main() {
    val file = File("src/main/kotlin/day5/input.txt")

    part1(file)

    val startTime = System.currentTimeMillis()
    part2(file)
    val endTime = System.currentTimeMillis()

    val duration = endTime - startTime
    val minutes = duration / 1000 / 60
    val seconds = (duration / 1000) % 60

    println("Execution time: ${minutes}m ${seconds}s")
}