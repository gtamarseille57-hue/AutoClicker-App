package com.example.autoclicker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutoClickerTheme {
                MainScreen(
                    onRequestOverlay = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
                            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
                            startActivity(intent)
                        } else {
                            startService(Intent(this, FloatingMenuService::class.java))
                        }
                    },
                    onRequestAccessibility = {
                        val intent = Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    onRequestOverlay: () -> Unit,
    onRequestAccessibility: () -> Unit,
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF101820))
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "AutoClicker",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
            Text(
                text = "IA Assist • Enregistrement • Exécution • Shizuku",
                color = Color(0xFFB8E1FF),
                modifier = Modifier.padding(top = 12.dp)
            )

            Column(
                modifier = Modifier.padding(top = 32.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(onClick = onRequestOverlay) {
                    Text("Démarrer le menu flottant")
                }
                Button(onClick = onRequestAccessibility) {
                    Text("Ouvrir l’accessibilité")
                }
                Button(onClick = { /* TODO */ }) {
                    Text("Lancer l’IA assist")
                }
            }
        }
    }
}

@Composable
fun AutoClickerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            primary = Color(0xFF4AA3FF),
            secondary = Color(0xFF88C6FF),
            background = Color(0xFF101820),
            surface = Color(0xFF1D2A36)
        ),
        content = content
    )
}
