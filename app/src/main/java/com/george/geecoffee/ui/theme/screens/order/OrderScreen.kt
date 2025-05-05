package com.george.geecoffee.ui.theme.screens.order


import android.R.attr.checked
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.george.geecoffee.R


@Composable
fun OrderScreen(navController: NavHostController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally) {
        var context= LocalContext.current
//        var productdata=productviewmodel(navController,context)
        Spacer(modifier = Modifier.height(50.dp))


        Text(text = "Place Your Order",
            color = Color.Blue,
            fontFamily = FontFamily.Monospace,
            fontSize = 30.sp)
        Image(painter = painterResource(id=R.drawable.coffee), contentDescription = "fire")

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Coffee Type: ",)
        var expanded by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .padding(16.dp)
        ){
            IconButton(onClick = { expanded = !expanded} ){
                Icon(Icons.Default.MoreVert, contentDescription = "Select Here")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded =false}
            ) {
                DropdownMenuItem(
                    text = { Text("Capuccino")},
                    onClick = {"/TO DO"}
                )
                DropdownMenuItem(
                    text = { Text("Expresso")},
                    onClick = {"/TO DO"}
                )
            }
        }
        Text(text = "Toppings:")
        var  checked by remember { mutableStateOf(true) }
        Row (
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "Whipped Cream"
            )
            Checkbox(
                checked = checked,
                onCheckedChange = { checked =it}
            )
        }
        Text(
            if (checked) "Checkbox is checked" else "Checkbox is unchecked"
        )
        Text(
            "Whipped Cream"
        )
        Checkbox(
            checked = checked,
            onCheckedChange = { checked =it}
        )
    }







    }





private fun NavHostController.navigate(value: Any) {}

@Preview
@Composable
fun OrderPrev() {
    OrderScreen(rememberNavController())
}