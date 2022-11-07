package com.eziosoft.moviesInParis.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var text by rememberSaveable {
        mutableStateOf("")
    }
    TextField(
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
        modifier = modifier,
        value = text,
        placeholder = { Text("Search") },
        maxLines = 1,
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable {
                    text = ""
                    onSearch(text)
                    keyboardController?.hide()
                },
                imageVector = Icons.Filled.Clear,
                contentDescription = "Clear"
            )
        },
        onValueChange = {
            text = it
            onSearch(text)
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default),
        keyboardActions = KeyboardActions(onDone = {
            onSearch(text)
            keyboardController?.hide()
        })
    )
}
