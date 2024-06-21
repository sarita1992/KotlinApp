package com.example.viesuretest.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.viesuretest.ui.DetailPage
import com.example.viesuretest.viewModel.UniConfigViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : ComponentActivity() {
    private val viewModel: UniConfigViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent { DetailPage(  intent.extras?.getString("data")) }

    }
}