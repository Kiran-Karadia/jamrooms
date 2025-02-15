package com.kiran.httpclients

import com.kiran.services.TokenService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpRequest
import io.micronaut.http.annotation.Filter
import io.micronaut.http.filter.ClientFilterChain
import io.micronaut.http.filter.HttpClientFilter
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory

@Filter(serviceId = ["spotify-api"])
class SpotifyApiFilter(
    private val tokenService: TokenService
): HttpClientFilter {

    private val logger = LoggerFactory.getLogger(this.javaClass.simpleName)

    override fun doFilter(request: MutableHttpRequest<*>, chain: ClientFilterChain): Publisher<out HttpResponse<*>> {
        // Use mono to handle using a suspend method while having a Publisher return type
        return mono {
            val token = tokenService.getAuthHeaderValue()
            logger.info("token: $token")
            logger.info("Before request: ${request.headers.toList()}")
            request.headers.set("Authorization", token)
            logger.info("After request: ${request.headers.toList()}")
            logger.info("Token has been added...")
            chain.proceed(request).awaitSingle()
        }
    }
}