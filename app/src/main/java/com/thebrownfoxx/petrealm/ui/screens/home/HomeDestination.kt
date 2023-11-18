package com.thebrownfoxx.petrealm.ui.screens.home

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.petrealm.ui.screens.destinations.OwnersDestination
import com.thebrownfoxx.petrealm.ui.screens.destinations.PetsDestination

@RootNavGraph(start = true)
@Destination
@Composable
fun Home(navigator: DestinationsNavigator) {
    HomeScreen(
        onNavigateToPets = { navigator.navigate(PetsDestination) },
        onNavigateToOwners = { navigator.navigate(OwnersDestination) },
    )
}