package epm.xnox.topnews.presentation.mark.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import epm.xnox.topnews.R
import epm.xnox.topnews.domain.model.Article
import epm.xnox.topnews.presentation.SharedViewModel
import epm.xnox.topnews.presentation.home.ui.ArticleItem
import epm.xnox.topnews.presentation.mark.viewModel.MarkViewModel

@Composable
fun ScreenMark(
    markViewModel: MarkViewModel,
    sharedViewModel: SharedViewModel,
    onNavigateToDetail: () -> Unit,
    onNavigateToBack: () -> Unit
) {
    val articles = markViewModel.articles.value

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Header(markViewModel, onNavigateToBack)
            Spacer(modifier = Modifier.height(10.dp))
            if (articles.isNotEmpty()) {
                Body(sharedViewModel, articles, onNavigateToDetail)
            } else {
                MessageEmpty()
            }
        }
    }
}

@Composable
fun MessageEmpty() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.illustration_empty_mark),
            contentDescription = null
        )
        Text(
            text = stringResource(id = R.string.mark_empty_title),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.mark_empty_description),
            fontWeight = FontWeight.Light
        )
    }
}

@Composable
fun Header(markViewModel: MarkViewModel, onNavigateToBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onNavigateToBack() }) {
            Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.article_mark),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = { markViewModel.onEvent(MarkEvent.DeleteAllArticles) }) {
            Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
        }
    }
}

@Composable
fun Body(
    sharedViewModel: SharedViewModel,
    articles: List<Article>,
    onNavigateToDetail: () -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(articles) { article ->
            ArticleItem(sharedViewModel = sharedViewModel, article = article) {
                onNavigateToDetail()
            }
        }
    }
}