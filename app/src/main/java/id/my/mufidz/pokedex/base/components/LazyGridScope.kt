package id.my.mufidz.pokedex.base.components

import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems

fun <T : Any> LazyGridScope.itemsPaging(
    items: LazyPagingItems<T>,
    itemContent: @Composable LazyGridItemScope.(value: T) -> Unit
) {
    items(
        count = items.itemCount,
    ) { index ->
        val item = items[index]
        item?.let { itemContent(it) }
    }
}