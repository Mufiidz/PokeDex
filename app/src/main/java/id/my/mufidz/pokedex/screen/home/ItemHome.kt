package id.my.mufidz.pokedex.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.palette.rememberPaletteState
import id.my.mufidz.pokedex.base.components.NetworkImage
import id.my.mufidz.pokedex.model.Pokemon
import id.my.mufidz.pokedex.ui.theme.PokeDexTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemHome(pokemon: Pokemon, onClick: () -> Unit) {
    val pokemonName = pokemon.name.capitalize(Locale.current)
    var palette by rememberPaletteState(null)
    val backgroundColor = palette?.lightVibrantSwatch?.rgb?.let { Color(it) }
    val cardColor =
        if (backgroundColor != null) CardDefaults.cardColors(containerColor = backgroundColor)
        else CardDefaults.cardColors()

    Card(
        modifier = Modifier.padding(8.dp),
        onClick = onClick,
        colors = cardColor,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            Modifier.background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.5f)
                    )
                )
            )
        ) {
            Column(
                Modifier.padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NetworkImage(url = pokemon.imageUrl, paletteLoadedListener = { palette = it })
                Text(
                    text = pokemonName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

            }
        }
    }
}

@Composable
@Preview
private fun ItemHomePreview() {
    PokeDexTheme {
        ItemHome(Pokemon("Example Pokemon", "https://pokeapi.co/api/v2/pokemon/1/")) {

        }
    }
}