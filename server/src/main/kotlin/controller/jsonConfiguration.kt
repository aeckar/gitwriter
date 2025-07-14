package controller

import kotlinx.serialization.json.Json

val jsonConfiguration = Json { // Enable sending objects as JSON over HTTP
    prettyPrint = true
    isLenient = true
    ignoreUnknownKeys = true
}