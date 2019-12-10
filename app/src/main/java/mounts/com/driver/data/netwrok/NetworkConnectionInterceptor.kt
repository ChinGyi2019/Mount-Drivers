package mounts.com.driver.data.netwrok

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import mounts.com.driver.Helpers.UiHelper
import mounts.com.driver.Util.NoInternetException
import mounts.com.driver.data.netwrok.MyApi.Companion.token
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(context: Context):Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailabel())
            throw NoInternetException("Make sure you have an active data connection")
    //    return chain.proceed(chain.request())
        val original = chain.request()
        val requestBuilder = original.newBuilder()
                                .addHeader("Authorization", "Bearer "+token)
                                .method(original.method(), original.body())
                        val request = requestBuilder.build()
        return chain.proceed(request)
         //


    }

    private fun isInternetAvailabel(): Boolean {
        var result = false
        val connectivityManager = applicationContext
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
     //  TODO() IF isConnected is deprecated?......
//            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
//            connectivityManager?.let {
//                it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
//                    result = when {
//                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//                        else -> false
//                    }
//                }

        }

    }
}
