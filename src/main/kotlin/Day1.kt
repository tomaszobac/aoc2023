import java.io.File

fun part1(file: File) {
    var numbers: Array<Int> = arrayOf()

    for (line in file.readLines()) {
        val firstNum = line.indexOfFirst { it.isDigit() }
        val lastNum = line.indexOfLast { it.isDigit() }
        val number = "${line[firstNum]}${line[lastNum]}"

        numbers += number.toInt()
    }

    println(numbers.sum())
}

fun part2(file: File) {
    val dictionary: Map<String, Int> = mapOf(
        Pair("one", 1), Pair("two", 2),Pair("three", 3),
        Pair("four", 4), Pair("five", 5),Pair("six", 6),
        Pair("seven", 7), Pair("eight", 8),Pair("nine", 9),
        Pair("1", 1), Pair("2", 2),Pair("3", 3),
        Pair("4", 4), Pair("5", 5),Pair("6", 6),
        Pair("7", 7), Pair("8", 8),Pair("9", 9),
        )
    var numbers: Array<Int> = arrayOf()

    for (line in file.readLines()) {
        var firstNum = 0
        var lastNum = 0
        var minIndex = Int.MAX_VALUE
        var maxIndex = 0

        for (key in dictionary.keys) {
            val firstIndex = line.indexOf(key)

            if (firstIndex == -1) continue

            val lastIndex = line.lastIndexOf(key)

            if (firstIndex <= minIndex) {
                firstNum = dictionary[key]!!
                minIndex = firstIndex
            }

            if (lastIndex >= maxIndex) {
                lastNum = dictionary[key]!!
                maxIndex = lastIndex
            }
        }

        numbers += ((firstNum * 10) + lastNum)
    }

    println(numbers.sum())
}

fun main() {
    val file = File("${System.getProperty("user.home")}\\Downloads\\input.txt")

    part1(file)
    part2(file)
}