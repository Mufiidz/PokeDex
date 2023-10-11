package id.my.mufidz.pokedex.screen.detail.section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.palette.graphics.Palette
import id.my.mufidz.pokedex.base.components.NetworkImage
import id.my.mufidz.pokedex.model.PokemonDetail
import id.my.mufidz.pokedex.utils.PokemonType

@Composable
fun DetailHeaderSection(
    modifier: Modifier = Modifier, pokemon: PokemonDetail, onPalleteChange: (Palette) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        NetworkImage(
            url = pokemon.imageUrl,
            modifier = Modifier.size(190.dp),
            paletteLoadedListener = onPalleteChange
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = pokemon.name.capitalize(Locale.current),
            color = Color.White,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        LazyRow(
            Modifier.wrapContentSize()
        ) {
            items(pokemon.types) {
                Box(
                    Modifier
                        .wrapContentSize()
                        .padding(PaddingValues(4.dp))
                        .background(
                            colorResource(id = PokemonType.getTypeColor(it.type.name)),
                            RoundedCornerShape(15.dp)
                        )

                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = it.type.name.capitalize(Locale.current),
                        color = Color.White,
                        fontSize = 14.sp,
                    )
                }
            }
        }
    }
}