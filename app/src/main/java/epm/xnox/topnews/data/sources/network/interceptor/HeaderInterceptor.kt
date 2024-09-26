package epm.xnox.topnews.data.sources.network.interceptor

import epm.xnox.topnews.core.common.Constant
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization" , "Bearer ${Constant.APIKEY}")
            .build()
        return chain.proceed(request)
    }
}