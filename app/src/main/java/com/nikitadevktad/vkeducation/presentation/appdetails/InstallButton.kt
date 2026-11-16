package com.nikitadevktad.vkeducation.presentation.appdetails

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nikitadevktad.vkeducation.presentation.theme.VkEducationTheme

@Composable
fun InstallButton(
    installState: InstallState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(vertical = 12.dp),
        modifier = modifier,
    ) {
        Column {
            Text(
                text = when (installState) {
                    InstallState.NotInstalled -> "Установить"
                    is InstallState.Installing -> "Отменить"
                    InstallState.Installed -> "Установлено"
                }
            )
            if (installState is InstallState.Installing) {
                LinearProgressIndicator(
                    progress = { installState.progress / 100f },
                    modifier = Modifier.fillMaxWidth(),
                )
                Text(text = "${installState.progress}%")
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    VkEducationTheme {
        InstallButton(
            installState = InstallState.NotInstalled,
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
