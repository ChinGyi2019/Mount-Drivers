package mounts.com.driver.data.netwrok
import mounts.com.driver.activity.ui.auth.AuthResponse
import mounts.com.driver.data.Responses.RealtimeTokenResponse
import mounts.com.driver.data.db.entities.RealtimeToken
import mounts.com.driver.data.db.entities.User
import okhttp3.OkHttpClient

import retrofit2.Response

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MyApi {

    @FormUrlEncoded
    @POST("/api/user/login")
    suspend fun userLogin(
            @Field("phone_number") phoneNumber: String,
            @Field("password") password: String,
            @Field("type") type: Int
    ) : Response<User>

    @GET("/api/firebase/realtime/token")
    suspend fun getRealtimeToken() : Response<RealtimeToken>


    companion object{
        @JvmStatic
        var token:String = ""
        const val BASE_URL ="http://178.128.23.146"


        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor) : MyApi{

//             val okHttpClient = OkHttpClient.Builder()
//                    .addInterceptor { chain ->
//
//                        val original = chain.request()
//
//                        val requestBuilder = original.newBuilder()
//
//                                .addHeader("Authorization", "Bearer "+token)
//                                .method(original.method(), original.body())
//
//
//                        val request = requestBuilder.build()
//                        chain.proceed(request)
//                    }.build()
            val okkHttpclient = OkHttpClient.Builder()
                    .addInterceptor(networkConnectionInterceptor)
                    .build()

            return Retrofit.Builder()
                    .baseUrl("http://178.128.23.146/")
                    .client(okkHttpclient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MyApi::class.java)
        }
    }

}