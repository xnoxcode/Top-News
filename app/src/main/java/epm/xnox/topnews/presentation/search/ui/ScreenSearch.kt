package epm.xnox.topnews.presentation.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import epm.xnox.topnews.R
import epm.xnox.topnews.presentation.SharedViewModel
import epm.xnox.topnews.presentation.home.ui.ArticleItem
import epm.xnox.topnews.presentation.home.ui.ArticlesLoading
import epm.xnox.topnews.presentation.search.viewModel.SearchViewModel

@Composable
fun ScreenSearch(
    searchViewModel: SearchViewModel,
    sharedViewModel: SharedViewModel,
    onNavigateToDetail: () -> Unit,
    onNavigateToBack: () -> Boolean
) {
    val state = searchViewModel.state.value
    val worldState = searchViewModel.searchWorld.value

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Header(searchViewModel, worldState, onNavigateToBack)
            if (state.loading) {
                ArticlesLoading()
            }
            if (state.error.isNotEmpty()) {
                SearchError(searchViewModel)
            }
            if (state.data.isNotEmpty()) {
                Body(state, sharedViewModel, onNavigateToDetail)
            }
        }
    }
}

@Composable
fun SearchError(searchViewModel: SearchViewModel) {
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
        TextButton(onClick = { searchViewModel.onEvent(SearchEvent.SearchArticle) }) {
            Text(text = stringResource(id = R.string.news_error_try_connect))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(viewModel: SearchViewModel, worldState: String, onNavigateToBack: () -> Boolean) {
    var onSearch by remember { mutableStateOf(false) }
    val recentSearch = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        SearchBar(
            query = worldState,
            active = onSearch,
            colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.secondary),
            onQueryChange = {
                viewModel.onEvent(SearchEvent.SetWorld(it))
            },
            onSearch = {
                recentSearch.add(worldState)
                viewModel.onEvent(SearchEvent.SearchArticle)
                onSearch = false
            },
            onActiveChange = {
                onSearch = it
            },
            placeholder = {
                Text(text = stringResource(id = R.string.search_article_label))
            },
            leadingIcon = {
                IconButton(onClick = { onNavigateToBack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            trailingIcon = {
                if (onSearch) {
                    IconButton(onClick = {
                        if (worldState.isNotEmpty())
                            viewModel.onEvent(SearchEvent.SetWorld(""))
                        else
                            onSearch = false
                    }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            recentSearch.forEach {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .clickable { viewModel.onEvent(SearchEvent.SetWorld(it)) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = it, color = MaterialTheme.colorScheme.onBackground)
                }
            }
        }
    }
}

@Composable
fun Body(state: SearchState, sharedViewModel: SharedViewModel, onNavigateToDetail: () -> Unit) {
    LazyColumn(modifier = Modifier.padding(10.dp)) {
        items(state.data) { article ->
            ArticleItem(article = article, sharedViewModel = sharedViewModel) {
                onNavigateToDetail()
            }
        }
    }
}