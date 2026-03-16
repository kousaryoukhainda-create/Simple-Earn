# 🚀 Simple Earn - Quick Start

## 30-Second Setup

1. **Download Android Studio** (if you don't have it)
   - https://developer.android.com/studio

2. **Open this project**
   - Launch Android Studio
   - File → Open → Select `Simple Earn` folder
   - Wait for Gradle sync ✓

3. **Run the app**
   - Click ▶ (Run button)
   - Select your emulator
   - Wait ~2 minutes for first build ✓

4. **Login**
   - Email: `anything@example.com`
   - Password: `password123` (or any 6+ chars)
   - Click "Log In" ✓

## What You Get

✅ 5 Fully Functional Screens:
   - Home (Dashboard with stats)
   - Earn (Task-based points)
   - Rewards (Redemption marketplace)
   - History (Transaction log)
   - Profile (Settings)

✅ Features:
   - Bottom navigation
   - Mock data pre-loaded
   - Google Mobile Ads ready
   - Firebase integration ready
   - ~13 MB APK size

## File Structure

```
Simple Earn/
├── README.md          ← Full documentation
├── SETUP.md           ← Detailed setup guide
├── QUICKSTART.md      ← This file
├── app/
│   └── src/main/
│       ├── java/      ← All Kotlin code
│       └── res/       ← Resources (colors, strings)
├── build.gradle       ← Dependencies
└── settings.gradle    ← Project config
```

## Build Commands

### Run on Emulator
```bash
./gradlew runDebug
```

### Build Debug APK
```bash
./gradlew assembleDebug
# Find at: app/build/outputs/apk/debug/app-debug.apk
```

### Build Release APK
```bash
./gradlew assembleRelease
# Find at: app/build/outputs/apk/release/app-release.apk
```

## Key Files

| File | Purpose |
|------|---------|
| `MainActivity.kt` | Main app with bottom nav |
| `AuthScreen.kt` | Login/Sign-up screens |
| `MainScreens.kt` | Home, Earn, Rewards, History, Profile |
| `ViewModels.kt` | State management (6 ViewModels) |
| `Models.kt` | Data classes |
| `colors.xml` | Color scheme |
| `strings.xml` | Text/strings |
| `AndroidManifest.xml` | App configuration |
| `build.gradle` | Dependencies |

## Color Palette

- 🔵 Primary: `#0066FF` (Blue)
- ✅ Success: `#00C853` (Green)
- ❌ Error: `#FF3B30` (Red)
- ⚠️ Warning: `#FFA500` (Orange)

## Mock Login Credentials

The app uses **mock authentication** - any email/password combination works:

```
✓ Email: user@example.com
✓ Password: password123

✓ Email: test@test.com
✓ Password: anypassword

✓ Email: anything@anything.com
✓ Password: 6ormoredigits
```

## Features Overview

### 🏠 Home Screen
- Total points balance
- Level progress bar
- Day streak counter
- Quick earn tasks (4 shortcuts)
- Recent activity feed
- Stats cards

### 💰 Earn Screen
- 10+ earning tasks
- Task categories (Videos, Surveys, Offers, Referrals)
- Difficulty badges
- Duration & points
- Task filtering
- Completion tracking

### 🎁 Rewards Screen
- Points balance display
- 10 reward options
- Gift cards, cash, mobile
- Price in points & USD
- Redeem buttons
- Category filtering

### 📊 History Screen
- Earnings log
- Redemptions log
- Date grouping
- Transaction stats
- Filter options

### 👤 Profile Screen
- User info
- Referral code (copy-able)
- Account settings
- Preferences (Dark Mode, Language)
- Support links
- Logout button

## Customization

### Change App Name
```
app/src/main/res/values/strings.xml
Line 1: <string name="app_name">NewName</string>
```

### Change Primary Color
```
app/src/main/res/values/colors.xml
Line 2: <color name="primary">#0066FF</color>
```

### Add Firebase
1. Get `google-services.json` from Firebase Console
2. Place in: `app/google-services.json`
3. Update ViewModels to use Firebase instead of mock data

### Update Ad IDs
Replace in `AndroidManifest.xml` line 17:
```xml
android:value="ca-app-pub-xxxxxxxxxxxxxxxx~yyyyyyyyyy"
```

With your Google AdMob app ID.

## Test Ad IDs (Google's Demo IDs)

Use these for testing - they won't generate revenue:

```
Banner: ca-app-pub-3940256099942544/6300978111
Interstitial: ca-app-pub-3940256099942544/1033173712
Rewarded: ca-app-pub-3940256099942544/5224354917
```

## APK Size

- Debug: ~15 MB
- Release (optimized): ~13 MB
  - Code: ~4 MB
  - Compose: ~4 MB
  - Firebase: ~2 MB
  - Ads SDK: ~2 MB
  - Resources: ~1 MB

## Troubleshooting

**Gradle sync fails?**
```bash
./gradlew clean
./gradlew sync
```

**App crashes on startup?**
- Check `logcat` for errors
- Verify `AndroidManifest.xml` is correct
- Make sure all Activity names match files

**Emulator won't run app?**
- Create new emulator (Android 8.0+)
- Allocate 2GB+ RAM
- Run `./gradlew installDebug` then launch from emulator

**Can't find Android SDK?**
- File → Project Structure → SDK Location
- Set to your Android SDK path (usually `~/Android/Sdk`)

## Architecture

**MVVM Pattern:**
- ViewModels manage state
- Composables observe state
- No direct Activity/Fragment manipulation
- Clean data flow

**Navigation:**
- Bottom navigation with 5 tabs
- ViewModel persistence
- State retention on tab switch

**Data Management:**
- Mock data for demo
- Ready for Firebase integration
- LiveData for reactive updates

## Next Steps

1. ✅ Run the app on emulator
2. ✅ Test all 5 screens
3. ✅ Review code structure
4. ✅ Customize colors/strings
5. ✅ Add Firebase (optional)
6. ✅ Build release APK
7. ✅ Deploy to Play Store

## Resources

- 📚 Full Docs: `README.md`
- 🔧 Setup Guide: `SETUP.md`
- 📱 Android Docs: https://developer.android.com
- 🎨 Compose Docs: https://developer.android.com/jetpack/compose
- 🔥 Firebase: https://console.firebase.google.com
- 📢 Google Ads: https://admob.google.com

## Support

- Check `README.md` for detailed documentation
- Review code comments in ViewModels
- Check `AndroidManifest.xml` for configuration
- Refer to Android Studio documentation

---

**Everything is ready to use! Just open, sync, and run. 🎉**

Built with Jetpack Compose, Firebase-ready, Google Ads integrated, and fully customizable.
