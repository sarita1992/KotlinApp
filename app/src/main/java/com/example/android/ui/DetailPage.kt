package com.example.android.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.android.model.BookData
import com.example.android.utils.DateTime
import com.google.gson.Gson

@Composable
fun DetailPage(string: String?) {

    val data = Gson().fromJson(string, BookData::class.java)
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        ImageCardData(data.image.toString())
        Column(
            modifier = Modifier.fillMaxSize().padding(10.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            data.title?.let {
                Text(
                    text = it,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.displaySmall
                )
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text =  DateTime.getFormattedDate(data.release_date.toString()),
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.TopEnd),
                    style = MaterialTheme.typography.labelMedium,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f, false)

            ) {
                Text(
                    text = data.description.toString(),
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium,
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize =20.sp)) {
                            append("Author: ")
                        }
                        append(data.author.toString() )
                    },
                    color = Color.Black,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }
}

@Composable
fun ImageCardData(image: String) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)

    ) {
        Image(
            painter = rememberAsyncImagePainter(image),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}
