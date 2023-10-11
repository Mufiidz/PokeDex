package id.my.mufidz.pokedex.screen.home

import androidx.paging.PagingData
import id.my.mufidz.pokedex.base.ActionResult
import id.my.mufidz.pokedex.base.ViewAction
import id.my.mufidz.pokedex.base.ViewState
import id.my.mufidz.pokedex.data.PokemonSort
import id.my.mufidz.pokedex.model.Pokemon
import id.my.mufidz.pokedex.usecase.SearchPokemonUseCase
import kotlinx.coroutines.flow.MutableStateFlow

sealed class HomeAction : ViewAction {
    data object LoadHomeData : HomeAction()
    data class SortingPokemons(val pokemonSort: PokemonSort) : HomeAction()
    data class SearchPokemons(val query: String) : HomeAction()
}

sealed class HomeResult : ActionResult {
    data class HomeData(val result: PagingData<Pokemon>) : HomeResult()
    data class SearchedData(val result: SearchPokemonUseCase.Result) : HomeResult()
}

data class HomeViewState(
    val pagingData: MutableStateFlow<PagingData<Pokemon>> = MutableStateFlow(PagingData.empty()),
    val queriedPokemons: List<Pokemon> = listOf(),
    val errorMessage: String = ""
) : ViewState