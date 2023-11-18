package com.thebrownfoxx.petrealm.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.VerticalSpacer
import com.thebrownfoxx.petrealm.R
import com.thebrownfoxx.petrealm.ui.theme.AppTheme

@Composable
fun HomeScreen(
    onNavigateToPets: () -> Unit,
    onNavigateToOwners: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(256.dp)
                .align(Alignment.Center),
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            VerticalSpacer(height = 16.dp)
            FilledButton(
                text = "Pets",
                onClick = onNavigateToPets,
                modifier = Modifier.fillMaxWidth(),
            )
            VerticalSpacer(height = 4.dp)
            FilledButton(
                text = "Owners",
                onClick = onNavigateToOwners,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(onNavigateToPets = {}, onNavigateToOwners = {})
    }
}