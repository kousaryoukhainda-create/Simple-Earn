package com.ykapps.simple_earn.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ykapps.simple_earn.model.*

class AuthViewModel : ViewModel() {
    private val _isLoggedIn = MutableLiveData(false)
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    private val _currentUser = MutableLiveData<User?>(null)
    val currentUser: LiveData<User?> = _currentUser

    fun login(email: String, password: String) {
        // Mock login - in production, use Firebase
        if (email.isNotEmpty() && password.length >= 6) {
            _currentUser.value = User(
                id = "user_${System.currentTimeMillis()}",
                name = email.substringBefore("@"),
                email = email,
                totalPoints = 2295,
                pointsEarned = 5745,
                pointsRedeemed = 3450,
                dayStreak = 1,
                membershipDays = 37,
                referralCount = 2,
                referralCode = "ERW67IUM"
            )
            _isLoggedIn.value = true
        }
    }

    fun signUp(email: String, password: String, name: String) {
        // Mock signup - in production, use Firebase
        if (email.isNotEmpty() && password.length >= 6 && name.isNotEmpty()) {
            _currentUser.value = User(
                id = "user_${System.currentTimeMillis()}",
                name = name,
                email = email,
                totalPoints = 0,
                pointsEarned = 0,
                pointsRedeemed = 0,
                dayStreak = 0,
                membershipDays = 0,
                referralCount = 0,
                referralCode = "ERW${System.currentTimeMillis() % 1000000}"
            )
            _isLoggedIn.value = true
        }
    }

    fun logout() {
        _isLoggedIn.value = false
        _currentUser.value = null
    }
}

class HomeViewModel : ViewModel() {
    private val _userStats = MutableLiveData<UserStats>()
    val userStats: LiveData<UserStats> = _userStats

    private val _recentTransactions = MutableLiveData<List<Transaction>>()
    val recentTransactions: LiveData<List<Transaction>> = _recentTransactions

    private val _quickTasks = MutableLiveData<List<EarningTask>>()
    val quickTasks: LiveData<List<EarningTask>> = _quickTasks

    init {
        loadData()
    }

    private fun loadData() {
        _userStats.value = UserStats(
            totalEarned = 5745,
            totalRedeemed = 3450,
            totalTransactions = 23,
            todayEarnings = 0,
            currentLevel = 3,
            nextLevelPoints = 5000
        )

        _recentTransactions.value = listOf(
            Transaction(
                title = "Daily Check-in (Day 1)",
                points = 25,
                type = "earning",
                date = System.currentTimeMillis()
            ),
            Transaction(
                title = "Social Share",
                points = 100,
                type = "earning",
                date = System.currentTimeMillis() - 86400000
            )
        )

        _quickTasks.value = listOf(
            EarningTask(
                title = "Watch Video",
                description = "Watch a 30-second ad video",
                points = 50,
                category = "Videos",
                duration = "30 sec"
            ),
            EarningTask(
                title = "Take Survey",
                description = "Share your opinion in a quick survey",
                points = 150,
                category = "Surveys",
                duration = "2 min"
            ),
            EarningTask(
                title = "Try App",
                description = "Download and open a featured app",
                points = 200,
                category = "Offers",
                duration = "1 min"
            ),
            EarningTask(
                title = "Refer Friend",
                description = "Get bonus when friend joins & earns",
                points = 300,
                category = "Referral",
                duration = "Instant"
            )
        )
    }
}

class EarnViewModel : ViewModel() {
    private val _allTasks = MutableLiveData<List<EarningTask>>()
    val allTasks: LiveData<List<EarningTask>> = _allTasks

    init {
        loadTasks()
    }

    private fun loadTasks() {
        _allTasks.value = listOf(
            EarningTask(
                title = "Watch a Short Video",
                description = "Watch a 30-second ad video",
                points = 50,
                category = "Videos",
                duration = "30 sec",
                difficulty = "Easy"
            ),
            EarningTask(
                title = "Complete a Survey",
                description = "Share your opinion in a quick survey",
                points = 150,
                category = "Surveys",
                duration = "2 min"
            ),
            EarningTask(
                title = "Try a New App",
                description = "Download and open a featured app",
                points = 200,
                category = "Offers",
                duration = "1 min",
                difficulty = "Popular"
            ),
            EarningTask(
                title = "Play a Mini Game",
                description = "Play our featured mini game",
                points = 75,
                category = "Videos",
                duration = "1 min"
            ),
            EarningTask(
                title = "Read an Article",
                description = "Read a sponsored article",
                points = 40,
                category = "Videos",
                duration = "1 min",
                difficulty = "Easy"
            ),
            EarningTask(
                title = "Sign Up to Netflix",
                description = "Create a new Netflix account",
                points = 500,
                category = "Offers",
                duration = "5 min",
                difficulty = "Hard"
            ),
            EarningTask(
                title = "Order from DoorDash",
                description = "Place your first DoorDash order",
                points = 400,
                category = "Offers",
                duration = "5 min"
            ),
            EarningTask(
                title = "Amazon Shopping",
                description = "Make a purchase on Amazon",
                points = 350,
                category = "Offers",
                duration = "5 min"
            ),
            EarningTask(
                title = "Refer a Friend",
                description = "Get bonus when friend joins & earns",
                points = 300,
                category = "Referral",
                duration = "Instant"
            ),
            EarningTask(
                title = "Social Share",
                description = "Share app on social media",
                points = 100,
                category = "Referral",
                duration = "Instant"
            )
        )
    }

