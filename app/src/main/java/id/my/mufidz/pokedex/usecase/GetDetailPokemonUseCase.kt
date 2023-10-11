package id.my.mufidz.pokedex.usecase

import id.my.mufidz.pokedex.data.PokemonRepository
import id.my.mufidz.pokedex.base.BaseUseCase
import id.my.mufidz.pokedex.data.network.DataResult
import id.my.mufidz.pokedex.model.PokemonDetail
import javax.inject.Inject

class GetDetailPokemonUseCase @Inject constructor(private val pokemonRepository: PokemonRepository) :
    BaseUseCase<GetDetailPokemonUseCase.Param, DataResult<PokemonDetail>, GetDetailPokemonUseCase.Result>() {

    override suspend fun execute(param: Param?): DataResult<PokemonDetail> {
        if (param == null) return DataResult.Failure(Throwable("Param cannot be null"))
        return pokemonRepository.getDetailPokemon(param.name)
    }

    override suspend fun DataResult<PokemonDetail>.transformToResult(): Result = when (this) {
        is DataResult.Failure -> Result.Error(error.localizedMessage ?: error.toString())
        is DataResult.Success -> {
            val abilities = data.abilities.filter { !it.isHidden }
            Result.Success(data.copy(abilities = abilities))
        }
    }

    data class Param(val name: String)

    sealed class Result {
        data class Success(val pokemon: PokemonDetail) : Result()
        data class Error(val message: String) : Result()
    }
}