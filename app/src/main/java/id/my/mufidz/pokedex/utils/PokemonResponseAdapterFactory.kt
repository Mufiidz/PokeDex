package id.my.mufidz.pokedex.utils

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.Converter
import de.jensklingenberg.ktorfit.internal.TypeData
import id.my.mufidz.pokedex.data.network.DataResult
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import timber.log.Timber

class PokemonResponseAdapterFactory : Converter.Factory {

    override fun suspendResponseConverter(
        typeData: TypeData,
        ktorfit: Ktorfit
    ): Converter.SuspendResponseConverter<HttpResponse, *>? {
        if (typeData.typeInfo.type != DataResult::class) return null
        return object : Converter.SuspendResponseConverter<HttpResponse, DataResult<Any>> {
            override suspend fun convert(response: HttpResponse): DataResult<Any> {
                return try {
                    val test = response.body<Any>(typeData.typeArgs.first().typeInfo)
                    DataResult.Success(test)
                } catch (t: Throwable) {
                    DataResult.Failure(t)
                }
            }
        }
    }
}