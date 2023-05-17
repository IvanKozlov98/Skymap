package itmo.skymap.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/*
 * @author Ivan Kozlov
 */
@Composable
fun Gradient(modifier: Modifier, color: Color = Color(0xAA000000)) {
    Box( // gradient
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        color
                    )
                )
            )
    )
}