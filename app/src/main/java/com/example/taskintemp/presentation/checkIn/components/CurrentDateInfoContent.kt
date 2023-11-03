package com.example.taskintemp.presentation.checkIn.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.taskintemp.R
import com.example.taskintemp.data.db.entity.Employee
import com.example.taskintemp.data.remote.dto.toDateModel
import com.example.taskintemp.presentation.common.ui.theme.Grey
import com.example.taskintemp.presentation.mainscreen.MainViewModel
import com.example.taskintemp.util.AppUtils.showDatePickerDialog
import com.example.taskintemp.util.AppUtils.showTimePickerDialog
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun CheckInActionContainer(
    viewModel: MainViewModel
) {
    val dateTime = viewModel.selectedTimeDto?.toDateModel()
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        val context = LocalContext.current
        OutlinedCard(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = CardDefaults.outlinedCardColors(containerColor = Color.Transparent),
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.sdp, vertical = 12.sdp)
                    .align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Begin Check In", color = Grey, fontSize = 14.ssp)
                Spacer(modifier = Modifier.height(10.sdp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.clickable {
                            context.showTimePickerDialog(
                                hour = dateTime?.hour ?: 0,
                                minute = dateTime?.minute ?: 0
                            ) { hour, minute ->
                                viewModel.validateDateSelected(dateTime?.date?:"" ,hour, minute)
                            }
                        },
                        text = "${dateTime?.time}",
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.ssp
                    )
                    Spacer(modifier = Modifier.width(5.sdp))
                    Text(
                        modifier = Modifier.clickable {
                            context.showDatePickerDialog(
                                dDay = dateTime?.day ?: 0,
                                dMonth = dateTime?.month ?: 0,
                                dYear = dateTime?.year ?: 0
                            ) { date ->
                                viewModel.validateDateSelected(dateSelected = date, hours = dateTime?.hour ?: 0, minutes = dateTime?.minute?:0 )
                            }
                        },
                        text = dateTime?.date ?: "",
                        color = Color.White,
                        fontSize = 12.ssp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Spacer(modifier = Modifier.height(7.sdp))
                AnimatedVisibility(visible = viewModel.invalidTimeMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(7.sdp))
                    Text(
                        text = viewModel.invalidTimeMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 10.ssp
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(7.sdp))
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(.65f)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(8.sdp),
            onClick = {
                viewModel.addCheckIn()
            }
        ) {
            Text(text = stringResource(id = R.string.submit))
        }
    }
}


@Composable
fun CheckInDataContainer(
    employee: Employee
) {
    val (date, time) = employee.check_in_date.split(" ")
    Column {
        Card(
            shape = RoundedCornerShape(12.sdp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(.1f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.sdp, horizontal = 10.sdp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = time, fontSize = 18.ssp, color = Color.White)
                Text(text = date, fontSize = 12.ssp, color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(12.sdp))
    }
}
