package org.gdg.fraud_cmp_app.presentation.getstarted

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bookstorecmp.composeapp.generated.resources.Res
import kotlinx.coroutines.delay
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.gdg.fraud_cmp_app.presentation.navigation.NavigationItem

@OptIn(ExperimentalResourceApi::class)
@Composable
fun GetStartedScreen(
    navigator: Navigator,
    onLocalChange: (String) -> Unit,
) {

    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            var showTeamText by remember { mutableStateOf(false) }
            Column(
                modifier = Modifier.weight(1f).padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(Res.drawable.gdg_logo),
                    null,
                    modifier = Modifier.size(136.dp).clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(16.dp))
                TypewriterGradientText(
                    text = stringResource(Res.string.get_started_desc),
                    onAnimationComplete = {
                        showTeamText = true
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                AnimatedVisibility(showTeamText) {

                    Text(
                        text = "Made with ❤ by \n Fraud Detection Team", style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 22.sp,
                            fontWeight = FontWeight(600),
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                        )
                    )
                }


                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp).height(45.dp)
                        .background(
                            color = Color(0xFF121212), shape = RoundedCornerShape(size = 200.dp)
                        ).clickable {
                            navigator.navigate(NavigationItem.Cart.route)
                        }, contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(Res.string.get_started), style = TextStyle(
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

@Composable
fun TypewriterGradientText(
    text: String,
    delayMillis: Long = 100L,
    onAnimationComplete: () -> Unit,
) {
    var displayedText by remember { mutableStateOf("") }

    LaunchedEffect(text) {
        displayedText = "" // Reset text
        text.forEachIndexed { index, _ ->
            delay(delayMillis) // Delay for each character
            displayedText = text.substring(0, index + 1) // Add one more character
        }
        onAnimationComplete() // Trigger callback when done
    }

    Text(
        text = displayedText,
        style = TextStyle(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFFDB4437),
                    Color(0xFF4285F4),
                    Color(0xFF0F9D58),
                    Color(0xFFF4B400),
                ),
                tileMode = TileMode.Mirror
            ),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 36.sp,
            textAlign = TextAlign.Center
        )
    )
}

