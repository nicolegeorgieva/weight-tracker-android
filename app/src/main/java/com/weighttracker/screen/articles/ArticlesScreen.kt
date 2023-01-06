package com.weighttracker.screen.articles

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.weighttracker.Screens
import com.weighttracker.browser
import com.weighttracker.component.BackButton

@Composable
fun ArticlesScreen() {
    val viewModel: ArticlesViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    UI(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun UI(
    state: ArticlesState,
    onEvent: (ArticlesEvent) -> Unit,
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
        BackButton(screens = Screens.Settings)

        Spacer(modifier = Modifier.height(32.dp))

        LazyColumn {
            items(items = state.articles) { articleItem ->
                ArticleCard(article = articleItem)
            }
        }
    }
}

@Composable
private fun ArticleCard(article: Article) {
    val browser = browser()
    Column(
        modifier = Modifier.clickable {
            browser.openUri(article.articleLink)
        }
    ) {
        AsyncImage(model = article.image, contentDescription = "")
        Text(text = article.title)
    }
}