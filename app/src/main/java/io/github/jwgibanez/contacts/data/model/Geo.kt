package io.github.jwgibanez.contacts.data.model

import java.io.Serializable

data class Geo(
    var lat: String? = null,
    var lng: String? = null
) : Serializable