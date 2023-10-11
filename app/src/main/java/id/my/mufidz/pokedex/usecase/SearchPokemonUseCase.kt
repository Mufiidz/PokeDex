package id.my.mufidz.pokedex.usecase

import id.my.mufidz.pokedex.base.BaseUseCase
import id.my.mufidz.pokedex.data.PokemonRepository
import id.my.mufidz.pokedex.data.network.DataResult
import id.my.mufidz.pokedex.model.Pokemon
import javax.inject.Inject

class SearchPokemonUseCase @Inject constructor(private val pokemonRepository: PokemonRepository) :
    BaseUseCase<SearchPokemonUseCase.Param, DataResult<List<Pokemon>>, SearchPokemonUseCase.Result>() {

    override suspend fun execute(param: Param?): DataResult<List<Pokemon>> {
        if (param == null) return DataResult.Failure(Throwable("Param cant be null"))
        return pokemonRepository.searchPokemon(param.query)
    }

    override suspend fun DataResult<List<Pokemon>>.transformToResult(): Result = when (this) {
        is DataResult.Failure -> Result.Error(error.localizedMessage ?: error.toString())
        is DataResult.Success -> Result.Success(data)
    }

    data class Param(val query: String)

    sealed class Result {
        data class Success(val pokemons: List<Pokemon>) : Result()
        data class Error(val message: String) : Result()
    }
}