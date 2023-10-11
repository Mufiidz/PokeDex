package id.my.mufidz.pokedex.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
@Entity
data class Pokemon(
    @PrimaryKey val name: String = "", val url: String = ""
) : Parcelable {

    @IgnoredOnParcel
    @Ignore
    val id: String = url.split("/".toRegex()).dropLast(1).last()

    @IgnoredOnParcel
    @Ignore
    val imageUrl: String =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}