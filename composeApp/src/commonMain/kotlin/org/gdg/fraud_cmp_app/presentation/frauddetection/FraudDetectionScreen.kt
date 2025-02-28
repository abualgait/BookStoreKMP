package org.gdg.fraud_cmp_app.presentation.frauddetection

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.koinInject

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CartScreen(
    navigator: Navigator,
    viewModel: FraudDetectionViewModel = koinInject(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold {
        AnimatedContent(
            targetState = state,
        ) {
            when {
                state.loading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                }

                state.error.isNotEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = state.error,
                            maxLines = 5
                        )
                    }
                }

                else -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        FraudDetectionMessage(
                            state = state.response.status,
                            state.response.feedback
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        SearchInputField { message ->
                            viewModel.onEvent(
                                FraudDetectionScreenEvents.GetSMSMessageFeedback(
                                    message
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                }
            }

        }

    }
}


@Composable
fun FraudDetectionMessage(state: Boolean?, message: String) {
    state?.let {
        val icon = if (state) Icons.Default.Warning else Icons.Default.CheckCircle
        val backgroundColor =
            if (state) Color(0xFFFF5252) else Color(0xFF4CAF50) // Red for fraud, Green for safe
        val messageColor = Color.White

        // Animation effect
        val scale by remember { mutableStateOf(1f) }
        val animatedScale by animateFloatAsState(
            targetValue = if (state) 1.2f else 1f,
            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
            label = "Scale Animation"
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .scale(animatedScale),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = if (state) "⚠️ Fraud Detected!" else "✅ No Fraud Found!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = messageColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = message,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = messageColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun SearchInputField(
    onSearch: (String) -> Unit,
) {
    var inputText by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = inputText,
            onValueChange = {
                inputText = it
                isError = it.isBlank() // Update error state when text changes
            },
            label = { Text("Enter text") },
            isError = isError,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { if (inputText.isNotBlank()) onSearch(inputText) }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        if (isError) {
            Text(
                text = "Input cannot be empty!",
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (inputText.isNotBlank()) {
                    onSearch(inputText)
                } else {
                    isError = true
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = inputText.isNotBlank(),
        ) {
            Text("Send", color = Color.White)
        }
    }
}



