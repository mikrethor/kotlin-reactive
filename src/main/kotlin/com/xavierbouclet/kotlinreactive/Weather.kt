package com.xavierbouclet.kotlinreactive


// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString), Root.class); */
data class Location (
    var name: String? = null,
    var region: String? = null,
    var country: String? = null,
    var lat: Double = 0.0,
    var lon: Double = 0.0,
    var tz_id: String? = null,
    var localtime_epoch: Int = 0,
    var localtime: String? = null)


data class Condition (
    var text: String? = null,
    var icon: String? = null,
    var code: Int = 0)


data class Current (
    var last_updated_epoch: Int = 0,
    var last_updated: String? = null,
    var temp_c: Double = 0.0,
    var temp_f: Double = 0.0,
    var is_day: Int = 0,
    var condition: Condition? = null,
    var wind_mph: Double = 0.0,
    var wind_kph: Double = 0.0,
    var wind_degree: Int = 0,
    var wind_dir: String? = null,
    var pressure_mb: Double = 0.0,
    var pressure_in: Double = 0.0,
    var precip_mm: Double = 0.0,
    var precip_in: Double = 0.0,
    var humidity: Int = 0,
    var cloud: Int = 0,
    var feelslike_c: Double = 0.0,
    var feelslike_f: Double = 0.0,
    var vis_km: Double = 0.0,
    var vis_miles: Double = 0.0,
    var uv: Double = 0.0,
    var gust_mph: Double = 0.0,
    var gust_kph: Double = 0.0,)


data class Day (
    var maxtemp_c: Double = 0.0,
    var maxtemp_f: Double = 0.0,
    var mintemp_c: Double = 0.0,
    var mintemp_f: Double = 0.0,
    var avgtemp_c: Double = 0.0,
    var avgtemp_f: Double = 0.0,
    var maxwind_mph: Double = 0.0,
    var maxwind_kph: Double = 0.0,
    var totalprecip_mm: Double = 0.0,
    var totalprecip_in: Double = 0.0,
    var avgvis_km: Double = 0.0,
    var avgvis_miles: Double = 0.0,
    var avghumidity: Double = 0.0,
    var daily_will_it_rain: Int = 0,
    var daily_chance_of_rain: Int = 0,
    var daily_will_it_snow: Int = 0,
    var daily_chance_of_snow: Int = 0,
    var condition: Condition? = null,
    var uv: Double = 0.0,)


data class Astro (
    var sunrise: String? = null,
    var sunset: String? = null,
    var moonrise: String? = null,
    var moonset: String? = null,
    var moon_phase: String? = null,
    var moon_illumination: String? = null)


data class Hour (
    var time_epoch: Int = 0,
    var time: String? = null,
    var temp_c: Double = 0.0,
    var temp_f: Double = 0.0,
    var is_day: Int = 0,
    var condition: Condition? = null,
    var wind_mph: Double = 0.0,
    var wind_kph: Double = 0.0,
    var wind_degree: Int = 0,
    var wind_dir: String? = null,
    var pressure_mb: Double = 0.0,
    var pressure_in: Double = 0.0,
    var precip_mm: Double = 0.0,
    var precip_in: Double = 0.0,
    var humidity: Int = 0,
    var cloud: Int = 0,
    var feelslike_c: Double = 0.0,
    var feelslike_f: Double = 0.0,
    var windchill_c: Double = 0.0,
    var windchill_f: Double = 0.0,
    var heatindex_c: Double = 0.0,
    var heatindex_f: Double = 0.0,
    var dewpoint_c: Double = 0.0,
    var dewpoint_f: Double = 0.0,
    var will_it_rain: Int = 0,
    var chance_of_rain: Int = 0,
    var will_it_snow: Int = 0,
    var chance_of_snow: Int = 0,
    var vis_km: Double = 0.0,
    var vis_miles: Double = 0.0,
    var gust_mph: Double = 0.0,
    var gust_kph: Double = 0.0,
    var uv: Double = 0.0,)


data class Forecastday (
    var date: String? = null,
    var date_epoch:Int = 0,
    var day: Day? = null,
    var astro: Astro? = null,
    var hour: ArrayList<Hour>? = null)


data class Forecast (
    var forecastday: ArrayList<Forecastday>? = null)


data class WeatherRoot (
    val location: Location,
    val current: Current,
    val forecast: Forecast)