    fun completeTask(taskId: String) {
        val tasks = _allTasks.value?.map {
            if (it.id == taskId) it.copy(isCompleted = true) else it
        }
        _allTasks.value = tasks ?: emptyList()
    }
}

class RewardsViewModel : ViewModel() {
    private val _rewards = MutableLiveData<List<Reward>>()
    val rewards: LiveData<List<Reward>> = _rewards

    private val _userBalance = MutableLiveData(2295)
    val userBalance: LiveData<Int> = _userBalance

    init {
        loadRewards()
    }

    private fun loadRewards() {
        _rewards.value = listOf(
            Reward(
                id = "amazon_5",
                name = "Amazon",
                category = "Gift Cards",
                description = "Gift Card • $5",
                points = 500,
                usdValue = 5.0,
                isHot = true
            ),
            Reward(
                id = "paypal_2.5",
                name = "PayPal",
                category = "Cash",
                description = "Cash Out • $2.5",
                points = 250,
                usdValue = 2.5
            ),
            Reward(
                id = "google_play_5",
                name = "Google Play",
                category = "Gift Cards",
                description = "Gift Card • $5",
                points = 500,
                usdValue = 5.0
            ),
            Reward(
                id = "itunes_5",
                name = "iTunes",
                category = "Gift Cards",
                description = "Gift Card • $5",
                points = 500,
                usdValue = 5.0,
                isHot = true
            ),
            Reward(
                id = "mobile_3",
                name = "Any Network",
                category = "Mobile",
                description = "Mobile Recharge • $3",
                points = 300,
                usdValue = 3.0
            ),
            Reward(
                id = "walmart_10",
                name = "Walmart",
                category = "Gift Cards",
                description = "Gift Card • $10",
                points = 1000,
                usdValue = 10.0
            ),
            Reward(
                id = "venmo_5",
                name = "Venmo",
                category = "Cash",
                description = "Cash Out • $5",
                points = 500,
                usdValue = 5.0
            ),
            Reward(
                id = "starbucks_4",
                name = "Starbucks",
                category = "Gift Cards",
                description = "Gift Card • $4",
                points = 400,
                usdValue = 4.0
            ),
            Reward(
                id = "premium_pack_8",
                name = "Premium Pack",
                category = "Mobile",
                description = "Mobile Data • $8",
                points = 800,
                usdValue = 8.0
            ),
            Reward(
                id = "netflix_15",
                name = "Netflix",
                category = "Gift Cards",
                description = "Gift Card • $15",
                points = 1500,
                usdValue = 15.0
            )
        )
    }

    fun redeemReward(rewardId: String) {
        val reward = _rewards.value?.find { it.id == rewardId }
        if (reward != null && _userBalance.value!! >= reward.points) {
            _userBalance.value = _userBalance.value!! - reward.points
        }
    }
}

class HistoryViewModel : ViewModel() {
    private val _transactions = MutableLiveData<List<Transaction>>()
    val transactions: LiveData<List<Transaction>> = _transactions

    init {
        loadTransactions()
    }

    private fun loadTransactions() {
        _transactions.value = listOf(
            Transaction(
                title = "Starbucks Gift Card $4",
                points = -400,
                type = "redemption",
                status = "Completed",
                date = System.currentTimeMillis() - 86400000 * 6
            ),
            Transaction(
                title = "PayPal Cash Out $2.5",
                points = -250,
                type = "redemption",
                status = "Completed",
                date = System.currentTimeMillis() - 86400000 * 6
            ),
            Transaction(
                title = "PayPal Cash Out $2.5",
                points = -250,
                type = "redemption",
                status = "Completed",
                date = System.currentTimeMillis() - 86400000 * 6
            ),
            Transaction(
                title = "Any Network Mobile Recharge $3",
                points = -300,
                type = "redemption",
                status = "Completed",
                date = System.currentTimeMillis() - 86400000 * 6
            ),
            Transaction(
                title = "Amazon Gift Card $5",
                points = -500,
                type = "redemption",
                status = "Completed",
                date = System.currentTimeMillis() - 86400000 * 8
            ),
            Transaction(
                title = "Daily Check-in (Day 1)",
                points = 25,
                type = "earning",
                status = "Completed",
                date = System.currentTimeMillis() - 86400000 * 3
            ),
            Transaction(
                title = "Daily Check-in (Day 2)",
                points = 30,
                type = "earning",
                status = "Completed",
                date = System.currentTimeMillis() - 86400000 * 5
            ),
            Transaction(
                title = "Social Share",
                points = 100,
                type = "earning",
                status = "Completed",
                date = System.currentTimeMillis() - 86400000 * 5
            ),
            Transaction(
                title = "Refer a Friend",
                points = 300,
                type = "earning",
                status = "Completed",
                date = System.currentTimeMillis() - 86400000 * 5
            )
        )
    }
}

class ProfileViewModel : ViewModel() {
    private val _userProfile = MutableLiveData<User>()
    val userProfile: LiveData<User> = _userProfile

    init {
        loadProfile()
    }

    private fun loadProfile() {
        _userProfile.value = User(
            id = "user_123",
            name = "Reward Hunter",
            email = "user@email.com",
            totalPoints = 2295,
            pointsEarned = 5745,
            pointsRedeemed = 3450,
            dayStreak = 1,
            membershipDays = 37,
            referralCount = 2,
            referralCode = "ERW67IUM",
            tier = "Silver"
        )
    }

    fun updateProfile(user: User) {
        _userProfile.value = user
    }
}
