package com.ykapps.simple_earn.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ykapps.simple_earn.model.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(user: User, stats: UserStats, recentTransactions: List<Transaction>, quickTasks: List<EarningTask>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(0.dp)
    ) {
        item {
            // Header with Welcome
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0066FF))
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = "Welcome back,",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Text(
                        text = user.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Points Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(Color(0xFF0066FF), RoundedCornerShape(12.dp))
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Total Points", fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
                            Text(
                                text = user.totalPoints.toString(),
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .background(Color(0xFF0052CC), RoundedCornerShape(20.dp))
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "⭐ ${user.tier}",
                                fontSize = 12.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Progress Bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .background(Color.White.copy(alpha = 0.3f), RoundedCornerShape(2.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.4f)
                                .fillMaxHeight()
                                .background(Color(0xFFFFA500), RoundedCornerShape(2.dp))
                        )
                    }
                    Text(
                        text = "Level 3 → Level 4",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Streak
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🔥 ${user.dayStreak} day streak", fontSize = 14.sp, color = Color.White)
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = {},
                            modifier = Modifier
                                .height(32.dp)
                                .background(Color(0xFF0052CC), RoundedCornerShape(6.dp)),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0052CC))
                        ) {
                            Text("Check In", fontSize = 12.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Stats Grid
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard("📈", stats.totalEarned.toString(), "Earned", Modifier.weight(1f))
                StatCard("💰", stats.totalRedeemed.toString(), "Redeemed", Modifier.weight(1f))
                StatCard("👥", user.referralCount.toString(), "Referrals", Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Quick Earn
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Quick Earn",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "View all",
                        fontSize = 13.sp,
                        color = Color(0xFF0066FF),
                        modifier = Modifier.clickable { }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                ) {
                    items(quickTasks.size) { index ->
                        QuickTaskCard(quickTasks[index])
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Recent Activity
            if (recentTransactions.isNotEmpty()) {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = "Recent Activity",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    recentTransactions.forEach { transaction ->
                        TransactionItem(transaction)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun StatCard(icon: String, value: String, label: String, modifier: Modifier) {
    Box(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(icon, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A)
            )
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color(0xFF6B7280)
            )
        }
    }
}

@Composable
fun QuickTaskCard(task: EarningTask) {
    Box(
        modifier = Modifier
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(12.dp)
            .clickable { }
    ) {
        Column {
            Text("🎥", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = task.title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "⭐ +${task.points}",
                fontSize = 12.sp,
                color = Color(0xFF00C853),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .background(
                        if (transaction.type == "earning") Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                        RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            ) {
                Text(if (transaction.type == "earning") "✅" else "💳", fontSize = 18.sp)
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = transaction.title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )
                Text(
                    text = SimpleDateFormat("MMM d", Locale.getDefault()).format(Date(transaction.date)),
                    fontSize = 12.sp,
                    color = Color(0xFF9CA3AF)
                )
            }
            Text(
                text = "${if (transaction.points > 0) "+" else ""}${transaction.points} pts",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = if (transaction.type == "earning") Color(0xFF00C853) else Color(0xFFFF3B30)
            )
        }
    }
}

@Composable
fun EarnScreen(tasks: List<EarningTask>, onTaskComplete: (String) -> Unit) {
    var selectedFilter by remember { mutableStateOf("All") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "Earn Points",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Bonus Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFF3E0), RoundedCornerShape(12.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🏆", fontSize = 32.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Complete all tasks today",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1A1A)
                    )
                    Text(
                        text = "Earn up to 2,165 bonus points",
                        fontSize = 12.sp,
                        color = Color(0xFF6B7280)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Filters
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Videos", "Surveys", "Offers", "Referral").forEach { filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter) },
                        colors = ChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF0066FF),
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        items(tasks.size) { index ->
            EarningTaskCard(tasks[index], onTaskComplete)
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun EarningTaskCard(task: EarningTask, onComplete: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFFE3F2FD), RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Text("🎬", fontSize = 20.sp)
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = task.title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        if (task.difficulty.isNotEmpty()) {
                            Text(
                                text = task.difficulty,
                                fontSize = 11.sp,
                                color = Color.White,
                                modifier = Modifier
                                    .background(Color(0xFF00C853), RoundedCornerShape(4.dp))
                                    .padding(4.dp)
                            )
                        }
                    }
                    Text(
                        text = task.description,
                        fontSize = 12.sp,
                        color = Color(0xFF6B7280)
                    )
                }
                if (task.isCompleted) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color(0xFF00C853)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "⏱️ ${task.duration}",
                    fontSize = 12.sp,
                    color = Color(0xFF6B7280)
                )
                Text(
                    text = "⭐ +${task.points}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00C853)
                )
            }
        }
    }
}

