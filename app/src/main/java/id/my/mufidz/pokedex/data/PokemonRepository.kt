package id.my.mufidz.pokedex.data

import id.my.mufidz.pokedex.data.PokemonSort.ASC
import id.my.mufidz.pokedex.data.PokemonSort.DEFAULT
import id.my.mufidz.pokedex.data.PokemonSort.DESC
import id.my.mufidz.pokedex.data.local.dao.PokemonDao
import id.my.mufidz.pokedex.data.network.DataResult
import id.my.mufidz.pokedex.data.network.PokemonApiServices
import id.my.mufidz.pokedex.model.Pokemon
import id.my.mufidz.pokedex.model.PokemonDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

interface PokemonRepository {
    suspend fun getPokemons(
        offset: Int = 0, sortBy: PokemonSort?
    ): DataResult<List<Pokemon>>

    suspend fun getDetailPokemon(name: String): DataResult<PokemonDetail>
    suspend fun searchPokemon(query: String): DataResult<List<Pokemon>>
}

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonApiServices: PokemonApiServices,
    private val pokemonDao: PokemonDao,
) : PokemonRepository {

    override suspend fun getPokemons(offset: Int, sortBy: PokemonSort?): DataResult<List<Pokemon>> {
        val sortedBy = sortBy ?: DEFAULT
        val localPokemons = getLocalPokemons(offset, sortedBy)
        if (localPokemons.isNotEmpty()) return DataResult.Success(localPokemons)

        return when (val response = pokemonApiServices.pokemons(offset)) {
            is DataResult.Failure -> DataResult.Failure(response.error)
            is DataResult.Success -> {
                var pokemons = response.data.results
                pokemonDao.insertPokemons(pokemons)
                pokemons = getLocalPokemons(offset, sortedBy)
                DataResult.Success(pokemons)
            }
        }
    }

    private suspend fun getLocalPokemons(offset: Int, sortBy: PokemonSort): List<Pokemon> {
        val localPokemons: List<Pokemon>
        withContext(Dispatchers.IO) {
            localPokemons = when (sortBy) {
                DEFAULT -> pokemonDao.getPokemonsPaged(offset)
                ASC -> pokemonDao.getPokemonsPagedASC(offset)
                DESC -> pokemonDao.getPokemonsPagedDESC(offset)
            }
        }
        return localPokemons
    }

    override suspend fun getDetailPokemon(name: String): DataResult<PokemonDetail> =
        pokemonApiServices.detailPokemon(name)

    override suspend fun searchPokemon(query: String): DataResult<List<Pokemon>> = try {
        val searchedPokemons = pokemonDao.searchPokemon("%$query%")
        DataResult.Success(searchedPokemons)
    } catch (e: Throwable) {
        DataResult.Failure(e)
    }

}

enum class PokemonSort { DEFAULT, ASC, DESC }