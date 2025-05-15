package com.yablonskyi.caelum.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp


val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 72.sp,
    ),
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TypographyPreview() {
    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier.align(Alignment.Center)
        ) {
            Text(
                "Some text",
                style = Typography.titleLarge,
            )
            Text(
                "Some text",
                style = Typography.bodyLarge
            )
            Text(
                "Some text",
                style = Typography.bodyMedium
            )
        }
    }
}