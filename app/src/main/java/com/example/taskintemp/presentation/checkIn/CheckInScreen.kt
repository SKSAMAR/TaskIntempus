package com.example.taskintemp.presentation.checkIn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.taskintemp.presentation.checkIn.components.CurrentDateInfoContent
import com.example.taskintemp.presentation.mainscreen.MainViewModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun CheckInScreen(viewModel: MainViewModel) {

    val state = viewModel.state.value
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        state.receivedResponse?.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.sdp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {
                    Spacer(modifier = Modifier.height(15.sdp))
                }

                item {
                    Text(
                        text = "Check In",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 18.ssp
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(25.sdp))
                }

                item {
                    CurrentDateInfoContent(viewModel)
                    Spacer(modifier = Modifier.height(15.sdp))
                    OutlinedButton(
                        onClick = {
                            viewModel.addCheckIn()
                        }
                    ) {
                        Text(text = "Submit")
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(15.sdp))
                }
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator()
        }
        else if (state.error.isNotEmpty()) {
            Text(
                text = state.error,
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}