package com.nicolas.weatherapp.ui.screens.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.nicolas.weatherapp.R

@Composable
fun UnexpectedError() {
    var isAlertDialogVisible by remember { mutableStateOf(true) }

    if (isAlertDialogVisible) {
        AlertDialog(
            onDismissRequest = {
                isAlertDialogVisible = false
            },
            title = {
                Text(text = stringResource(R.string.text_title_button_unexpected_error_elements_visuals))
            },
            text = {
                Text(text = stringResource(R.string.text_body_button_unexpected_error_elements_visuals))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        isAlertDialogVisible = false
                    }
                ) {
                    Text(text = stringResource(R.string.text_button_dialog_understood))
                }
            }
        )
    }
}