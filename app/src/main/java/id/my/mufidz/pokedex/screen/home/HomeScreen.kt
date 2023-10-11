package id.my.mufidz.pokedex.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import id.my.mufidz.pokedex.R
import id.my.mufidz.pokedex.base.components.SearchAppBar
import id.my.mufidz.pokedex.base.components.itemsPaging
import id.my.mufidz.pokedex.data.PokemonSort
import id.my.mufidz.pokedex.screen.destinations.DetailScreenDestination
import timber.log.Timber

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    homeViewModel.execute(HomeAction.LoadHomeData)
    HomeScreenContent(homeViewModel, navigator)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(homeViewModel: HomeViewModel, navigator: DestinationsNavigator) {
    val pokemonPagingItems =
        homeViewModel.viewState.collectAsState().value.pagingData.collectAsLazyPagingItems()
    var isShownMenu by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        SearchAppBar(isActive = active, query = query, onQueryChange = {
            query = it
            if (it.length > 1) {
                homeViewModel.execute(HomeAction.SearchPokemons(it))
            }
        }, onActiveChange = {
            active = it
            if (!it) {
                homeViewModel.execute(HomeAction.LoadHomeData)
            }
        }) {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }, actions = {
                IconButton(onClick = { active = !active }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                }
                IconButton(onClick = { isShownMenu = !isShownMenu }) {
                    Icon(imageVector = Icons.Filled.Sort, contentDescription = "Sorting Icon")
                    DropdownMenu(
                        expanded = isShownMenu,
                        onDismissRequest = { isShownMenu = !isShownMenu }) {
                        repeat(PokemonSort.entries.size) {
                            val pokemonSort = PokemonSort.entries[it]
                            DropdownMenuItem(text = {
                                Text(
                                    text = pokemonSort.name.capitalize(Locale.current),
                                    textAlign = TextAlign.End
                                )
                            }, onClick = {
                                Timber.d("Selected Sort => $pokemonSort")
                                homeViewModel.execute(
                                    HomeAction.SortingPokemons(pokemonSort)
                                )
                                isShownMenu = !isShownMenu
                            })
                        }
                    }
                }
            })
        }
    }) { paddingValues ->
        PagingRefresh(loadState = pokemonPagingItems.loadState.refresh,
            onRetry = { pokemonPagingItems.retry() },
            paddingValues
        ) {
            LazyVerticalGrid(
                GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                itemsPaging(pokemonPagingItems) {
                    ItemHome(pokemon = it) { navigator.navigate(DetailScreenDestination(it.name)) }
                }
                pokemonPagingItems.apply {
                    when (val appendState = loadState.append) {
                        is LoadState.Loading -> {
                            item {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }

                        is LoadState.Error -> {
                            val error = appendState.error
                            item {
                                Column(
                                    Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = error.localizedMessage ?: error.toString(),
                                        color = Color.Red,
                                        textAlign = TextAlign.Center
                                    )
                                    TextButton(onClick = { retry() }) {
                                        Text(text = "Retry")
                                    }
                                }
                            }
                        }

                        is LoadState.NotLoading -> {}
                    }
                }
            }
        }
    }
}

@Composable
private fun PagingRefresh(
    loadState: LoadState,
    onRetry: () -> Unit,
    paddingValues: PaddingValues,
    content: @Composable () -> Unit
) {

    when (loadState) {
        is LoadState.Error -> {
            val error = loadState.error
            Column(
                Modifier
                    .wrapContentSize(align = Alignment.Center)
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = error.localizedMessage ?: error.toString(),
                    color = Color.Red,
                    textAlign = TextAlign.Center
                )
                TextButton(onClick = onRetry) {
                    Text(text = "Retry")
                }
            }
        }

        LoadState.Loading -> {
            Column(
                Modifier.fillMaxSize().padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                )
            }
        }

        else -> content()
    }
}