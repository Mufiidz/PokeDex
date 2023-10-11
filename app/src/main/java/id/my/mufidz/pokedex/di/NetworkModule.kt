package id.my.mufidz.pokedex.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.jensklingenberg.ktorfit.Ktorfit
import id.my.mufidz.pokedex.data.network.PokemonApiServices
import id.my.mufidz.pokedex.utils.PokemonResponseAdapterFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val LOGGING = "Network Request"

    @Singleton
    @Provides
    fun provideBaseHttpClient(): HttpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    coerceInputValues = true

                }, ContentType.Application.Json
            )
        }
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Timber.d(message)
                }
            }
        }
    }

    @Singleton
    @Provides
    fun provideKtorfit(httpClient: HttpClient): Ktorfit =
        Ktorfit.Builder().httpClient(httpClient)
            .baseUrl("https://pokeapi.co/api/v2/", true)
            .converterFactories(PokemonResponseAdapterFactory()).build()

    @Singleton
    @Provides
    fun providePokemonServices(ktorfit: Ktorfit): PokemonApiServices = ktorfit.create()
}