package com.george.geecoffee.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.george.geecoffee.ui.theme.screens.Payment.PaymentScreen
import com.george.geecoffee.ui.theme.screens.homescreen.HomeScreen
import com.george.geecoffee.ui.theme.screens.manage.ManageOrdersScreen
import com.george.geecoffee.ui.theme.screens.order.OrderScreen

@Composable
fun AppNavHost(modifier: Modifier=Modifier,navController:NavHostController= rememberNavController(),startDestination:String= "home_screen") {

    NavHost(navController = navController, startDestination = "home_screen") {
        composable("home_screen") {
            HomeScreen(navController)
        }
        composable("order_screen") {
            OrderScreen(navController, onLogout = { /* handle logout */ })
        }
        composable(
            route = "payment_screen/{coffeeType}/{quantity}",
            arguments = listOf(
                navArgument("coffeeType") { type = NavType.StringType },
                navArgument("quantity") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val coffeeType = backStackEntry.arguments?.getString("coffeeType") ?: ""
            val quantity = backStackEntry.arguments?.getString("quantity") ?: "1"

            PaymentScreen(
                navController = navController,
                selectedCoffeeType = coffeeType,
                quantity = quantity
            )
        }
         composable("manage_orders_screen") {
            ManageOrdersScreen(navController)
        }
    }
}