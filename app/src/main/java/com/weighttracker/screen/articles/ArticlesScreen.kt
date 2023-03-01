package com.weighttracker.screen.articles

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import com.weighttracker.component.Header
import com.weighttracker.network.RemoteCall
import com.weighttracker.network.articles.Article

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
        Header(back = Screens.Settings, title = "Articles")

        Spacer(modifier = Modifier.height(32.dp))

        LazyColumn {
            when (state.articles) {
                is RemoteCall.Error -> {
                    item {
                        Text(text = "Error!")
                        Button(onClick = {
                            onEvent(ArticlesEvent.RetryArticlesRequest)
                        }) {
                            Text(text = "Retry")
                        }
                    }
                }
                RemoteCall.Loading -> {
                    item {
                        Text(text = "Loading")
                    }
                }
                is RemoteCall.Ok -> {
                    items(items = state.articles.data.articles) { articleItem ->
                        ArticleCard(article = articleItem)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

        }
    }
}

@Composable
private fun ArticleCard(article: Article) {
    val browser = browser()
    Column(
        modifier = Modifier
            .clickable {
                browser.openUri(article.articleLink)
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
            model = article.image, contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = article.title, fontSize = 16.sp, fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )
    }
}