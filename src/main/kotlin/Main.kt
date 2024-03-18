package org.example

fun main() {
    val file = "weather_stations.csv"

    val analyzer = WeatherStationAnalyzer()
    analyzer.analyzeWeatherStationsData(file)

}