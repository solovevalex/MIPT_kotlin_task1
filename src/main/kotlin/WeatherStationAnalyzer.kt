package org.example
import java.io.File

class WeatherStationAnalyzer {
    fun analyzeWeatherStationsData(filePath: String) {
        val file = File(this::class.java.classLoader.getResource(filePath)!!.file)
        val weatherStationsData = mutableMapOf<String, MutableList<Double>>()

        file.forEachLine {
            val data = it.split(";")
            if (data.size >= 2) {
                val station = data[0]
                val measurement = data[1].toDouble()
                weatherStationsData.getOrPut(station) { mutableListOf() }.add(measurement)
            }
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
}