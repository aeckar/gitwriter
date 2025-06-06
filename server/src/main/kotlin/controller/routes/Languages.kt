package controller.routes

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import controller.httpClient
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import model.LanguageConfiguration

// ':' then autocomplete filter, then autocomplete value
// forks: followers: stars: created: pushed: topic: size: owner:
// language:<AUTOCOMPLETE>
// license:<AUTOCOMPLETE>
// archived:true|false

// use existing textmate grammars + monaco to render text

@Resource("/languages")
data class Languages(val name: String) {


    /*
        - dt: assignable attributes (css classes) to elements -- needs attribute literal & simple formatting dsl (support for CSS too)
        - navigation sidebar (like kotlin documentation)
            - pages on left
            - text sections on right (marker) for
            - solid menu bar on top with logo, then search (bar), then upload (icon), then advanced repo search (icon)
        - author
        - [raw | render] - toggle
        -
     */

    // support user upload-conversion
}