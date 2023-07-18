package com.example.eventfinder.composable

import android.annotation.SuppressLint
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


class FilterBarComposable {
    @Composable
    fun FilterBar()
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
        )
        {
            Row(modifier = Modifier.weight(1f)) {
                CategorySelection()
            }
            Row(modifier = Modifier.weight(1f)) {
                DateSelection()
            }


        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CategorySelection() {
        val options = listOf<String>("All Categories", "Education", "Drink", "Cinema", "Dance", "Games")
        // state of the menu
        var expanded by remember {
            mutableStateOf(false)
        }

        // remember the selected item
        var selectedItem by remember {
            mutableStateOf(options[0])
        }

        //Dropdown
        Row( modifier = Modifier.fillMaxWidth()) {

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }) {
                TextField(
                    value = selectedItem,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false })
                {
                    options.forEach { item ->
                        DropdownMenuItem(text = { Text(item) },
                            onClick = {
                                selectedItem = item
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
            onClick = { openDatePickerDialog = !openDatePickerDialog })
        {
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
            val dateString = simpleDateFormat.format(selectedDate)
            Text(String.format("Date: %s", dateString))
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

private fun LocalDateTime.toMillis(): Long? {
    return this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}
