package com.george.geecoffee.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.george.geecoffee.ui.theme.screens.Payment.PaymentScreen
import com.george.geecoffee.ui.theme.screens.homescreen.HomeScreen
import com.george.geecoffee.ui.theme.screens.manage.ManageOrdersScreen
import com.george.geecoffee.ui.theme.screens.order.OrderScreen


/**
 * Sealed class that contains all the routes for the app
 */
sealed class Routes(val route: String) {
    object Home : Routes("home_screen")
    object Order : Routes("order_screen")
    object Payment : Routes("payment_screen/{coffeeType}/{quantity}") {
        fun createRoute(coffeeType: String, quantity: String): String {
            return "payment_screen/$coffeeType/$quantity"
        }
    }
    object ManageOrders : Routes("manage_orders_screen")
}

/**
 * Navigation graph for the app
 */
@Composable
fun SetupNavigation(
    navController: NavHostController,
    onLogout: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home.route
    ) {
        composable(Routes.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Routes.Order.route) {
            OrderScreen(
                navController = navController,
                onLogout = onLogout
            )
        }

        composable(
            route = Routes.Payment.route,
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

        composable(Routes.ManageOrders.route) {
            ManageOrdersScreen(navController = navController)
        }
    }
}

/**
 * Navigation actions for the app
 * This class provides methods to navigate to different screens
 */
class NavigationActions(private val navController: NavHostController) {

    fun navigateToHome() {
        navController.navigate(Routes.Home.route) {
            popUpTo(Routes.Home.route) { inclusive = true }
        }
    }

    fun navigateToOrder() {
        navController.navigate(Routes.Order.route)
    }

    fun navigateToPayment(coffeeType: String, quantity: String) {
        navController.navigate(Routes.Payment.createRoute(coffeeType, quantity))
    }

    fun navigateToManageOrders() {
        navController.navigate(Routes.ManageOrders.route) {
            popUpTo(Routes.Home.route)
        }
    }

    fun navigateBack() {
        navController.popBackStack()
    }

    fun logout() {
        navController.navigate(Routes.Home.route) {
            popUpTo(0) { inclusive = true }
        }
    }
}
