package mounts.com.driver.data.netwrok


import android.util.Log
import mounts.com.driver.Util.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response


abstract class SafeApiRequest {

    suspend fun<T:Any> apiRequest(call:suspend  ()-> Response<T>):T{

        val response = call.invoke()
        if(response.isSuccessful){
            return response.body()!!

        }else{

            Log.e("Response","Error")
            val error = response.errorBody()?.string()
            val message = StringBuilder()

            error?.let {
                try{
                    message.append(JSONObject(it).getString("meassage"))
                }catch (e:JSONException){

                }
                message.append("\n")
            }
            message.append("Error Code :${response.code()}")
            throw ApiException(message.toString())

        }

    }
}