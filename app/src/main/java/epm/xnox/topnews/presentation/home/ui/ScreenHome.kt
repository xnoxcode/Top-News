package epm.xnox.topnews.presentation.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import epm.xnox.topnews.R
import epm.xnox.topnews.core.extension.formatDate
import epm.xnox.topnews.domain.model.Article
import epm.xnox.topnews.presentation.SharedViewModel
import epm.xnox.topnews.presentation.home.viewModel.HomeViewModel
import epm.xnox.topnews.ui.component.ArticleShimmerEffect
import epm.xnox.topnews.ui.component.BannerShimmerEffect
import epm.xnox.topnews.ui.component.DatePickerDialog

@Composable
fun ScreenHome(
    homeViewModel: HomeViewModel,
    sharedViewModel: SharedViewModel,
    onNavigateToDetail: () -> Unit,
    onNavigateToMark: () -> Unit,
    onNavigateToSearch: () -> Unit
) {
    val topNewsState = homeViewModel.topNewsState.value
    val allNewsState = homeViewModel.allNewsState.value
    val worldState = homeViewModel.searchWorld.value
    val dateState = homeViewModel.searchDate.value

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Header(onNavigateToMark, onNavigateToSearch)
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = stringResource(id = R.string.popular_news),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 15.dp)
            )

            if (topNewsState.loading) {
                BannerShimmerEffect()
            }
            if (topNewsState.error.isNotBlank()) {
                BannerError(homeViewModel)
            }
            if (topNewsState.data.isNotEmpty()) {
                Banner(topNewsState, sharedViewModel, onNavigateToDetail)
            }

            Spacer(modifier = Modifier.height(15.dp))
            Category(homeViewModel, worldState, dateState)
            Spacer(modifier = Modifier.height(15.dp))

            if (allNewsState.loading) {
                ArticlesLoading()
            }
            if (allNewsState.error.isNotBlank()) {
                ArticlesError(homeViewModel)
            }
            if (allNewsState.data.isNotEmpty()) {
                Body(allNewsState, sharedViewModel, onNavigateToDetail)
            }
        }
    }
}

@Composable
fun ArticlesError(viewModel: HomeViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.illustration_network_error),
            contentDescription = null
        )
        Text(
            text = stringResource(id = R.string.banner_error),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextButton(onClick = { viewModel.onEvent(HomeEvent.GetAllNews) }) {
            Text(text = stringResource(id = R.string.news_error_try_connect))
        }
    }
}

@Composable
fun BannerError(viewModel: HomeViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.banner_error),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = { viewModel.onEvent(HomeEvent.GetTopNews) }) {
                Text(text = stringResource(id = R.string.news_error_try_connect))
            }
        }
    }
}

@Composable
fun ArticlesLoading() {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        items(6) {
            ArticleShimmerEffect()
        }
    }
}

@Composable
fun Header(onNavigateToMark: () -> Unit, onNavigateToSearch: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.app_name),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.font))
        )
        IconButton(onClick = { onNavigateToSearch() }) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }
        IconButton(onClick = { onNavigateToMark() }) {
            Icon(
                painter = painterResource(id = R.drawable.bookmark_outline),
                contentDescription = null
            )
        }
    }
}

@Composable
fun Banner(
    topNewsState: NewsState,
    sharedViewModel: SharedViewModel,
    onNavigateToDetail: () -> Unit
) {
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(topNewsState.data) { article ->
            BannerItem(article, sharedViewModel, onNavigateToDetail)
        }
    }
}

@Composable
fun BannerItem(article: Article, sharedViewModel: SharedViewModel, onNavigateToDetail: () -> Unit) {
    Card(
        onClick = {
            sharedViewModel.addArticle(article)
            onNavigateToDetail()
        },
        modifier = Modifier
            .width(300.dp)
            .height(220.dp)
            .padding(10.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(article.urlToImage)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.image_preview),
                error = painterResource(id = R.drawable.image_preview),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(color = Color.Transparent.copy(alpha = 0.5f))
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    text = article.title,
                    maxLines = 2,
                    color = Color.White.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Bold
                )
                Row(modifier = Modifier.padding(start = 10.dp, bottom = 5.dp)) {
                    Text(
                        text = article.author,
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = article.publishedAt.formatDate(),
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
    }
}

@Composable
fun Category(viewModel: HomeViewModel, worldState: String, dateState: String) {
    val categories by remember { mutableStateOf(HomeCategory.entries.map { it.name }) }
    var showDatePicker by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LazyRow(modifier = Modifier.weight(1f)) {
            items(categories) { category ->
                CategoryItem(category = category, isSelected = worldState == category) { world ->
                    viewModel.onEvent(HomeEvent.SetWorld(world))
                    viewModel.onEvent(HomeEvent.GetAllNews)
                }
            }
        }
        Spacer(modifier = Modifier.width(5.dp))
        IconButton(onClick = { showDatePicker = true }) {
            Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
        }
    }
    DatePickerDialog(
        show = showDatePicker,
        context = context,
        initialDate = dateState,
        onDateSelected = { date ->
            viewModel.onEvent(HomeEvent.SaveDate(date))
            showDatePicker = false
        },
        onDismissRequest = {
            showDatePicker = false
        }
    )
}

@Composable
fun CategoryItem(category: String, isSelected: Boolean, onSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onSelected(category) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = category, fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun Body(
    allNewsState: NewsState,
    sharedViewModel: SharedViewModel,
    onNavigateToDetail: () -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(allNewsState.data) { article ->
            ArticleItem(article, sharedViewModel, onNavigateToDetail)
        }
    }
}

@Composable
fun ArticleItem(
    article: Article,
    sharedViewModel: SharedViewModel,
    onNavigateToDetail: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(100.dp)
            .clickable {
                sharedViewModel.addArticle(article)
                onNavigateToDetail()
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(article.urlToImage)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.image_preview),
            error = painterResource(id = R.drawable.image_preview),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = article.title,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Text(
                    modifier = Modifier.weight(1f),
                    text = article.author,
                    maxLines = 1,
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = article.publishedAt.formatDate(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}