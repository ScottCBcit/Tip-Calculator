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

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        TextField(
            value = billAmount,
            onValueChange = { billAmount = it },
            label = { Text(stringResource(R.string.service_cost)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TipPercentageButton(text = "10%", isSelected = selectedTipPercentage == 0.15f) {
                selectedTipPercentage = 0.15f
            }
            TipPercentageButton(text = "15%", isSelected = selectedTipPercentage == 0.18f) {
                selectedTipPercentage = 0.18f
            }
            TipPercentageButton(text = "18%", isSelected = selectedTipPercentage == 0.20f) {
               selectedTipPercentage = 0.20f
            }
            TipPercentageButton(text = "20%", isSelected = selectedTipPercentage == 0.25f) {
               selectedTipPercentage = 0.25f
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val bill = billAmount.toFloatOrNull() ?: 0f
                val tip = bill * selectedTipPercentage
                val total = bill + tip
                tipAmount = tip // Update the tipAmount state
            },
            modifier = Modifier.align(Alignment.End)

        ) {
            Text(text = "Calculate Tip")
        }

        // Show the tip amount if it's greater than zero
        if (tipAmount > 0) {
            Text(
                text = stringResource(R.string.tip_amount, String.format("%.2f", tipAmount)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .background(MaterialTheme.colors.primaryVariant)
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
                    .defaultMinSize(minHeight = 48.dp)
                    .wrapContentSize(align = Alignment.Center)
                    .then(Modifier.sizeIn(minHeight = 48.dp)),
                color = MaterialTheme.colors.onPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
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