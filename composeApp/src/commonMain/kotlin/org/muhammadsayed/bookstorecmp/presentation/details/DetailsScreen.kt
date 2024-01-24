package org.muhammadsayed.bookstorecmp.presentation.details

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seiko.imageloader.rememberImagePainter
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject
import org.muhammadsayed.bookstorecmp.domain.model.BookDomainModel
import org.muhammadsayed.bookstorecmp.presentation.navigation.NavigationItem
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsScreen(
    navigator: Navigator,
    viewModel: DetailsViewModel = koinInject(),
    bookId: String,
    bookImage: String
) {
    LaunchedEffect(Unit) {
        viewModel.onEvent(DetailsScreenEvents.GetBookDetails(bookId))
    }
    val state by viewModel.state.collectAsState()

    Scaffold {

        when (state) {
            is DetailsScreenState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is DetailsScreenState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "An error has been occurred")
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize().padding(16.dp)
                ) {

                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()

                        ) {

                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "",
                                modifier = Modifier.clickable {
                                    navigator.goBack()
                                }
                            )
                            Text(
                                text = "Classics",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    lineHeight = 32.sp,

                                    fontWeight = FontWeight(600),
                                    color = Color(0xFF121212),
                                )
                            )

                            Image(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    navigator.navigate(NavigationItem.Cart.route)
                                }
                            )

                        }

                    }
                    if (state is DetailsScreenState.Success.BookDetails) {
                        val data = (state as DetailsScreenState.Success.BookDetails)
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                textAlign = TextAlign.Center,
                                text = data.data.title ?: "",
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    lineHeight = 32.sp,
                                    fontWeight = FontWeight(600),
                                    color = Color(0xFF121212),
                                )
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(modifier = Modifier.wrapContentHeight()) {

                                Image(
                                    rememberImagePainter(bookImage),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(137.dp).height(214.dp)
                                        .clip(RoundedCornerShape(5.dp))
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {


                                    Text(
                                        text = "Author : Oscar Wilde",
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            lineHeight = 22.4.sp,

                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF000000),
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = "Category : Classics",
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            lineHeight = 22.4.sp,
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF000000),
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = "Rating: 4.11/5",
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            lineHeight = 22.4.sp,
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF000000),
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = "Pricing: ${Random.nextInt(500)}",
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            lineHeight = 22.4.sp,
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF000000),
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                            .padding(vertical = 8.dp)
                                            .background(
                                                color = Color(0xFF121212),
                                                shape = RoundedCornerShape(size = 5.dp)
                                            ).clickable {
                                                val book = data.data
                                                viewModel.onEvent(
                                                    DetailsScreenEvents.AddBook(
                                                        BookDomainModel(
                                                            id = bookId,
                                                            title = book.title ?: "",
                                                            subtitle = book.subtitle ?: "",
                                                            type = "",
                                                            price = "",
                                                            image = bookImage,
                                                            author = "",
                                                            qty = 1
                                                        )
                                                    )
                                                )
                                            }, contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Add to Cart",
                                            style = TextStyle(
                                                fontSize = 14.sp,
                                                lineHeight = 22.sp,
                                                fontWeight = FontWeight(600),
                                                color = Color(0xFFF2F2F2),
                                                textAlign = TextAlign.Center,
                                            ), modifier = Modifier.padding(vertical = 10.dp)
                                        )
                                    }

                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Description:",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    lineHeight = 32.sp,
                                    fontWeight = FontWeight(600),
                                    color = Color(0xFF121212),
                                )
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(data.data.description ?: "")
                        }
                    }
                }
            }
        }

    }


}