package com.example.mood_mediaplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mood_mediaplayer.screens.MoodSelectionScreen
import com.example.mood_mediaplayer.screens.PlayerScreen
import com.example.mood_mediaplayer.screens.SongListScreen
import com.example.mood_mediaplayer.ui.theme.Mood_mediaplayerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mood_mediaplayerTheme {
                AppNavigator()
                }
            }
        }
    }

@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    Surface {
        NavHost(navController = navController, startDestination = "moodSelection") {

            // Mood Select Screen
            composable("moodSelection") {
                MoodSelectionScreen(onMoodSelected = { mood ->
                    navController.navigate("songList/$mood")
                })
            }

            // Song List Screen
            composable("songList/{moodName}") { backStack ->
                val moodName = backStack.arguments?.getString("moodName") ?: "Unknown Mood"

                val moodColor = when (moodName) {
                    "Happy" -> Color(0xFFFFC107)
                    "Sad" -> Color(0xFF2196F3)
                    "Chill" -> Color(0xFF4DB6AC)
                    "Romantic" -> Color(0xFFE91E63)
                    "Energetic" -> Color(0xFFFF5722)
                    else -> Color(0xFF9E9E9E)
                }

                SongListScreen(
                    moodName = moodName,
                    moodColor = moodColor,
                    onSongSelected = { songIndex ->
                        navController.navigate("player/$moodName/$songIndex")
                    },
                    onBackPressed = {
                        navController.popBackStack()
                    }
                )
            }


            // Player Screen
            composable("player/{moodName}/{songIndex}") { backStack ->
                val moodName = backStack.arguments?.getString("moodName") ?: "Unknown"
                val songIndex = backStack.arguments?.getString("songIndex")?.toInt() ?: 0

                val songs = when (moodName) {
                    "Happy" -> listOf("Love You Zindagi", "Main Koi Aisa Geet Gaoon")
                    "Sad" -> listOf("Humdard", "Duaa")
                    "Chill" -> listOf("Make Some Noise For Desi Boys", "Subha Hone Na De")
                    "Romantic" -> listOf("Chaleya", "Agar Tum Sath Ho", "Saiyara", "Ranjha")
                    "Energetic" -> listOf("Challa", "Dangal", "Zinda")
                    else -> listOf("No Songs Available")
                }

                val moodColor = when (moodName) {
                    "Happy" -> Color(0xFFFFC107)
                    "Sad" -> Color(0xFF2196F3)
                    "Chill" -> Color(0xFF4DB6AC)
                    "Romantic" -> Color(0xFFE91E63)
                    "Energetic" -> Color(0xFFFF5722)
                    else -> Color(0xFF9E9E9E)
                }

                PlayerScreen(
                    moodName = moodName,
                    songs = songs,
                    startIndex = songIndex,
                    moodColor = moodColor
                )
            }

        }
    }
}

