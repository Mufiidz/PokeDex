package id.my.mufidz.pokedex.data.network

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import id.my.mufidz.pokedex.model.PokemonDetail

interface PokemonApiServices {
    @GET(Endpoints.POKEMON)
    suspend fun pokemons(
        @Query(Endpoints.OFFSET) offset : Int? = 0,
        @Query(Endpoints.LIMIT) limit : Int? = 20
    ) : DataResult<PokemonResponse>

    @GET("${Endpoints.POKEMON}/{${Endpoints.NAME}}")
    suspend fun detailPokemon(
        @Path(Endpoints.NAME) name: String,
    ) : DataResult<PokemonDetail>
}