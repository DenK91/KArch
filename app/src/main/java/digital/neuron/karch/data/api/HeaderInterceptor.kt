package digital.neuron.karch.data.api

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder().addHeader(ACCEPT_HEADER, JSON_TYPE).build()
        return chain.proceed(request)
    }

    companion object {
        private const val ACCEPT_HEADER = "Accept"
        private const val JSON_TYPE = "application/json"
    }
}
