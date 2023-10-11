package id.my.mufidz.pokedex.screen.detail

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import id.my.mufidz.pokedex.base.BaseViewModel
import id.my.mufidz.pokedex.usecase.GetDetailPokemonUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

@HiltViewModel
class DetailPokemonViewModel @Inject constructor(
    private val detailPokemonUseCase: GetDetailPokemonUseCase, savedStateHandle: SavedStateHandle
) : BaseViewModel<DetailViewState, DetailAction, DetailResult>(
    DetailViewState(), savedStateHandle
) {

    override fun DetailResult.updateViewState(): DetailViewState = when (this) {
        is DetailResult.DetailPokemon -> result.mapResult()
    }

    override fun DetailAction.handleAction(): Flow<DetailResult> = channelFlow {
        when (this@handleAction) {
            is DetailAction.LoadDetailPokemon -> {
                val param = GetDetailPokemonUseCase.Param(name)
                detailPokemonUseCase.getResult(param).also { send(DetailResult.DetailPokemon(it)) }
            }
        }
    }

    private fun GetDetailPokemonUseCase.Result.mapResult(): DetailViewState = when (this) {
        is GetDetailPokemonUseCase.Result.Error -> updateState {
            it.copy(
                isLoading = false, errorMessage = message
            )
        }

        is GetDetailPokemonUseCase.Result.Success -> updateState {
            it.copy(
                isLoading = false, pokemon = pokemon
            )
        }
    }
}