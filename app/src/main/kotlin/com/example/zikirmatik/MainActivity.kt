package com.example.zikirmatik

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.zikirmatik.ui.theme.ZikirmatikTheme
import com.example.zikirmatik.ui.zikirmatik.ZikirmatikScreen
import com.google.android.gms.ads.MobileAds

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Google Mobile Ads SDK başlatma (AdMob)
        MobileAds.initialize(this) {}

        setContent {
            ZikirmatikTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ZikirmatikScreen()
                }
            }
        }
    }
}
