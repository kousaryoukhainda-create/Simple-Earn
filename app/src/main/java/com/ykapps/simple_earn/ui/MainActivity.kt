package com.ykapps.simple_earn.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.MobileAds
import com.ykapps.simple_earn.viewmodel.*

class MainActivity : ComponentActivity() {
    private lateinit var authViewModel: AuthViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var earnViewModel: EarnViewModel
    private lateinit var rewardsViewModel: RewardsViewModel
    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Google Mobile Ads
        MobileAds.initialize(this)

        // Initialize ViewModels
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        earnViewModel = ViewModelProvider(this).get(EarnViewModel::class.java)
        rewardsViewModel = ViewModelProvider(this).get(RewardsViewModel::class.java)
        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        setContent {
            SimpleEarnApp(
                authViewModel = authViewModel,
                homeViewModel = homeViewModel,
                earnViewModel = earnViewModel,
                rewardsViewModel = rewardsViewModel,
                historyViewModel = historyViewModel,
                profileViewModel = profileViewModel,
                onLogout = { goToLogin() }
            )
        }
    }

    private fun goToLogin() {
        startActivity(LoginActivity.getIntent(this))
        finish()
    }
}

@Composable
fun SimpleEarnApp(
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel,
    earnViewModel: EarnViewModel,
    rewardsViewModel: RewardsViewModel,
    historyViewModel: HistoryViewModel,
    profileViewModel: ProfileViewModel,
    onLogout: () -> Unit
) {
    val isLoggedIn by authViewModel.isLoggedIn.observeAsState(false)
    val currentUser by authViewModel.currentUser.observeAsState()
    
    if (!isLoggedIn || currentUser == null) {
        LoginSignUpNavigation(authViewModel)
    } else {
        MainAppNavigation(
            user = currentUser!!,
            homeViewModel = homeViewModel,
            earnViewModel = earnViewModel,
            rewardsViewModel = rewardsViewModel,
            historyViewModel = historyViewModel,
            profileViewModel = profileViewModel,
            onLogout = onLogout
        )
    }
}

@Composable
fun LoginSignUpNavigation(authViewModel: AuthViewModel) {
    var showLogin by remember { mutableStateOf(true) }

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

@Composable
fun MainAppNavigation(
    user: com.ykapps.simple_earn.model.User,
    homeViewModel: HomeViewModel,
    earnViewModel: EarnViewModel,
    rewardsViewModel: RewardsViewModel,
    historyViewModel: HistoryViewModel,
    profileViewModel: ProfileViewModel,
    onLogout: () -> Unit
) {
    var selectedTab by remember { mutableStateOf("home") }

    val userStats by homeViewModel.userStats.observeAsState()
    val recentTransactions by homeViewModel.recentTransactions.observeAsState(emptyList())
    val quickTasks by homeViewModel.quickTasks.observeAsState(emptyList())

    val allTasks by earnViewModel.allTasks.observeAsState(emptyList())

    val rewards by rewardsViewModel.rewards.observeAsState(emptyList())
    val userBalance by rewardsViewModel.userBalance.observeAsState(0)

    val historyTransactions by historyViewModel.transactions.observeAsState(emptyList())

    val userProfile by profileViewModel.userProfile.observeAsState(user)

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedTab) {
                "home" -> {
                    if (userStats != null && recentTransactions.isNotEmpty() && quickTasks.isNotEmpty()) {
                        HomeScreen(
                            user = user,
                            stats = userStats!!,
                            recentTransactions = recentTransactions,
                            quickTasks = quickTasks
                        )
                    }
                }
                "earn" -> {
                    EarnScreen(
                        tasks = allTasks,
                        onTaskComplete = { earnViewModel.completeTask(it) }
                    )
                }
                "rewards" -> {
                    RewardsScreen(
                        rewards = rewards,
                        userBalance = userBalance,
                        onRedeem = { rewardsViewModel.redeemReward(it) }
                    )
                }
                "history" -> {
                    HistoryScreen(transactions = historyTransactions)
                }
                "profile" -> {
                    ProfileScreen(
                        user = userProfile ?: user,
                        onLogout = onLogout
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(selectedTab: String, onTabSelected: (String) -> Unit) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color.White,
        contentColor = Color(0xFF0066FF)
    ) {
        NavigationBarItem(
            selected = selectedTab == "home",
            onClick = { onTabSelected("home") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home", modifier = Modifier.size(24.dp)) },
            label = { Text("Home", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF0066FF),
                selectedTextColor = Color(0xFF0066FF),
                unselectedIconColor = Color(0xFFBDBDBD),
                unselectedTextColor = Color(0xFFBDBDBD)
            )
        )

        NavigationBarItem(
            selected = selectedTab == "earn",
            onClick = { onTabSelected("earn") },
            icon = { Icon(Icons.Default.Star, contentDescription = "Earn", modifier = Modifier.size(24.dp)) },
            label = { Text("Earn", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF0066FF),
                selectedTextColor = Color(0xFF0066FF),
                unselectedIconColor = Color(0xFFBDBDBD),
                unselectedTextColor = Color(0xFFBDBDBD)
            )
        )

        NavigationBarItem(
            selected = selectedTab == "rewards",
            onClick = { onTabSelected("rewards") },
            icon = { Icon(Icons.Default.Card, contentDescription = "Rewards", modifier = Modifier.size(24.dp)) },
            label = { Text("Rewards", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF0066FF),
                selectedTextColor = Color(0xFF0066FF),
                unselectedIconColor = Color(0xFFBDBDBD),
                unselectedTextColor = Color(0xFFBDBDBD)
            )
        )

        NavigationBarItem(
            selected = selectedTab == "history",
            onClick = { onTabSelected("history") },
            icon = { Icon(Icons.Default.History, contentDescription = "History", modifier = Modifier.size(24.dp)) },
            label = { Text("History", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF0066FF),
                selectedTextColor = Color(0xFF0066FF),
                unselectedIconColor = Color(0xFFBDBDBD),
                unselectedTextColor = Color(0xFFBDBDBD)
            )
        )

        NavigationBarItem(
            selected = selectedTab == "profile",
            onClick = { onTabSelected("profile") },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile", modifier = Modifier.size(24.dp)) },
            label = { Text("Profile", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF0066FF),
                selectedTextColor = Color(0xFF0066FF),
                unselectedIconColor = Color(0xFFBDBDBD),
                unselectedTextColor = Color(0xFFBDBDBD)
            )
        )
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
