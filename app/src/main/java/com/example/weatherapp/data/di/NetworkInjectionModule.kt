package com.example.weatherapp.data.di

import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.domain.mappers.CurrentAndForecastWeatherMapper
import com.example.weatherapp.domain.mappers.WeatherDaysMapper
import com.example.weatherapp.domain.repository.WeatherRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkInjectionModule {

    val module = module {

        factory<WeatherRepository> {
            WeatherRepositoryImpl(
                api = get(),
                currentAndForecastWeatherMapper = get()
            )
        }

        factory<MoshiConverterFactory> {
            MoshiConverterFactory.create()
        }

        factory<HttpLoggingInterceptor> {
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        factory<OkHttpClient> {
            OkHttpClient.Builder()
                .addInterceptor(get<HttpLoggingInterceptor>())
                .build()
        }

        single<Retrofit> {
            Retrofit.Builder()
                .baseUrl("https://api.open-meteo.com/")
                .client(get<OkHttpClient>())
                .addConverterFactory(get<MoshiConverterFactory>())
                .build()
        }

        single<WeatherApi> { provideWeatherApiService(get()) }

        factory {
            WeatherDaysMapper()
        }

        factory {
            CurrentAndForecastWeatherMapper(weatherDaysMapper = get())
        }
    }

    private fun provideWeatherApiService(retrofit: Retrofit) =
        retrofit.create(WeatherApi::class.java)
}