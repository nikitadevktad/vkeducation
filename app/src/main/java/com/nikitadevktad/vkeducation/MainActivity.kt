package com.nikitadevktad.vkeducation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.nikitadevktad.vkeducation.ui.theme.VkEducationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VkEducationTheme {
                NewApp()
            }
        }
    }
}

@Composable
fun NewApp(
    modifier: Modifier= Modifier,
) {

    var text by rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current

    var error by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier,
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
                error = false
            },
            label = {
                Text("Введите текст")
            },
            isError = error,
        )
        Button(
            onClick = {
                if(text.isBlank()) {
                    error = true
                } else {
                    val intent = Intent(
                        context,
                        NewActivity::class.java,
                    )

                    intent.putExtra("text", text)
                    context.startActivity(intent)
                }
            }
        ) {
            Text(
                text = "Открыть вторую Activity"
            )
        }
        Button(
            onClick = {
                if(text.isBlank()){
                    error = true
                } else {
                    val intentCall = Intent(
                        Intent.ACTION_DIAL,
                        Uri.parse("tel:$text")

                    )
                    context.startActivity(intentCall)
                }
            }
        ) {
            Text(
                text = "Позвонить другу"
            )
        }
        Button(
            onClick = {
                if(text.isBlank()){
                    error = true
                } else {
                    val intentShare = Intent(
                        Intent.ACTION_SEND
                    ).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, text)
                    }
                    context.startActivity(
                        Intent.createChooser(
                            intentShare,
                            "Поделиться"
                        )
                    )
                }
            }
        ) {
            Text("Поделиться текстом")
        }
    }
}