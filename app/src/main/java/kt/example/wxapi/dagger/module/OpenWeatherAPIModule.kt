package kt.example.wxapi.dagger.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import kt.example.wxapi.api.OpenWeatherAPI
import kt.example.wxapi.api.OpenWeatherInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = arrayOf(GSONModule::class))
class OpenWeatherAPIModule {

    @Provides
    @Singleton
    fun provideApi(gson : Gson): Retrofit.Builder {

        val apiClient = OkHttpClient.Builder().addInterceptor(OpenWeatherInterceptor()).build()

        return Retrofit.Builder().apply {
            baseUrl(OpenWeatherAPI.BASE_URL)
            addConverterFactory(GsonConverterFactory.create(gson))
            client(apiClient)
                .build().create(OpenWeatherAPI::class.java)
        }
    }
}