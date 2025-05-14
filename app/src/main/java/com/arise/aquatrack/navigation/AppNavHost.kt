package com.arise.aquatrack.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arise.aquatrack.screens.Dashboard.DashboardScreen
import com.arise.aquatrack.screens.home.Home_Screen
import com.arise.aquatrack.screens.login.Login_Screen
import com.arise.aquatrack.screens.products.AddProductsScreen
import com.arise.aquatrack.screens.products.UpdateProductsScreen
import com.arise.aquatrack.screens.products.ViewProductsScreen
import com.arise.aquatrack.screens.register.RegisterScreen
import com.arise.aquatrack.screens.splash.SplashScreen
import com.arise.aquatrack.screens.client.AddClientScreen // Updated for clients
import com.arise.aquatrack.screens.client.UpdateClientScreen // Updated for clients
import com.arise.aquatrack.screens.products.AddEquipmentScreen
import com.arise.aquatrack.screens.viewClientScreen.ViewClientsScreen
//import com.arise.aquatrack.screens.client.ViewClientScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_DASHBOARD
) {
    NavHost(
        modifier = modifier,
        startDestination = startDestination,
        navController = navController
    ) {
        composable(ROUTE_HOME) {
            Home_Screen(navController)
        }
        composable(ROUTE_LOGIN) {
            Login_Screen(navController)
        }
        composable(ROUTE_REGISTER) {
            RegisterScreen(navController)
        }
        composable(ROUTE_SPLASH_SCREEN) {
            SplashScreen(navController)
        }
        composable(ROUTE_ADD_PRODUCT) {
            AddProductsScreen(navController)
        }
//        composable(ROUTE_EQUIPMENT) {
//            EquipmentScreen(navController)
//        }
        composable(ROUTE_ADD_EQUIPMENT) {
            AddEquipmentScreen(navController)
        }
        composable(ROUTE_VIEW_PRODUCT) {
            ViewProductsScreen(navController)
        }
        composable(ROUTE_UPDATE_PRODUCT + "/{id}") { passedData ->
            UpdateProductsScreen(navController, passedData.arguments!!.getString("id")!!)
        }
        composable(ROUTE_DASHBOARD) { DashboardScreen(navController) }

        // Updated routes for client management
        composable(ROUTE_ADD_CLIENT) {
            AddClientScreen(navController) // Screen to add a client
        }
        composable(ROUTE_VIEW_CLIENTS) {
            ViewClientsScreen(navController) // Screen to view all clients
        }
        composable("$ROUTE_UPDATE_CLIENT/{clientId}") { passedData ->
            UpdateClientScreen(
                navController, passedData.arguments?.getString("clientId")!!
            ) // Screen to update client details
        }
    }
}
