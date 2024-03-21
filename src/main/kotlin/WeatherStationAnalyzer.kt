package org.example
import java.io.File

fun analyzeWeatherStationsData(filePath: String) {
    val weatherStationsData = mutableMapOf<String, MutableList<Double>>()

    try {
        val file = File(ClassLoader.getSystemClassLoader().getResource(filePath).toURI())

        file.forEachLine {
            try {
                val data = it.split(";")
                if (data.size >= 2) {
                    val station = data[0]
                    val measurement = data[1].toDouble()
                    weatherStationsData.getOrPut(station) { mutableListOf() }.add(measurement)
                }
            } catch (e: NumberFormatException) {
                println("Error converting to double: $it")
            } catch (e: Exception) {
                println("Error processing line: $it. Error: ${e.message}")
            }
        }
    } catch (e: Exception) {
        println("Error loading or processing file: $e")
    }

    val result = weatherStationsData.mapValues { (_, values) ->
        val min = values.minOrNull() ?: 0.0
        val mean = values.average()
        val max = values.maxOrNull() ?: 0.0
        String.format("%.1f/%.1f/%.1f", min, mean, max)
    }.toList().sortedBy { it.first }

    val output = result.joinToString("\n", "{", "}") { "${it.first}=${it.second}" }
    println(output)
}