@Composable
fun RewardsScreen(rewards: List<Reward>, userBalance: Int, onRedeem: (String) -> Unit) {
    var selectedCategory by remember { mutableStateOf("All") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "Rewards",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Balance Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0066FF), RoundedCornerShape(12.dp))
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = "Available Balance",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(
                            text = userBalance.toString(),
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = " pts",
                            fontSize = 16.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        Text(
                            text = " = \$${String.format("%.2f", userBalance * 0.01)}",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Category Filter
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Gift Cards", "Cash", "Mobile").forEach { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(category) },
                        colors = ChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF0066FF),
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        items(rewards.size) { index ->
            RewardCard(rewards[index], userBalance, onRedeem)
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun RewardCard(reward: Reward, userBalance: Int, onRedeem: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color(0xFF0066FF), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("🎁", fontSize = 24.sp)
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = reward.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    if (reward.isHot) {
                        Text(
                            text = "HOT",
                            fontSize = 10.sp,
                            color = Color.White,
                            modifier = Modifier
                                .background(Color(0xFFFF6B6B), RoundedCornerShape(4.dp))
                                .padding(4.dp)
                        )
                    }
                }
                Text(
                    text = reward.description,
                    fontSize = 12.sp,
                    color = Color(0xFF6B7280)
                )
                Text(
                    text = "⭐ ${reward.points} pts",
                    fontSize = 11.sp,
                    color = Color(0xFF00C853),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Button(
                onClick = { onRedeem(reward.id) },
                enabled = userBalance >= reward.points,
                modifier = Modifier
                    .height(40.dp)
                    .width(80.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0066FF),
                    disabledContainerColor = Color(0xFFBDBDBD)
                ),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text("Redeem", fontSize = 11.sp)
            }
        }
    }
}

@Composable
fun HistoryScreen(transactions: List<Transaction>) {
    var selectedFilter by remember { mutableStateOf("All") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "History",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Stats Cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color(0xFFE8F5E9), RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("📈", fontSize = 24.sp)
                        Text("+0", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00C853))
                        Text("Today's Earnings", fontSize = 12.sp, color = Color(0xFF6B7280))
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color(0xFFE3F2FD), RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("💳", fontSize = 24.sp)
                        Text(transactions.size.toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0066FF))
                        Text("Total Transactions", fontSize = 12.sp, color = Color(0xFF6B7280))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Filters
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Earnings", "Redemptions").forEach { filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter) },
                        colors = ChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF0066FF),
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        items(transactions.size) { index ->
            TransactionItem(transactions[index])
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ProfileScreen(user: User, onLogout: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(16.dp)
    ) {
        item {
            // Profile Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(Color(0xFF0066FF), RoundedCornerShape(30.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("R", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp)
                    ) {
                        Text(user.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = "⭐ ${user.tier} • Member for ${user.membershipDays} days",
                            fontSize = 12.sp,
                            color = Color(0xFF6B7280)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard("⭐", user.totalPoints.toString(), "Points Balance", Modifier.weight(1f))
                StatCard("📈", user.pointsEarned.toString(), "Total Earned", Modifier.weight(1f))
                StatCard("💳", user.pointsRedeemed.toString(), "Redeemed", Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Referral Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Text("Referral Program", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0066FF))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "You've referred ${user.referralCount} friends. Keep going!",
                        fontSize = 12.sp,
                        color = Color(0xFF6B7280)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TextField(
                            value = user.referralCode,
                            onValueChange = {},
                            enabled = false,
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp),
                            shape = RoundedCornerShape(6.dp),
                            colors = TextFieldDefaults.colors(
                                disabledContainerColor = Color(0xFFF0F2F5),
                                disabledTextColor = Color(0xFF0066FF)
                            )
                        )
                        Button(
                            onClick = {},
                            modifier = Modifier
                                .height(40.dp)
                                .width(40.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0066FF)),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text("📋", fontSize = 16.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Settings Sections
            SettingsSection("ACCOUNT") {
                SettingsItem("Edit Profile", "user")
                SettingsItem("Email: ${user.email}", "mail")
                SettingsItem("Change Password", "lock")
            }

            SettingsSection("PREFERENCES") {
                SettingsItem("Notifications", "bell")
                SettingsItem("Dark Mode", "moon")
                SettingsItem("Language", "globe")
            }

            SettingsSection("SUPPORT") {
                SettingsItem("Help & FAQ", "help")
                SettingsItem("Terms of Service", "document")
                SettingsItem("Privacy Policy", "shield")
                SettingsItem("Rate the App", "star")
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Sign Out
            Button(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B6B)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Sign Out", fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun SettingsSection(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF9CA3AF),
            modifier = Modifier.padding(16.dp, 8.dp)
        )
        Box(modifier = Modifier.background(Color.White, RoundedCornerShape(8.dp))) {
            Column {
                content()
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun SettingsItem(title: String, icon: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(when(icon) {
                "user" -> "👤"
                "mail" -> "✉️"
                "lock" -> "🔒"
                "bell" -> "🔔"
                "moon" -> "🌙"
                "globe" -> "🌍"
                "help" -> "❓"
                "document" -> "📄"
                "shield" -> "🛡️"
                "star" -> "⭐"
                else -> "•"
            }, fontSize = 18.sp, modifier = Modifier.padding(end = 12.dp))
            
            Text(title, fontSize = 14.sp, color = Color(0xFF1A1A1A), modifier = Modifier.weight(1f))
            Text("›", fontSize = 20.sp, color = Color(0xFFBDBDBD))
        }
    }
}
