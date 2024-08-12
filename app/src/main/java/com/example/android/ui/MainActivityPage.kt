package com.example.android.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import coil.compose.rememberAsyncImagePainter
import com.example.android.model.BookData
import com.example.android.utils.Ascending
import com.example.android.utils.Constant
import com.example.android.view.DetailActivity
import com.example.android.viewModel.UniConfigViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Collections


@Composable
fun MainActivityPage(viewModel: UniConfigViewModel) {
    val usersState by viewModel.bookListUIState.collectAsState()
    val errorState by viewModel.showError.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getBookData()
    }

    if (errorState.isNotEmpty()) {
        GetData(errorState)
    } else if (usersState.isEmpty()) {
        ShowLoading()
    } else {
        Collections.sort<BookData>(
            usersState,
            Ascending()
        )
        StoreData(usersState)
        LazyColumn {
            items(usersState) { message ->
                MessageCard(message)
            }
        }
    }
}

@Composable
fun ShowLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
        )
    }
}


@Composable
fun ShowErrorMessage(errorState: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(
            text = errorState,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.displaySmall
        )
    }
}

@Composable
fun MessageCard(msg: BookData) {
    val context = LocalContext.current
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Card(shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(all = 6.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            onClick = {
                onSurfaceClick(
                    context, msg
                )
            }) {
            Box(
                modifier = Modifier.padding(2.dp),
            ) {
                Image(
                    painter = rememberAsyncImagePainter(msg.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier.padding(
                        start = 58.dp, top = 5.dp, end = 8.dp, bottom = 8.dp
                    )
                ) {
                    Text(
                        text = msg.title.toString(),
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )


                    Text(
                        text = msg.description.toString(),
                        modifier = Modifier.padding(all = 4.dp),
                        maxLines = 2,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}


fun onSurfaceClick(context: Context, bookData: BookData) {
    val json = Gson().toJson(bookData)
    val intnt = Intent(context, DetailActivity::class.java)
    intnt.putExtra("data", json)
    context.startActivity(intnt)
}


@Composable
fun StoreData(usersState: List<BookData>) {
    val context = LocalContext.current
    val json = Gson().toJson(usersState)
    // on below line we are storing data in shared preferences file.
    getSharedPreferences(context).edit().putString("data", json).apply()
}

@Composable
fun GetData(errorState: String) {
    val context = LocalContext.current

    val string = getSharedPreferences(context).getString("data", "")
    if(string?.isNotEmpty()!!){
    val type = object : TypeToken<ArrayList<BookData?>?>() {}.type
    val data = Gson().fromJson(string, type) as ArrayList<BookData>
        LazyColumn {
            items(data) { message ->
                MessageCard(message)
            }
        }
    } else {
        ShowErrorMessage(errorState)
    }
}

fun getSharedPreferences(context: Context): SharedPreferences {
    val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

    return EncryptedSharedPreferences.create(
        context,
        Constant.PREF_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}