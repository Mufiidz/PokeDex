package id.my.mufidz.pokedex.data.network

import id.my.mufidz.pokedex.model.Pokemon
import kotlinx.serialization.Serializable

@Serializable
data class PokemonResponse(
    val count: Int = 0,
    val next: String? = null,
    val previous: String? = null,
    val results: List<Pokemon> = listOf()
)