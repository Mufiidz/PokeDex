package id.my.mufidz.pokedex.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.my.mufidz.pokedex.data.PokemonRepository
import id.my.mufidz.pokedex.data.PokemonSort
import id.my.mufidz.pokedex.data.network.DataResult
import id.my.mufidz.pokedex.model.Pokemon

class PokemonPagingSource(
    private val pokemonRepository: PokemonRepository,
    private val pokemonSort: PokemonSort?
) :
    PagingSource<Int, Pokemon>() {
    companion object {
        private const val BASE_OFFSET: Int = 20
    }

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? = state.anchorPosition?.let { anchorPosition ->
        val anchorPage = state.closestPageToPosition(anchorPosition)
        anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> = try {
        val currentPage = params.key ?: 0
        val offset = currentPage * BASE_OFFSET
        when (val dataSource = pokemonRepository.getPokemons(offset, pokemonSort)) {
            is DataResult.Failure -> {
                LoadResult.Error(dataSource.error)
            }
            is DataResult.Success -> {
                val pokemons = dataSource.data
                LoadResult.Page(
                    data = pokemons,
                    prevKey = null,
                    nextKey = if (pokemons.isNotEmpty()) currentPage + 1 else null
                )
            }
        }
    } catch (e: Exception) {
        LoadResult.Error(e)
    }
}