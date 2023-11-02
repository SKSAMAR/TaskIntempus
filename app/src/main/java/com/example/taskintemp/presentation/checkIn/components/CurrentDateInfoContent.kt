package com.example.taskintemp.presentation.checkIn.components

import android.os.Build
import android.widget.TimePicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.taskintemp.presentation.common.ui.theme.CardMainColor
import com.example.taskintemp.presentation.common.ui.theme.CardMainColorDark
import com.example.taskintemp.presentation.mainscreen.MainViewModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun CurrentDateInfoContent(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    Column {
        AndroidView(
            factory = { context ->
                TimePicker(context).apply {
                    viewModel.modifyTimePicker(this)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(15.sdp))
        AnimatedVisibility(viewModel.invalidTimeMessage.isNotEmpty()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = viewModel.invalidTimeMessage,
                textAlign = TextAlign.Center,
                fontSize = 12.ssp,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}