package de.tudarmstadt.smartcitystudyapp.interfaces

import de.tudarmstadt.smartcitystudyapp.BuildConfig
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiInterface {

    /**
     * POST Report and attached images to server
     */
    @Multipart
    @POST(BuildConfig.ENDPOINT_REPORT)
    suspend fun postReport(
        @Part("report") report: RequestBody?,
        @Part files: MutableList<MultipartBody.Part?>?
    ): Response<Void>

    companion object {
        var BASE_URL = BuildConfig.SERVER_URL

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}