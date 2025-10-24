package com.embot.immfly_store.ui.components.displayer

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.embot.immfly_store.ui.theme.GreenColor


@Composable
fun InfoActionPopUp(
    title: String,
    message: String,
    confirmText: String,
    cancelText: String = "",
    isSucceed: Boolean? = null,
    isSingleAction: Boolean = false,
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = { Text(text = message) },
        titleContentColor = if (isSucceed == true) GreenColor else Color.Black,
        containerColor = Color.White,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = confirmText)
            }
        },
        dismissButton = {
            if(!isSingleAction) {
                TextButton(onClick = onDismiss) {
                    Text(cancelText)
                }
            }
        }
    )
}

@Preview(showBackground = false)
@Composable
fun InfoActionPopUpPreview() {
    InfoActionPopUp(
        title = "Title",
        message = "Message",
        confirmText = "Confirm",
        cancelText = "Cancel",
        onConfirm = {},
        onDismiss = {},
    )
}