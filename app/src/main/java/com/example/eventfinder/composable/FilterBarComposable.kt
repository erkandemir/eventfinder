package com.example.eventfinder.composable

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eventfinder.model.EventCategoryModel
import com.example.eventfinder.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*



class FilterBarComposable(private val viewModel: MainViewModel) {
    @Composable
    fun FilterBar()
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .background(color = MaterialTheme.colorScheme.background)
        )
        {
            Row(modifier = Modifier.weight(0.4f)) {
                CategorySelection()
            }
            Row(modifier = Modifier.weight(0.4f)) {
                DateSelection()
            }
            Row(modifier = Modifier.weight(0.2f)) {
                Button(onClick = {
                    viewModel.resetFilter()

                }) {
                    Text(text = "Reset", fontSize = 10.sp)
                }
            }
        }
        Spacer(modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .height(2.dp)
            .fillMaxWidth())
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CategorySelection() {

        // state of the menu
        var expanded by remember {
            mutableStateOf(false)
        }

        val eventCategoryList = viewModel.eventCategoryListState.value
        var selectedItemValue by remember {
            mutableStateOf(viewModel.selectedEventCategoryModel.value!!.name)
        }

        //Dropdown
        Row( modifier = Modifier
            .fillMaxWidth()) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                OutlinedTextField(
                    value = viewModel.selectedEventCategoryModel.value!!.name,
                    onValueChange = {
                    },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    textStyle = TextStyle.Default.copy(fontSize = 12.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.background,
                        unfocusedBorderColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedTextColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .menuAnchor()

                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                )
                {
                    eventCategoryList.forEach { item ->
                        DropdownMenuItem(text = { Text(item.name) },
                            onClick = {
                                selectedItemValue = item.name
                                viewModel.selectedEventCategoryModel.value = item
                                viewModel.getEvents()
                                expanded = false
                            })
                    }
                }

            }
        }
    }

    @SuppressLint("UnrememberedMutableState")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DateSelection()
    {
        val dateTime = LocalDateTime.now()
        var selectedDate by remember {
            mutableStateOf(dateTime.toMillis())
        }

        var openDatePickerDialog by remember { mutableStateOf(false) }
        TextButton(
            onClick = { openDatePickerDialog = !openDatePickerDialog },
        )
        {
            Text(String.format("Date: %s", viewModel.selectedDate.value))
        }

        if(openDatePickerDialog) {

            val datePickerState = remember {
                DatePickerState(
                    yearRange = (2023..2050),
                    initialSelectedDateMillis = dateTime.toMillis(),
                    initialDisplayMode = DisplayMode.Picker,
                    initialDisplayedMonthMillis = null
                )
            }

            val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }
            val mDatePickerDialog = DatePickerDialog(
                onDismissRequest = { openDatePickerDialog = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            selectedDate = datePickerState.selectedDateMillis
                            openDatePickerDialog = false
                            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
                            val dateString = simpleDateFormat.format(selectedDate)
                            viewModel.selectedDate.value = dateString
                            viewModel.dateSelected.value = true
                            viewModel.getEvents()

                        }, enabled = confirmEnabled.value
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDatePickerDialog = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    showModeToggle = openDatePickerDialog
                )
            }
        }
   }

}

fun LocalDateTime.toMillis(): Long? {
    return this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}
