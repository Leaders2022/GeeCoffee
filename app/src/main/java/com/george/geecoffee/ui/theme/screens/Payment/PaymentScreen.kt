package com.george.geecoffee.ui.theme.screens.Payment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    navController: NavController,
    selectedCoffeeType: String = "",
    quantity: String = "1"
) {
    val paymentMethods = listOf("MPESA", "AIRTEL MONEY", "CASH")
    var selectedPaymentMethod by remember { mutableStateOf(paymentMethods[0]) }
    var expanded by remember { mutableStateOf(false) }

    // Calculate total amount based on coffee type and quantity
    val priceMap = mapOf(
        "Espresso" to 100,
        "Americano" to 120,
        "Cappuccino" to 150
    )

    val unitPrice = priceMap[selectedCoffeeType] ?: 100
    val totalAmount = try {
        unitPrice * quantity.toInt()
    } catch (e: NumberFormatException) {
        unitPrice
    }

    var totalAmountText by remember { mutableStateOf(totalAmount.toString()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Payment") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Complete Your Payment",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Order Details
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Order Summary",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Coffee Type:")
                        Text(selectedCoffeeType, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Quantity:")
                        Text(quantity, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Unit Price:")
                        Text("KSH $unitPrice", fontWeight = FontWeight.Bold)
                    }
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total Amount:", fontWeight = FontWeight.Bold)
                        Text("KSH $totalAmount", fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Total Amount Field
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Total Amount",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = totalAmountText,
                    onValueChange = { totalAmountText = it },
                    readOnly = true,
                    prefix = { Text("KSH ") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Payment Method Dropdown
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Payment Method",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedPaymentMethod,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        paymentMethods.forEach { paymentMethod ->
                            DropdownMenuItem(
                                text = { Text(paymentMethod) },
                                onClick = {
                                    selectedPaymentMethod = paymentMethod
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Pay Now Button
            Button(
                onClick = {
                    // Process payment and navigate to manage orders screen
                    navController.navigate("manage_orders_screen") {
                        popUpTo("home_screen") // Navigate back to home and clear backstack
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Pay Now")
            }
        }
    }
}


@Preview
@Composable
private fun PayPrev() {
    PaymentScreen(rememberNavController())

}