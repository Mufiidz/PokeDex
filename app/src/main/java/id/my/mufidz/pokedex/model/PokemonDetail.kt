package id.my.mufidz.pokedex.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
@Entity
data class PokemonDetail(
    val id: Int = 0,
    val abilities: List<Abilities> = listOf(),
    val forms: List<Form> = listOf(),
    val height: Int = 0,
    val name: String = "",
    val order: Int = 0,
    val sprites: Sprites = Sprites(),
    val weight: Int = 0,
    val types: List<PokemonType> = listOf()
) : Parcelable {

    @Serializable
    @Parcelize
    data class Abilities(
        val ability: Ability = Ability(),
        @SerialName("is_hidden")
        val isHidden: Boolean = false,
        val slot: Int = 0
    ) : Parcelable

    @Serializable
    @Parcelize
    data class Ability(
        val name: String = "",
        val url: String = ""
    ) : Parcelable

    @Serializable
    @Parcelize
    data class Form(
        val name: String = "",
        val url: String = ""
    ) : Parcelable

    @Serializable
    @Parcelize
    data class Sprites(
        @SerialName("front_default")
        val frontDefault: String = "",
    ) : Parcelable

    @Serializable
    @Parcelize
    data class PokemonType(val slot: Int = 0, val type: Type = Type()) : Parcelable {
        @Serializable
        @Parcelize
        data class Type(
            val name: String = "",
            val url: String = ""
        ) : Parcelable
    }

    @IgnoredOnParcel
    val imageUrl: String =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}