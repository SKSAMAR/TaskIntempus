package com.example.taskintemp.presentation.checkIn.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
    val state = viewModel.state.value
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.sdp),
        colors = CardDefaults.cardColors(containerColor = CardMainColor),
        shape = RoundedCornerShape(12.sdp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Text(
                text = state.receivedResponse?.detailedDate ?: "Time",
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.surface
            )

            Row(
                modifier = Modifier.fillMaxWidth().background(CardMainColorDark)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(modifier = Modifier.padding(vertical = 7.sdp) , text = "Tuesday", color = MaterialTheme.colorScheme.surface, fontSize = 18.ssp, fontWeight = FontWeight.ExtraBold)
            }
        }
    }
}