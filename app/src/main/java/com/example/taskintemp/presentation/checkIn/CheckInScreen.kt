package com.example.taskintemp.presentation.checkIn

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.taskintemp.presentation.checkIn.components.CheckInActionContainer
import com.example.taskintemp.presentation.checkIn.components.CheckInDataContainer
import com.example.taskintemp.presentation.common.components.CustomProgressLoader
import com.example.taskintemp.presentation.common.ui.theme.ContainerBackgroundColor
import com.example.taskintemp.presentation.mainscreen.MainViewModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun CheckInScreen(viewModel: MainViewModel) {
    val state = viewModel.state.value
    val configuration = LocalConfiguration.current
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = ContainerBackgroundColor),
        contentAlignment = Alignment.Center
    ) {
        state.receivedResponse?.let {
            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                CheckInScreenPortrait(viewModel)
            } else {
                CheckInScreenLandscape(viewModel)
            }
        }
        if (state.isLoading || viewModel.operationLoading) {
            CustomProgressLoader()
        } else if (state.error.isNotEmpty()) {
            Text(
                text = state.error,
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CheckInScreenPortrait(
    viewModel: MainViewModel
) {
    val allCheckInsList = viewModel.allCheckInsList.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.sdp)
        ) {
            Spacer(modifier = Modifier.height(30.sdp))
            CheckInActionContainer(viewModel)
            Spacer(modifier = Modifier.height(22.sdp))

            if (allCheckInsList.value.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Spacer(modifier = Modifier.height(8.sdp))
                        Text(
                            text = "Most Recent",
                            color = MaterialTheme.colorScheme.surface,
                            fontSize = 12.ssp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Spacer(modifier = Modifier.height(8.sdp))
                    }

                    items(allCheckInsList.value) {
                        CheckInDataContainer(it)
                    }
                }
            } else {

                Column(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxHeight(.5f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No Check Ins Made Yet.",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 16.ssp
                    )
                }
            }
        }
    }
}


@Composable
fun CheckInScreenLandscape(
    viewModel: MainViewModel
) {
    val allCheckInsList = viewModel.allCheckInsList.collectAsState(initial = emptyList())

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.sdp)
        ) {
            CheckInActionContainer(viewModel)
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.sdp)
        ) {
            if (allCheckInsList.value.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {

                    item {
                        Spacer(modifier = Modifier.height(8.sdp))
                        Text(
                            text = "Most Recent",
                            color = MaterialTheme.colorScheme.surface,
                            fontSize = 12.ssp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Spacer(modifier = Modifier.height(8.sdp))
                    }

                    items(allCheckInsList.value) {
                        CheckInDataContainer(it)
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxHeight(.5f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No Check Ins Made Yet.",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 14.ssp
                    )
                }
            }
        }
    }
}