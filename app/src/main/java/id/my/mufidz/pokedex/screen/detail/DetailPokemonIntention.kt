package id.my.mufidz.pokedex.screen.detail

import android.os.Parcelable
import id.my.mufidz.pokedex.base.ActionResult
import id.my.mufidz.pokedex.base.ViewAction
import id.my.mufidz.pokedex.base.ViewState
import id.my.mufidz.pokedex.model.PokemonDetail
import id.my.mufidz.pokedex.usecase.GetDetailPokemonUseCase
import kotlinx.parcelize.Parcelize

sealed class DetailAction : ViewAction {
    data class LoadDetailPokemon(val name: String) : DetailAction()
}

sealed class DetailResult : ActionResult {
    data class DetailPokemon(val result: GetDetailPokemonUseCase.Result) : DetailResult()
}
@Parcelize
data class DetailViewState(
    val errorMessage: String? = null,
    val pokemon: PokemonDetail = PokemonDetail(),
    val isLoading: Boolean = true
) : ViewState, Parcelable