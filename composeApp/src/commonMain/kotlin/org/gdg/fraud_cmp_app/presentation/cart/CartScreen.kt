package org.gdg.fraud_cmp_app.presentation.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject
import org.gdg.fraud_cmp_app.domain.model.SmsDomainModel

@Composable
fun CartScreen(
    navigator: Navigator,
    viewModel: CartViewModel = koinInject(),
) {
    val state by viewModel.state.collectAsState()

//    LaunchedEffect(Unit) {
//        viewModel.onEvent(CartScreenEvents.LoadCartItems)
//    }
    Scaffold(
        bottomBar = { MyNotchedBottomAppBar() },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = Color.Green,
                onClick = {},
                shape = CircleShape,
                contentColor = Color.Blue,
                modifier = Modifier,
                content = { Icon(Icons.Default.Home, contentDescription = null) },
            )
        },
        floatingActionButtonPosition = FabPosition.Center

    ) {
//        if (state.smsList?.isEmpty() == true) {
//            Box(modifier = Modifier, contentAlignment = Alignment.Center) {
//                Text("Empty")
//
//            }
//        }

        LazyColumn {
            items(state.smsList?.size!!) {
            }
        }

        ServicesGridScreen()
        Spacer(modifier = Modifier.safeContentPadding())
    }
}

@Composable
fun MyNotchedBottomAppBar(
    onHomeClick: () -> Unit = {},
    onStatsClick: () -> Unit = {},
    onListClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
) {
    BottomAppBar(
        modifier = Modifier.background(
            color = Color.White,
            shape = CircleShape
        ),            // Creates a circular notch
        contentColor = Color.Gray,
    ) {
        IconButton(onClick = onHomeClick) {
            Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
        }
        IconButton(onClick = onStatsClick) {
            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Stats")
        }

        // Spacer to push the next icons to the end,
        // ensuring the FloatingActionButton appears in the center
        Spacer(modifier = Modifier.weight(1f, fill = true))

        IconButton(onClick = onListClick) {
            Icon(imageVector = Icons.Default.List, contentDescription = "List")
        }
        IconButton(onClick = onProfileClick) {
            Icon(imageVector = Icons.Default.Person, contentDescription = "Profile")
        }
    }
}

@Composable
fun ServicesGridScreen() {
    val services = listOf(
        ServiceItem(Icons.Default.Phone, "Your numbers", "Your good phone numbers listed"),
        ServiceItem(Icons.Default.Check, "Checked numbers", "Check if a specific number is spam or not"),
        ServiceItem(Icons.Default.Search, "Search for a number", "Search for a number and check if it cipher or not"),
        ServiceItem(Icons.Default.Person, "Your loyalty", "Check your number if it considered as a cipher number or not ")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ServiceCard(
                serviceItem = services[0],
                modifier = Modifier.weight(1f).clickable {

                }
            )
            ServiceCard(
                serviceItem = services[1],
                modifier = Modifier.weight(1f).clickable {

                }
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ServiceCard(
                serviceItem = services[2],
                modifier = Modifier.weight(1f).clickable {

                }
            )
            ServiceCard(
                serviceItem = services[3],
                modifier = Modifier.weight(1f).clickable {

                }
            )
        }
    }
}

/*
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
                        text = stringResource(Res.string.cart),
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
//                    HorizontalPagerItem(it, viewModel) { item ->
//                        viewModel.onEvent(CartScreenEvents.UpdateBookQty(item))
//                    }
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(Res.string.order_summary),
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
                            text = stringResource(Res.string.see_more),
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
                            text = stringResource(Res.string.shipping),
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
                            text = stringResource(Res.string.total),
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
                            text = stringResource(Res.string.proceed_to_checkout),
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
//
*/


@Composable
fun ServiceCard(
    serviceItem: ServiceItem,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Icon
            Icon(
                imageVector = serviceItem.icon,
                contentDescription = serviceItem.title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            // Title
            Text(
                text = serviceItem.title,
                style = MaterialTheme.typography.titleSmall
            )
            // Subtitle
            Text(
                text = serviceItem.subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}


@Composable
fun SmsHolder(
    modifier: Modifier = Modifier,
    isCipherSms: Boolean,
    onClick: () -> Unit = {},
    smsDomainModel: SmsDomainModel,
) {

    //based on this boolean value we can make some decisions make it red or make it blue  to

}


/*

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HorizontalPagerItem(
    item: SmsDomainModel,
    viewModel: CartViewModel,
    onQtyChanged: (SmsDomainModel) -> Unit,
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
                                painter = painterResource(Res.drawable.subtract_box),
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
                                painter = painterResource(Res.drawable.add_box),
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
//            Icon(Icons.Default.Close, "", tint = Color.White,
//                modifier = Modifier.padding(16.dp).clickable {
//                    viewModel.onEvent(CartScreenEvents.DeleteBook(item))
//                })

        }

    }
}

*/
