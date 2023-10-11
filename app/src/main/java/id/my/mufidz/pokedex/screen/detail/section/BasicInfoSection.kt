package id.my.mufidz.pokedex.screen.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.my.mufidz.pokedex.base.components.NetworkImage
import id.my.mufidz.pokedex.model.PokemonDetail
import id.my.mufidz.pokedex.ui.theme.PokeDexTheme

@Composable
fun BasicInfoSection(pokemon: PokemonDetail) {
    val infos = listOf(
        BasicInfo("Weight", String.format("%.1f KG", pokemon.weight.toFloat() / 10)),
        BasicInfo("Id", String.format("#%03d", pokemon.id)),
        BasicInfo("Height", String.format("%.1f M", pokemon.height.toFloat() / 10)),
        BasicInfo("Ability", pokemon.abilities.first().ability.name.capitalize(Locale.current))
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 8.dp)
    ) {
        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NetworkImage(
                url = pokemon.sprites.frontDefault,
                Modifier.size(100.dp)
            )
            LazyHorizontalGrid(
                modifier = Modifier
                    .heightIn(100.dp, 130.dp)
                    .fillMaxWidth(),
                rows = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.Center,
                userScrollEnabled = false
            ) {
                items(infos) {
                    Column(
                        Modifier
                            .wrapContentSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = it.content,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = it.title,
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                }
            }
        }
    }
}

data class BasicInfo(
    val title: String = "", val content: String = ""
)

@Preview
@Composable
private fun BasicInfoSectionPreview() {
    PokeDexTheme {
        BasicInfoSection(PokemonDetail(height = 7, weight = 69, id = 1))
    }
}