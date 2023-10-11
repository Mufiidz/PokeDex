package id.my.mufidz.pokedex.utils

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import id.my.mufidz.pokedex.R
import timber.log.Timber

object PokemonType {
    fun getTypeColor(type: String): Int {
        Timber.d(type)
        return when (type.toLowerCase(Locale.current)) {
            "fighting" -> R.color.fighting
            "flying" -> R.color.flying
            "poison" -> R.color.poison
            "ground" -> R.color.ground
            "rock" -> R.color.rock
            "bug" -> R.color.bug
            "ghost" -> R.color.ghost
            "steel" -> R.color.steel
            "fire" -> R.color.fire
            "water" -> R.color.water
            "grass" -> R.color.grass
            "electric" -> R.color.electric
            "psychic" -> R.color.psychic
            "ice" -> R.color.ice
            "dragon" -> R.color.dragon
            "fairy" -> R.color.fairy
            "dark" -> R.color.dark
            else -> R.color.gray_21
        }
    }
}