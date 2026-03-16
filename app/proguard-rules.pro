# Keep Firebase classes
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }

# Keep Google Mobile Ads classes
-keep class com.google.android.gms.ads.** { *; }
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient { *; }

# Keep custom application classes
-keep class com.ykapps.simple_earn.** { *; }

# Keep model classes
-keepclassmembers class com.ykapps.simple_earn.model.** { *; }

# Keep ViewModels
-keepclassmembers class com.ykapps.simple_earn.viewmodel.** { *; }

# Keep Jetpack Compose classes
-keep class androidx.compose.** { *; }
-keep class androidx.lifecycle.** { *; }

# Keep GSON classes
-keep class com.google.gson.** { *; }
-keep class * extends com.google.gson.JsonSerializer { *; }
-keep class * extends com.google.gson.JsonDeserializer { *; }

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Remove logging
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}
