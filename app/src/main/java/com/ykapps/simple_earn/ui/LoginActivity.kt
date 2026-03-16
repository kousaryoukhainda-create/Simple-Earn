package com.ykapps.simple_earn.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModelProvider
import com.ykapps.simple_earn.viewmodel.AuthViewModel

class LoginActivity : ComponentActivity() {
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        setContent {
            var showLogin by remember { mutableStateOf(true) }

            // Observe login state
            val isLoggedIn by authViewModel.isLoggedIn.observeAsState(false)
            
            LaunchedEffect(isLoggedIn) {
                if (isLoggedIn) {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
            }

            if (showLogin) {
                LoginScreen(
                    onLoginClick = { email, password ->
                        authViewModel.login(email, password)
                    },
                    onSignUpClick = { showLogin = false }
                )
            } else {
                SignUpScreen(
                    onSignUpClick = { email, password, name ->
                        authViewModel.signUp(email, password, name)
                    },
                    onLoginClick = { showLogin = true }
                )
            }
        }
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
}

class SignUpActivity : ComponentActivity() {
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        setContent {
            var showLogin by remember { mutableStateOf(false) }

            // Observe login state
            val isLoggedIn by authViewModel.isLoggedIn.observeAsState(false)
            
            LaunchedEffect(isLoggedIn) {
                if (isLoggedIn) {
                    startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                    finish()
                }
            }

            if (showLogin) {
                LoginScreen(
                    onLoginClick = { email, password ->
                        authViewModel.login(email, password)
                    },
                    onSignUpClick = { showLogin = false }
                )
            } else {
                SignUpScreen(
                    onSignUpClick = { email, password, name ->
                        authViewModel.signUp(email, password, name)
                    },
                    onLoginClick = { showLogin = true }
                )
            }
        }
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }
}

// Extension function for observeAsState
@Composable
private fun <T> androidx.lifecycle.LiveData<T>.observeAsState(initial: T? = null): State<T?> {
    val state = remember { mutableStateOf(initial) }
    DisposableEffect(this) {
        val observer = androidx.lifecycle.Observer<T> { value ->
            state.value = value
        }
        this@observeAsState.observeForever(observer)
        onDispose {
            this@observeAsState.removeObserver(observer)
        }
    }
    return state
}
