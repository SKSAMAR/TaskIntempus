package com.example.taskintemp.presentation.common.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ir.kaaveh.sdpcompose.sdp

@Composable
fun CustomProgressLoader(){
    Card(
        shape = RoundedCornerShape(12.sdp),
        elevation = CardDefaults.cardElevation(12.sdp)
    ) {
        CircularProgressIndicator(modifier = Modifier.padding(32.sdp))
    }
}