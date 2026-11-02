package com.nikitadevktad.vkeducation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.core.view.KeyEventDispatcher

class NewActivity: ComponentActivity(){
    override fun onCreate(savedInstantState: Bundle?) {
        super.onCreate(savedInstantState)

        setContent {
            val text = intent.getStringExtra("text")
            Text(text ?:"")
        }
    }
}