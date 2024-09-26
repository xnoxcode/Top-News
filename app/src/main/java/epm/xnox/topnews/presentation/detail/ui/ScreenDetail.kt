package epm.xnox.topnews.presentation.detail.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import epm.xnox.topnews.presentation.detail.viewModel.DetailViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScreenDetail(
    detailViewModel: DetailViewModel,
    sharedViewModel: SharedViewModel,
    navigateToBack: () -> Unit
) {
    val article = sharedViewModel.article!!

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column {
            Header(detailViewModel, article, navigateToBack)
            Spacer(modifier = Modifier.height(10.dp))
            Body(article)
        }
    }
}

@Composable
fun Header(viewModel: DetailViewModel, article: Article, navigateToBack: () -> Unit) {
    var insertArticle by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
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
            modifier = Modifier.fillMaxSize()
        )
        Row(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.statusBars)
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { navigateToBack() },
                modifier = Modifier.background(
                    color = Color.Transparent.copy(alpha = 0.3f),
                    shape = CircleShape
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            IconButton(
                onClick = {
                    if (article.id == 0) {
                        if (!insertArticle) {
                            viewModel.onEvent(DetailEvent.InsertArticle(article))
                            insertArticle = true
                        }
                    } else {
                        viewModel.onEvent(DetailEvent.DeleteArticle(article.id))
                        navigateToBack()
                    }
                },
                modifier = Modifier.background(
                    color = Color.Transparent.copy(alpha = 0.3f),
                    shape = CircleShape
                )
            ) {
                Icon(
                    painter = painterResource(id = if (insertArticle) R.drawable.bookmark else R.drawable.bookmark_outline),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(color = Color.Transparent.copy(alpha = 0.3f))
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = article.title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                fontFamily = FontFamily(Font(R.font.font)),
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = article.author,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = article.publishedAt.formatDate(),
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}

@Composable
fun Body(article: Article) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = article.source.name,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = { openLink(context = context, url = article.url) }) {
                Icon(painter = painterResource(id = R.drawable.ic_web), contentDescription = null)
            }
            IconButton(onClick = { shareArticle(context = context, url = article.url) }) {
                Icon(imageVector = Icons.Default.Share, contentDescription = null)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = article.description)
        }
    }
}

fun openLink(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}

fun shareArticle(context: Context, url: String) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, url)
        type = "text/plain"
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.share_article_title)
        )
    )
}