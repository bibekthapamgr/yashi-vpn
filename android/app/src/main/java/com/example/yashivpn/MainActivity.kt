package com.example.yashivpn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.yashivpn.ui.StarterScreen
import com.example.yashivpn.ui.theme.YashiTheme

/**
 * M01 starter activity. It renders a clearly labelled placeholder screen only;
 * the VPN service and WireGuard integration arrive in M03.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YashiTheme {
                StarterScreen()
            }
        }
    }
}
