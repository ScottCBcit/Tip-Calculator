package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TipCalculator()
                }
            }
        }
    }
}



@Composable
fun TipCalculator() {
    var billAmount by remember { mutableStateOf("") }
    var selectedTipPercentage by remember { mutableStateOf(0.15f) }
    var tipAmount by remember { mutableStateOf(0f) }

    fun updateValues() {
        val bill = if (billAmount.isEmpty()) 0f else billAmount.toFloat()
        val tip = bill * selectedTipPercentage
        val total = bill + tip
        tipAmount = tip
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = billAmount,
            onValueChange = { billAmount = it
                                updateValues()},
            label = { Text(stringResource(R.string.service_cost)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TipPercentageButton(text = "10%", isSelected = selectedTipPercentage == 0.10f) {
                selectedTipPercentage = 0.10f
                updateValues()
            }
            TipPercentageButton(text = "15%", isSelected = selectedTipPercentage == 0.15f) {
                selectedTipPercentage = 0.15f
                updateValues()
            }
            TipPercentageButton(text = "18%", isSelected = selectedTipPercentage == 0.18f) {
               selectedTipPercentage = 0.18f
                updateValues()
            }
            TipPercentageButton(text = "20%", isSelected = selectedTipPercentage == 0.20f) {
               selectedTipPercentage = 0.20f
                updateValues()
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                billAmount = (5..200).random()
                    .toString() // generate a random number between 5 and 200 and set it to billAmount
                updateValues()
            },

        ) {
            Text(text = "Generate Bill")
        }

        if (tipAmount > 0) {
            // Convert the billAmount string to a float, or use 0 if the conversion fails
            val bill = billAmount.toFloatOrNull() ?: 0f

            // Calculate the total after adding the tip
            val totalAfterTip = bill * (1 + selectedTipPercentage)

            Spacer(modifier = Modifier.height(16.dp))

            // Display the bill amount
            Text(
                text = stringResource(R.string.bill_amount, String.format("%.2f", bill)),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground
            )

            // Display the tip amount
            Text(
                text = stringResource(R.string.tip_amount, String.format("%.2f", tipAmount)),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary
            )

            // Display the tip percentage
            Text(
                text = stringResource(R.string.tip_percent, String.format("%.2f%%",
                    selectedTipPercentage * 100)),
                fontSize = 16.sp,
                color = MaterialTheme.colors.onBackground
            )

            // Add a divider line
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider(
                    color = MaterialTheme.colors.primary,
                    thickness = 2.dp,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            // Display the total amount after adding the tip
            Text(
                text = stringResource(R.string.total_amount, String.format("%.2f", totalAfterTip)),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

    }
}


@Composable
fun TipPercentageButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    val contentColor = if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        ),
        modifier = Modifier.width(80.dp)
    ) {
        Text(text = text, fontSize = 14.sp)
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        TipCalculator()
    }
}