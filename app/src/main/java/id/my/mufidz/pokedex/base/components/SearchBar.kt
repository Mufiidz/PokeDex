package id.my.mufidz.pokedex.base.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction

@Composable
fun SearchAppBar(
    isActive: Boolean,
    query: String,
    onQueryChange: (String) -> Unit = {},
    onActiveChange: (Boolean) -> Unit = {},
    defaultAppBar: @Composable () -> Unit
) {
    if (!isActive) return defaultAppBar()

    SearchBar(
        text = query,
        onTextChange = onQueryChange,
        onCloseClicked = { onActiveChange(false) },
        onSearchClicked = onQueryChange
    )
}

@Composable
private fun SearchBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = text,
        onValueChange = onTextChange,
        placeholder = {
            Text(text = "Search here...")
        },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    if (text.isNotEmpty()) {
                        onTextChange("")
                    } else {
                        onCloseClicked()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon",
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClicked(text)
            }
        ),
    )
}