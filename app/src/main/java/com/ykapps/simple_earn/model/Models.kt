package com.ykapps.simple_earn.model

import java.util.Date

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val totalPoints: Int = 0,
    val pointsEarned: Int = 0,
    val pointsRedeemed: Int = 0,
    val dayStreak: Int = 0,
    val membershipDays: Int = 0,
    val referralCount: Int = 0,
    val referralCode: String = "",
    val tier: String = "Silver",
    val createdAt: Long = System.currentTimeMillis()
)

data class Reward(
    val id: String = "",
    val name: String = "",
    val category: String = "", // Gift Cards, Cash, Mobile
    val description: String = "",
    val points: Int = 0,
    val usdValue: Double = 0.0,
    val icon: String = "",
    val isHot: Boolean = false,
    val isPopular: Boolean = false
)

data class EarningTask(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val icon: String = "",
    val points: Int = 0,
    val category: String = "", // Videos, Surveys, Offers, Referral
    val duration: String = "",
    val isCompleted: Boolean = false,
    val difficulty: String = "" // Easy, Normal, Hard
)

data class Transaction(
    val id: String = "",
    val userId: String = "",
    val type: String = "", // earning, redemption
    val title: String = "",
    val description: String = "",
    val points: Int = 0,
    val status: String = "Completed", // Completed, Pending, Failed
    val date: Long = System.currentTimeMillis()
)

data class UserStats(
    val totalEarned: Int = 0,
    val totalRedeemed: Int = 0,
    val totalTransactions: Int = 0,
    val todayEarnings: Int = 0,
    val currentLevel: Int = 3,
    val nextLevelPoints: Int = 5000
)
