package com.muhammadsayed.wearapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material3.Card
import androidx.wear.compose.material3.CardDefaults
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import com.muhammadsayed.wearapp.R
import com.seiko.imageloader.rememberImagePainter
import org.koin.compose.koinInject
import org.muhammadsayed.bookstorecmp.domain.model.BookDomainModel
import org.muhammadsayed.bookstorecmp.presentation.home.HomeViewModel
import org.muhammadsayed.bookstorecmp.theme.AppTheme


class WearActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            AppTheme {
                WearAppCompose()
            }

        }
    }
}

@Composable
fun WearAppCompose(viewModel: HomeViewModel = koinInject()) {
    val state by viewModel.state.collectAsState()


    val transition = rememberInfiniteTransition(label = "loading animation transition")
    val rotation by transition.animateFloat(
        initialValue = 0f, targetValue = 360f, animationSpec = InfiniteRepeatableSpec(
            animation = tween(1000, easing = EaseIn),
            repeatMode = RepeatMode.Restart
        ),
        label = "loading rotation"
    )
    ScalingLazyColumn(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        if (state.loading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(35.dp)
                            .background(Color.White)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.loading),
                            modifier = Modifier
                                .padding(5.dp)
                                .rotate(rotation),
                            contentDescription = ""
                        )
                    }

                }
            }

        } else {
            item {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(id = R.string.best_deals),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
            }


            items(state.currentlyReading?.size ?: 0) { i ->
                BookItem(item = state.currentlyReading!![i], onClick = {

                })

            }
        }

    }

}

@Composable
fun BookItem(
    modifier: Modifier = Modifier,
    item: BookDomainModel, onClick: (BookDomainModel) -> Unit
) {

    Card(
        onClick = {
            onClick(item)
        },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                rememberImagePainter(item.image),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(4.dp)),
            )

            Column(
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
            ) {
                Text(
                    text = item.title,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2
                )

                Text(
                    text = item.author, fontSize = 8.sp,
                    maxLines = 1,
                    modifier = modifier.padding(vertical = 8.dp),
                    fontWeight = FontWeight.W300

                )
            }


        }
    }

}



