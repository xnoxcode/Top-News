package epm.xnox.topnews.ui.component

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import epm.xnox.topnews.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    show: Boolean,
    context: Context,
    initialDate: String,
    onDateSelected: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    if (show) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = converterDateToMillis(initialDate)
        )

        DatePickerDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onClick = {
                    val selectedDate = Calendar.getInstance().apply {
                        timeInMillis = datePickerState.selectedDateMillis!!
                    }
                    if (selectedDate.before(Calendar.getInstance())) {
                        val formatter =
                            SimpleDateFormat(
                                "yyyy-MM-dd",
                                Locale.getDefault()
                            ).apply {
                                timeZone = TimeZone.getTimeZone("UTC")
                            }
                        onDateSelected(formatter.format(selectedDate.time))
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.message_select_date_before),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }) {
                    Text(stringResource(id = R.string.dialog_btn_ok))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(stringResource(id = R.string.dialog_btn_cancel))
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                title = {
                    Text(
                        text = stringResource(id = R.string.dialog_date_picker_title),
                        fontSize = 18.sp, textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp)
                    )
                }
            )
        }
    }
}

fun converterDateToMillis(dateString: String): Long? {
    return try {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date: Date = formatter.parse(dateString) ?: return null
        date.time
    } catch (e: Exception) {
        return null
    }
}