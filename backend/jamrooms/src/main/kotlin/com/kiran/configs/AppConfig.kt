package com.kiran.configs

import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("app")
class AppConfig {
    var spotify: SpotifyConfig = SpotifyConfig()
    lateinit var redirectUrl: String
    @ConfigurationProperties("spotify")
    class SpotifyConfig {
        var clientId: String? = null
        var clientSecret: String? = null
        var urls: SpotifyUrls = SpotifyUrls()

        @ConfigurationProperties("urls")
        class SpotifyUrls {
            var api: String? = null
            var auth: String? = null
        }
    }
}