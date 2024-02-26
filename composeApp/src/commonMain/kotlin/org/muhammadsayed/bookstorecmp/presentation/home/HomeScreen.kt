package org.muhammadsayed.bookstorecmp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import cafe.adriel.lyricist.LocalStrings
import com.seiko.imageloader.rememberImagePainter
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject
import org.muhammadsayed.bookstorecmp.domain.model.BookDomainModel

@Composable
fun HomeScreen(
    navigator: Navigator,
    viewModel: HomeViewModel = koinInject(),
) {
    val state by viewModel.state.collectAsState()

    Scaffold(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
        if (state.error != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "An error has been occurred")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    ) {
                        Text(
                            text = LocalStrings.current.happyReading,
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 32.sp,
                                fontWeight = FontWeight(600),
                                color = Color(0xFF121212),
                            )
                        )

                        Image(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )

                    }
                    Spacer(modifier = Modifier.height(16.dp))

                }
                if (state.loading) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(140.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                }
                if (state.currentlyReading?.isNotEmpty() == true) {

                    item {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = LocalStrings.current.bestDeals,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    item {
                        LazyRow {
                            items(state.currentlyReading ?: emptyList()) {
                                HorizontalPagerItem(it) { item ->
                                    val id = item.id.removePrefix("/works/")
                                    navigator.navigate("/details/${id}?image=${item.image}")
                                }
                            }
                        }
                    }
                }

                item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    ) {
                        Text(
                            text = LocalStrings.current.topBooks,
                            style = TextStyle(
                                fontSize = 20.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight(600),
                                color = Color(0xFF121212),
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.1.sp,
                            )
                        )
                        Text(
                            text = LocalStrings.current.seeMore,
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF121212),
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.1.sp,
                            )
                        )

                    }
                }
                item {
                    SelectableChipsRow()
                }

                if (state.alreadyReadLoading) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(270.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                }
                if (state.alreadyRead?.isNotEmpty() == true) {

                    item {
                        LazyRow {
                            items(state.alreadyRead ?: emptyList()) {
                                VerticalPagerItem(it) { item ->
                                    val id = item.id.removePrefix("/works/")
                                    navigator.navigate("/details/${id}")
                                }
                            }
                        }
                    }


                }
            }
        }


    }


}

@Composable
fun SelectableChipsRow() {
    val chipItems = listOf("This Week", "This Month", "This Year")

    var selectedChip by remember { mutableStateOf(chipItems.first()) }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(chipItems) { chip ->
            SelectableChip(
                text = chip,
                selected = chip == selectedChip,
                onSelected = { selectedChip = chip }
            )
        }
    }
}

@Composable
fun SelectableChip(
    text: String,
    selected: Boolean,
    onSelected: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .selectable(
                selected = selected,
                onClick = { onSelected(text) }
            )
            .background(
                color = if (selected) Color.Black else Color.Transparent,
                shape = MaterialTheme.shapes.medium
            )
            .border(width = 1.dp, shape = MaterialTheme.shapes.medium, color = Color.Black)
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .clip(MaterialTheme.shapes.medium)
    ) {
        Text(
            text = text,
            color = if (selected) Color.White else Color.Black,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun HorizontalPagerItem(item: BookDomainModel, onClick: (BookDomainModel) -> Unit) {
    Card(
        modifier = Modifier
            .width(300.dp)
            .height(140.dp)
            .padding(start = 16.dp)
            .clickable { onClick(item) },
        shape = RoundedCornerShape(5.dp),
    ) {
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
                    maxLines = 1,
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
}


@Composable
fun VerticalPagerItem(item: BookDomainModel, onClick: (BookDomainModel) -> Unit) {
    Card(
        modifier = Modifier
            .width(170.dp)
            .height(270.dp)
            .padding(start = 16.dp)
            .clickable { onClick(item) },
        shape = RoundedCornerShape(5.dp),
    ) {
        Column {

            Image(
                rememberImagePainter(item.image),
                contentScale = ContentScale.Fit,
                contentDescription = null,
                modifier = Modifier
                    .weight(0.5f).fillMaxWidth().background(color = Color(0xFFB8B8B8))
                    .padding(top = 16.dp)
            )

            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(10.dp),
                horizontalAlignment = Alignment.Start
            ) {

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

                Text(
                    text = item.author,
                    maxLines = 1,
                    style = TextStyle(
                        fontSize = 10.85.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
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
}