package model

import io.ktor.http.HeadersBuilder
import io.ktor.http.HttpHeaders

fun HeadersBuilder.appendCommonHeaders() {
    append(HttpHeaders.Accept, "text/json;charset=UTF-8")
    append(HttpHeaders.AcceptCharset, "ISO-8859-1")
    append(HttpHeaders.UserAgent, "Ktor Client")
}