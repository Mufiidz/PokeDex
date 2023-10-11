package id.my.mufidz.pokedex.screen.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.my.mufidz.pokedex.base.BaseViewModel2
import id.my.mufidz.pokedex.usecase.GetPokemonsUseCase
import id.my.mufidz.pokedex.usecase.SearchPokemonUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapConcat
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pokemonsUseCase: GetPokemonsUseCase,
    private val searchPokemonUseCase: SearchPokemonUseCase
) : BaseViewModel2<HomeViewState, HomeAction, HomeResult>(HomeViewState()) {

    override fun HomeResult.updateViewState(): HomeViewState = when (this) {
        is HomeResult.HomeData -> updateState {
            it.copy(pagingData = MutableStateFlow(result))
        }

        is HomeResult.SearchedData -> when (result) {
            is SearchPokemonUseCase.Result.Error -> updateState {
                it.copy(errorMessage = result.message)
            }

            is SearchPokemonUseCase.Result.Success -> updateState {
                it.copy(pagingData = MutableStateFlow(PagingData.from(result.pokemons)))
            }
        }
    }

    @OptIn(FlowPreview::class)
    override fun HomeAction.handleAction(): Flow<HomeResult> = channelFlow {
        when (this@handleAction) {
            HomeAction.LoadHomeData -> {
                pokemonsUseCase.getResult().also {
                    it.distinctUntilChanged().cachedIn(viewModelScope).collect { data ->
                        send(HomeResult.HomeData(data))
                    }
                }
            }

            is HomeAction.SortingPokemons -> {
                val param = GetPokemonsUseCase.Param(pokemonSort)
                pokemonsUseCase.getResult(param).also {
                    it.distinctUntilChanged().cachedIn(viewModelScope).collect { data ->
                        send(HomeResult.HomeData(data))
                    }
                }
            }

            is HomeAction.SearchPokemons -> {
                MutableStateFlow(query).debounce(5.toDuration(DurationUnit.SECONDS))
                    .distinctUntilChanged().collectLatest { newQuery ->
                    val param = SearchPokemonUseCase.Param(newQuery)
                    searchPokemonUseCase.getResult(param).also {
                        send(HomeResult.SearchedData(it))
                    }
                }
            }
        }
    }
}