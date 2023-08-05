package com.f10tools.batto.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen() {
    val scrollState = rememberScrollState()
    val modifier = Modifier.verticalScroll(scrollState)

    Column(
        modifier = modifier
    ) {

    }
}