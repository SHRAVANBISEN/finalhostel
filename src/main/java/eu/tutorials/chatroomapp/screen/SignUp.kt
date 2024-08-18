package eu.tutorials.chatroomapp.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import eu.tutorials.chatroomapp.data.Result
import eu.tutorials.chatroomapp.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel,
    onNavigateToLogin: () -> Unit
){

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var hostelName by remember { mutableStateOf("") }
    var roomNumber by remember { mutableStateOf("") }
    val authResult by authViewModel.authResult.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
        OutlinedTextField(
            value = hostelName,
            onValueChange = { hostelName = it },
            label = { Text("Hostel Name") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
        OutlinedTextField(
            value = roomNumber,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    roomNumber = it
                }
            },
            label = { Text("Room Number") },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Button(
            onClick = {
                authViewModel.signUp(email, password, firstName, hostelName, roomNumber)
                email = ""
                password = ""
                firstName = ""
                hostelName = ""
                roomNumber = ""
            },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Sign Up")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Already have an account? Sign in.",
            modifier = Modifier.clickable { onNavigateToLogin() }
        )

        authResult?.let { result ->
            when (result) {
                is Result.Success -> {
                    Text("Registration successful")
                }
                is Result.Error -> {
                    Text("Registration failed")
                }
            }
        }
    }
}
