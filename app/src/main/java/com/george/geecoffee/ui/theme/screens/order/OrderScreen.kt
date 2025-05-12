package com.george.geecoffee.ui.theme.screens.order


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun OrderScreen(

    navController: NavController,
    onLogout: () -> Unit
) {
    val coffeeTypes = listOf("Espresso", "Americano", "Cappuccino")
    var selectedCoffeeType by remember { mutableStateOf(coffeeTypes[0]) }
    var expanded by remember { mutableStateOf(false) }
    var quantity by remember { mutableStateOf("1") }

    Scaffold(
      /*  topBar = {
            TopAppBar(
                title = { Text("Order Coffee") }
            )
        }*/
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
                text = "Place Your Order",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Coffee Type Dropdown
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Coffee Type",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedCoffeeType,
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
                        coffeeTypes.forEach { coffeeType ->
                            DropdownMenuItem(
                                text = { Text(coffeeType) },
                                onClick = {
                                    selectedCoffeeType = coffeeType
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Quantity Field
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Quantity",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = quantity,
                    onValueChange = {
                        // Only allow digits
                        if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                            quantity = it
                        }
                    },
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Buttons
            Button(
                onClick = {
                    // Navigate to payment screen with order details
                    navController.navigate("payment_screen/$selectedCoffeeType/$quantity")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Order Now")
            }

          OutlinedButton(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Logout")
            }
        }
    }
}

@Preview
@Composable
private fun OrderPrev() {
    OrderScreen(rememberNavController()) { }
    
}