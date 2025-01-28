package com.hb.test.utils

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Dark mode Landscape",
    group = "UI mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    widthDp = 720,
    heightDp = 360,
    showBackground = true
)
@Preview(
    name = "Phone Light Mode",
    group = "UI mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    device = Devices.PIXEL_4_XL,
    widthDp = 360,
    heightDp = 640,
    showBackground = true
)
@Preview(
    name = "Phone Dark Mode",
    group = "UI mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    device = Devices.PIXEL_4_XL,
    widthDp = 360,
    heightDp = 640,
    showBackground = true
)
annotation class DarkAndLightPreviews
