package com.mohanjp.composebasics

import android.content.ClipboardManager
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberImagePainter
import com.mohanjp.composebasics.ui.theme.ComposeBasicsTheme
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

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
                    CopyImage(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun CopyImage(
    modifier: Modifier = Modifier
) {

    var filePath by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = filePath,
            onValueChange = { },
            label = { Text("Paste image path here") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Button(onClick = {

            val clipboardManager =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val item = clipboardManager.primaryClip?.getItemAt(0)
            val clipData = item?.uri

            val imageContentUri = clipData.toString()

            try {
                Uri.parse(imageContentUri) // Check if the URL is valid
                filePath = getFilePathFromUri(context, imageContentUri.toUri()) ?: ""

                println(filePath)

            } catch (e: Exception) {
                e.printStackTrace()
                println("exception: $e")
            }

        }) {
            Text(text = "Paste")
        }

        if (filePath.isNotBlank()) {
            Image(
                painter = rememberImagePainter(filePath),
                contentDescription = "Pasted Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
}


fun getFilePathFromUri(context: Context, uri: Uri): String? {
    val contentResolver: ContentResolver = context.contentResolver
    val inputStream: InputStream? = contentResolver.openInputStream(uri)

    if (inputStream != null) {
        val file = File(context.filesDir, "temp_file.jpg")

        val outputStream = FileOutputStream(file)
        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }

        return file.absolutePath
    }
    return null
}
