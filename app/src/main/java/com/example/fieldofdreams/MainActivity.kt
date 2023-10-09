@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.fieldofdreams

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fieldofdreams.ui.theme.FieldOfDreamsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "greeting") {
                composable("greeting") {
                    Greeting {
                        navController.navigate("mainScreen")
                    }
                }
                composable("mainScreen") {
                    MainScreen()
                }
            }
        }
    }
}


@Composable
fun MainScreen() {
    FieldOfDreamsTheme {
        val service = Service.Words()
        val (first, second) = service.getWord()
        val word = remember {
            mutableStateOf(first)
        }
        val encryptedWord = remember {
            mutableStateOf(service.getEncryptedWord(first))
        }
        val meaning = remember {
            mutableStateOf(second)
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            ) {
            Box (modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxHeight(0.3f)) {
                Text(
                    modifier = Modifier.padding(20.dp),
                    text = meaning.value,
                    textAlign = TextAlign.Justify
                )
            }
            Box (modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxHeight(0.8f)) {
                Column (horizontalAlignment = Alignment.CenterHorizontally){
                    Text(
                        modifier = Modifier.padding(20.dp),
                        text = encryptedWord.value,
                        fontSize = 40.sp
                        )
                    Row {
                        val text = remember {
                            mutableStateOf("")
                        }
                        TextField(
                            value = text.value,
                            onValueChange = {
                                if (it.length < 2) text.value = it
                            },
                            modifier = Modifier.width(44.dp))
                        Button(onClick = {
                            val letter = text.value[0]
                            if (service.checkLetter(word.value, letter)) {
                                encryptedWord.value = service.openLetter(
                                    encryptedWord.value, word.value, letter
                                )
                            }
                            text.value = ""
                        },
                            modifier = Modifier.padding(start = 10.dp, top = 4.dp)
                        ) {
                            Text(text = "Проверить")
                        }
                    }
                }
            }
            Box(modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxHeight()) {
                Button(
                    modifier = Modifier.padding(20.dp),
                    onClick = {
                        val (first, second) = service.getWord()
                        word.value = first
                        meaning.value = second
                        encryptedWord.value = service.getEncryptedWord(word.value)
                    }
                ) {
                    Text(text = "Поменять слово")
                }
            }
        }
    }
}

@Composable
fun Greeting(onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column (horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Добро пожаловать на Поле Чудес!")
            Button(onClick = { onClick() }) {
                Text(text = "Начать игру")
            }
        }
    }
}
