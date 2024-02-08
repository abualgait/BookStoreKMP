package org.muhammadsayed.bookstorecmp.presentation.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.lyricist.LocalStrings
import com.seiko.imageloader.rememberImagePainter
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.muhammadsayed.bookstorecmp.domain.model.BookDomainModel

@Composable
fun CartScreen(
    navigator: Navigator,
    viewModel: CartViewModel = koinInject(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(CartScreenEvents.LoadCartItems)
    }
    Scaffold {
        if (state.cartItems?.isEmpty() == true) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                Text(
                    "No Items found in Cart",

                    textAlign = TextAlign.Center
                )
            }

        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize().padding(16.dp)
        ) {
            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = LocalStrings.current.cart,
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 32.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF121212),
                        )
                    )
                }

            }

            if (state.cartItems?.isNotEmpty() == true) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                items(state.cartItems ?: emptyList()) {
                    HorizontalPagerItem(it, viewModel) { item ->
                        viewModel.onEvent(CartScreenEvents.UpdateBookQty(item))
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = LocalStrings.current.orderSummary,
                        style = TextStyle(
                            fontSize = 20.sp,

                            fontWeight = FontWeight(600),
                            color = Color(0xFF121212),
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = LocalStrings.current.seeMore,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF252525),
                            )
                        )
                        Text(
                            text = state.subTotal.toString(),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight(600),
                                color = Color(0xFF252525),
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = LocalStrings.current.shipping,
                            style = TextStyle(
                                fontSize = 16.sp,

                                fontWeight = FontWeight(400),
                                color = Color(0xFF252525),
                            )
                        )
                        Text(
                            text = "$10.00",
                            style = TextStyle(
                                fontSize = 16.sp,

                                fontWeight = FontWeight(600),
                                color = Color(0xFF252525),
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = Color(0xFF252525))
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = LocalStrings.current.total,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF121212),
                            )
                        )

                        Text(
                            text = state.total.toString(),
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight(600),
                                color = Color(0xFF121212),
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .background(
                                color = Color(0xFF121212),
                                shape = RoundedCornerShape(size = 5.dp)
                            ).clickable {

                            }, contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = LocalStrings.current.proceedToCheckout,
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 22.sp,
                                fontWeight = FontWeight(600),
                                color = Color(0xFFF2F2F2),
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                }
            }


        }


    }


}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun HorizontalPagerItem(
    item: BookDomainModel,
    viewModel: CartViewModel,
    onQtyChanged: (BookDomainModel) -> Unit
) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(top = 16.dp),
        shape = RoundedCornerShape(5.dp),
    ) {
        Box(contentAlignment = Alignment.TopEnd) {
            Row {

                Image(
                    rememberImagePainter(item.image),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .weight(0.3f).fillMaxHeight()
                )

                Column(
                    modifier = Modifier
                        .weight(0.7f)
                        .fillMaxHeight()
                        .background(Color.Black)
                        .padding(10.dp),
                    horizontalAlignment = Alignment.Start
                ) {

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = item.type,
                        style = TextStyle(
                            fontSize = 10.85.sp,
                            fontWeight = FontWeight(300),
                            color = Color(0xFFDEDEDE),
                        )
                    )

                    Text(
                        text = item.title,
                        maxLines = 2,
                        style = TextStyle(
                            fontSize = 15.2.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFFFFFF),
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = item.author,
                        maxLines = 1,
                        style = TextStyle(
                            fontSize = 10.85.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFFFFFF),
                        )
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row {
                            Image(
                                painter = painterResource("subtract-box.png"),
                                contentDescription = "Subtract Qty",
                                modifier = Modifier.size(25.dp).clickable {
                                    if (item.qty!! > 1L) {
                                        onQtyChanged(item.copy(qty = item.qty!! - 1))
                                    }

                                }
                            )

                            Text(
                                modifier = Modifier.padding(horizontal = 8.dp),
                                text = item.qty!!.toString(),
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(600),
                                    color = Color(0xFFFFFFFF),
                                )
                            )

                            Image(
                                painter = painterResource("add-box.png"),
                                contentDescription = "Add Qty",
                                modifier = Modifier.size(25.dp).clickable {
                                    if (item.qty!! < 10L) {
                                        onQtyChanged(item.copy(qty = item.qty!! + 1))
                                    }
                                }
                            )
                        }
                        Text(
                            text = "$${item.price}",
                            style = TextStyle(
                                fontSize = 21.71.sp,
                                fontWeight = FontWeight(600),
                                color = Color(0xFFFFFFFF),
                            )
                        )
                    }

                }
            }
            Icon(Icons.Default.Close, "", tint = Color.White,
                modifier = Modifier.padding(16.dp).clickable {
                    viewModel.onEvent(CartScreenEvents.DeleteBook(item))
                })

        }

    }
}

