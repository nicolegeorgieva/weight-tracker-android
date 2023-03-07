package com.weighttracker.screen.recipe

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.weighttracker.Screens
import com.weighttracker.browser
import com.weighttracker.component.ErrorMessage
import com.weighttracker.component.Header
import com.weighttracker.component.LoadingMessage
import com.weighttracker.domain.data.Recipe
import com.weighttracker.network.RemoteCall

@Composable
fun RecipeScreen() {
    val viewModel: RecipeViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    UI(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun UI(
    state: RecipeState,
    onEvent: (RecipeEvent) -> Unit,
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
        Header(back = Screens.Settings, title = "Recipes")

        Spacer(modifier = Modifier.height(32.dp))

        LazyColumn {
            when (state.recipe) {
                is RemoteCall.Error -> {
                    item {
                        ErrorMessage {
                            onEvent(RecipeEvent.RetryRecipeRequest)
                        }
                    }
                }
                is RemoteCall.Loading -> {
                    item {
                        LoadingMessage()
                    }
                }
                is RemoteCall.Ok -> {
                    items(items = state.recipe.data) { recipeItem ->
                        RecipeCard(recipe = recipeItem)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun RecipeCard(recipe: Recipe) {
    val browser = browser()
    Column(
        modifier = Modifier
            .clickable {
                browser.openUri(recipe.link)
            }
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            model = recipe.image,
            contentScale = ContentScale.Crop,
            contentDescription = ""
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = recipe.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )
    }
}