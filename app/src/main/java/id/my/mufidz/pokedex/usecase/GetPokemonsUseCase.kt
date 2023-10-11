package id.my.mufidz.pokedex.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.my.mufidz.pokedex.base.BaseUseCase
import id.my.mufidz.pokedex.data.PokemonRepository
import id.my.mufidz.pokedex.data.PokemonSort
import id.my.mufidz.pokedex.data.paging.PokemonPagingSource
import id.my.mufidz.pokedex.model.Pokemon
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonsUseCase @Inject constructor(private val pokemonRepository: PokemonRepository) :
    BaseUseCase<GetPokemonsUseCase.Param, Flow<PagingData<Pokemon>>, Flow<PagingData<Pokemon>>>() {

    override suspend fun execute(param: Param?): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { PokemonPagingSource(pokemonRepository, param?.pokemonSort) }
        ).flow
    }

    override suspend fun Flow<PagingData<Pokemon>>.transformToResult(): Flow<PagingData<Pokemon>> =
        this

    data class Param(val pokemonSort: PokemonSort)
}