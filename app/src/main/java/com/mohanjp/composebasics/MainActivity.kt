package com.mohanjp.composebasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohanjp.composebasics.ui.theme.ComposeBasicsTheme
import kotlin.properties.Delegates

class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeBasicsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NameListScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeBasicsTheme {
        NameListScreen()
    }
}

@Composable
fun NameListScreen() {
    var names by remember {
        mutableStateOf(listOf<String>())
    }

    var name by remember {
        mutableStateOf("")
    }

    println("Value changed >>")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f),
                value = name,
                onValueChange = { text ->
                    name = text
                }
            )

            Spacer(
                modifier = Modifier
                    .width(15.dp)
            )

            Button(onClick = {
                if(name.isNotBlank()) {
                    names += name
                    //name = ""
                }
            }) {
                Text(text = "Add to List")
            }
        }

        ShowNameList(names = names)
    }
}

@Composable
fun ShowNameList(
    names: List<String>,
    modifier: Modifier = Modifier
) {
    println("lazy column function called")

    LazyColumn(modifier) {

        items(names) { name ->
            println("rendering item")

            Text(
                text = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Divider()
        }
    }
}