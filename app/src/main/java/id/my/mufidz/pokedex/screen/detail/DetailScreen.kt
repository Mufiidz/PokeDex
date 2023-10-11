package id.my.mufidz.pokedex.screen.detail

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.palette.rememberPaletteState
import id.my.mufidz.pokedex.screen.detail.section.BasicInfoSection
import id.my.mufidz.pokedex.screen.detail.section.DetailHeaderSection

@Destination
@Composable
fun DetailScreen(navigator: DestinationsNavigator, name: String) {

    val detailViewModel = hiltViewModel<DetailPokemonViewModel>()
    detailViewModel.execute(DetailAction.LoadDetailPokemon(name))
    DetailScreenContent(navigator, detailViewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailScreenContent(
    navigator: DestinationsNavigator, detailViewModel: DetailPokemonViewModel
) {
    val state = detailViewModel.viewState.collectAsState().value
    val pokemon = state.pokemon
    var palette by rememberPaletteState(null)
    val backgroundColor = palette?.lightVibrantSwatch?.rgb?.let { Color(it) } ?: Color.Transparent
    val view = LocalView.current

    SideEffect {
        val window = (view.context as Activity).window
        val vibrant = palette?.lightVibrantSwatch?.rgb
        if (vibrant != null) {
            window.statusBarColor = backgroundColor.toArgb()
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Pokemon Detail", maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                navigationIcon = {
                    IconButton(onClick = { navigator.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back Button"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor)
            )
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        if (state.isLoading) {
            Column(
                Modifier.fillMaxSize().padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues), contentPadding = PaddingValues(
                    horizontal = 16.dp
                )
            ) {
                item {
                    DetailHeaderSection(
                        modifier = Modifier.padding(bottom = 8.dp),
                        pokemon = pokemon,
                        onPalleteChange = { palette = it })
                }
                item {
                    BasicInfoSection(pokemon = pokemon)
                }
            }
        }
    }
}