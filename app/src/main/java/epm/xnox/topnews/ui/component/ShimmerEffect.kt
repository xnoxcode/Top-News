package epm.xnox.topnews.ui.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

fun Modifier.shimmerEffect(
    baseColor: Color = Color.LightGray.copy(alpha = 0.2f),
    transitionColor: Color = Color.LightGray.copy(alpha = 0.6f)
): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "")
    val translateAnimation by transition.animateFloat(
        label = "Translate Animation",
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1500,
                easing = FastOutSlowInEasing
            )
        )
    )
    background(
        brush = Brush.linearGradient(
            colors = listOf(baseColor, transitionColor, baseColor),
            start = Offset.Zero,
            end = Offset(x = translateAnimation, y = translateAnimation)
        )
    ).onGloballyPositioned { size = it.size }
}

@Composable
fun ArticleShimmerEffect() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Spacer(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(30.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(20.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.width(5.dp))
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .height(20.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.width(10.dp))
                Spacer(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .shimmerEffect()
                )
            }
        }
    }
}

@Composable
fun BannerShimmerEffect() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(15.dp)
                .clip(RoundedCornerShape(8.dp))
                .shimmerEffect()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(30.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(20.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.width(10.dp))
                Spacer(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.width(10.dp))
                Spacer(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .shimmerEffect()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Test() {
    BannerShimmerEffect()
}