package com.yablonskyi.caelum.data.di

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("appid", apiKey)
            .build()

        val request = original.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(request)
    }
}
