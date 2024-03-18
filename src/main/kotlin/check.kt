package org.example
import java.io.File
import java.util.*

data class EntryDescription(val name: String, val quantity: Int, val average: Double, val spread: Double = 5.0)
data class Entry(val name: String, val value: Double)

val random = Random(2308)

fun generate(descriptions: List<EntryDescription>, outputFile: File) {
    val outputData = buildString {
        val entries: List<Entry> = descriptions.flatMap { description ->
            List(description.quantity) {
                Entry(
                    description.name,
                    random.nextDouble(
                        description.average - description.spread / 2,
                        description.average + description.spread / 2
                    )
                )
            }
        }
        descriptions.forEach {
            appendLine("#$it")
        }
        entries.shuffled().forEach {
            appendLine("${it.name};${String.format("%.4f", it.value)}")
        }
    }

    outputFile.writeText(outputData)
}

fun main() {
    val descriptions = listOf(
        EntryDescription("Tokyo", 100, 35.6897),
        EntryDescription("Jakarta", 120, -6.1750),
        EntryDescription("Delhi", 120, 28.6100),
        EntryDescription("Guangzhou", 120, 23.1300)
    )

    // Получаем путь к папке resources внутри проекта
    val outputFile = File(Thread.currentThread().contextClassLoader.getResource("check.txt").file)
    generate(descriptions, outputFile)

    val analyzer = WeatherStationAnalyzer()
    analyzer.analyzeWeatherStationsData("check.txt")
}