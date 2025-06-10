package com.shubham.presentation.bookdetails

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.palette.graphics.Palette
import coil.Coil
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.shubham.presentation.common.BookListState
import com.shubham.presentation.common.components.LoadingIndicator
import com.shubham.presentation.common.components.RetryView


@Composable
fun BookDetailsScreen(
    viewModel: BookDetailsViewModel = hiltViewModel(), onCloseClick: () -> Unit
) {
    val context = LocalContext.current
    val bookListState by viewModel.bookListState.collectAsStateWithLifecycle()
    val imageUrl: String = bookListState.books.firstOrNull()?.coverImageUrl ?: ""
    val previewUrl: String? = bookListState.books.firstOrNull()?.previewUrl
    val bannerColor = rememberDominantColor(imageUrl, viewModel)
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            bannerColor.copy(alpha = 0.9f), bannerColor.copy(alpha = 0.2f)
        )
    )
    if (bookListState.isLoading) {
        LoadingIndicator()
    } else if (bookListState.error != null) {
        RetryView {
            viewModel.retryLoadBook()
        }
    } else {
        DetailsScreen(context, bookListState, imageUrl, previewUrl, gradientBrush, onCloseClick)
    }


}

@Composable
fun DetailsScreen(
    context: Context,
    bookListState: BookListState,
    imageUrl: String,
    previewUrl: String?,
    gradientBrush: Brush,
    onCloseClick: () -> Unit
) {
    val overlap = 90.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = overlap)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(gradientBrush)
            ) {
                IconButton(
                    onClick = { onCloseClick() },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.Black
                    )
                }
            }
            AsyncImage(
                model = imageUrl,
                contentDescription = "Book Cover",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .aspectRatio(2f / 3f)
                    .align(Alignment.BottomCenter)
                    .offset(y = overlap / 2)
                    .clip(RoundedCornerShape(8.dp))
                    .border(2.dp, Color.White, RoundedCornerShape(8.dp))
                    .shadow(8.dp)
            )
        }
        Text(
            text = bookListState.books.firstOrNull()?.title ?: "",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = 16.dp),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = bookListState.books.firstOrNull()?.author ?: "",
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = bookListState.books.firstOrNull()?.summary ?: "No description available.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                previewUrl?.let {
                    val intent = Intent(Intent.ACTION_VIEW, it.toUri())
                    context.startActivity(intent)
                }
            }, modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 32.dp)
        ) {
            Text("Preview Book")
        }
    }
}

@Composable
fun rememberDominantColor(url: String, viewModel: BookDetailsViewModel): Color {
    val context = LocalContext.current
    var color by remember(url) { mutableStateOf(Color.Gray) }

    LaunchedEffect(url) {
        try {
            val request =
                ImageRequest.Builder(context).data(url).allowHardware(false) // Required for Palette
                    .build()
            val drawable = Coil.imageLoader(context).execute(request).drawable
            val bitmap = drawable?.toBitmap()
            bitmap?.let {
                val palette = Palette.from(it).generate()
                val dominantColor = palette.getDominantColor(Color.Red.toArgb())
                color = Color(dominantColor)
            }
        } catch (e: Exception) {
            viewModel.logger("Failed to load palette color", e)
            color = Color.Gray
        }
    }
    return color
}