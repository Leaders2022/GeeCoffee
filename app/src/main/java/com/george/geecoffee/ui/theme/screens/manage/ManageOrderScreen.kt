package com.george.geecoffee.ui.theme.screens.manage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

// Data class to represent a coffee order
data class CoffeeOrder(
    val phone: String,
    val coffeeType: String,
    val quantity: Int,
    val amount: Int,
    val paymentMethod: String,
    var delivered: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageOrdersScreen(navController: NavController) {
    // Sample data - in a real app, this would come from a database or API
    val ordersList = remember {
        mutableStateListOf(
            CoffeeOrder("0712345678", "Espresso", 2, 200, "MPESA", true),
            CoffeeOrder("0723456789", "Americano", 1, 120, "CASH", false),
            CoffeeOrder("0734567890", "Cappuccino", 3, 450, "AIRTEL MONEY", false),
            CoffeeOrder("0745678901", "Espresso", 1, 100, "MPESA", true),
            CoffeeOrder("0756789012", "Cappuccino", 2, 300, "CASH", false)
        )
    }

    var searchPhoneNumber by remember { mutableStateOf("") }
    var searchedOrder by remember { mutableStateOf<CoffeeOrder?>(null) }

    var isDeliveredSwitchState by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Orders") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("home_screen") {
                            popUpTo("home_screen") { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Filled.Home, contentDescription = "Home")
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
        ) {
            // Orders Table
            Text(
                text = "Coffee Orders",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Table Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(8.dp)
            ) {
                Text(
                    text = "Phone",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(100.dp)
                )
                Text(
                    text = "Coffee",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(80.dp)
                )
                Text(
                    text = "Qty",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(40.dp)
                )
                Text(
                    text = "Amount",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(70.dp)
                )
                Text(
                    text = "Payment",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(100.dp)
                )
                Text(
                    text = "Delivered",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(80.dp)
                )
            }

            // Table Content
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .border(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                items(ordersList) { order ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = order.phone,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(100.dp)
                        )
                        Text(
                            text = order.coffeeType,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(80.dp)
                        )
                        Text(
                            text = order.quantity.toString(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(40.dp)
                        )
                        Text(
                            text = order.amount.toString(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(70.dp)
                        )
                        Text(
                            text = order.paymentMethod,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(100.dp)
                        )
                        Text(
                            text = if (order.delivered) "True" else "False",
                            textAlign = TextAlign.Center,
                            color = if (order.delivered) Color.Green else Color.Red,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.width(80.dp)
                        )
                    }
                    Divider()
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Order Management Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Order Management",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )

                    // Search by Phone Number
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = searchPhoneNumber,
                            onValueChange = { searchPhoneNumber = it },
                            label = { Text("Phone Number") },
                            modifier = Modifier.weight(1f),
                            leadingIcon = {
                                Icon(Icons.Default.Search, contentDescription = "Search")
                            }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                searchedOrder = ordersList.find { it.phone == searchPhoneNumber }
                                if (searchedOrder != null) {
                                    isDeliveredSwitchState = searchedOrder!!.delivered
                                }
                            },
                            modifier = Modifier.width(100.dp)
                        ) {
                            Text("Search")
                        }
                    }

                    // Show searched order details and update options
                    searchedOrder?.let { order ->
                        Divider(modifier = Modifier.padding(vertical = 8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Coffee: ${order.coffeeType}")
                                Text("Quantity: ${order.quantity}")
                                Text("Amount: KSH ${order.amount}")
                                Text("Payment: ${order.paymentMethod}")
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("Delivered")
                                Switch(
                                    checked = isDeliveredSwitchState,
                                    onCheckedChange = { isDeliveredSwitchState = it }
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = {
                                    val index = ordersList.indexOfFirst { it.phone == searchPhoneNumber }
                                    if (index != -1) {
                                        ordersList[index] = ordersList[index].copy(delivered = isDeliveredSwitchState)
                                        searchedOrder = ordersList[index]
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text("Update")
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Button(
                                onClick = {
                                    ordersList.removeIf { it.phone == searchPhoneNumber }
                                    searchPhoneNumber = ""
                                    searchedOrder = null
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ManagePrev() {
    ManageOrdersScreen(rememberNavController())
    
}