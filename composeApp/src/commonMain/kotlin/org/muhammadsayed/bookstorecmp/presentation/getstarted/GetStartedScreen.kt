package org.muhammadsayed.bookstorecmp.presentation.getstarted

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.muhammadsayed.bookstorecmp.presentation.navigation.NavigationItem

@OptIn(ExperimentalResourceApi::class)
@Composable
fun GetStartedScreen(
    navigator: Navigator,
) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.weight(0.6f)
            ) {
                Image(
                    painter = painterResource("get-started.png"),
                    null, modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                Image(
                    painter = painterResource("logo.png"),
                    null, modifier = Modifier.size(136.dp)
                )

            }
            Column(modifier = Modifier.weight(0.4f).padding(16.dp)) {
                Text(
                    text = "Read more and stress less with our online book shopping app. Shop from anywhere you are and discover titles that you love. Happy reading!",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.5.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF252525),
                        textAlign = TextAlign.Center,
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(56.dp)
                        .background(
                            color = Color(0xFF121212),
                            shape = RoundedCornerShape(size = 5.dp)
                        ).clickable {
                            navigator.navigate(NavigationItem.Home.route)
                        }, contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Get Started",
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 22.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFFF2F2F2),
                            textAlign = TextAlign.Center,
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }


        }

    }


}