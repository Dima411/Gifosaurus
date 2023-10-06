package com.example.gifosaurus.ui.screens.detailscreen

import android.content.Intent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.gifosaurus.config.Icons.iconShare

/**
 * The function creates a share button with an icon
 */
@Composable
fun ShareButton(gifUrl: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Button(
        onClick = {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, gifUrl)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        },
        modifier = modifier,
        shape = ShapeDefaults.ExtraSmall,
        colors = ButtonDefaults.buttonColors(Color.Transparent)
    ) {
        Icon(imageVector = iconShare, contentDescription = null, tint = Color.Black)
    }
}
