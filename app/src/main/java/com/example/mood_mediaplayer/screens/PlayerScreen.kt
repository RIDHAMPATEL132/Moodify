package com.example.mood_mediaplayer.screens

import android.media.MediaPlayer
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mood_mediaplayer.data.SongManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mood_mediaplayer.R

@Composable
fun PlayerScreen(
    moodName: String,
    songs: List<String>,
    startIndex: Int,
    moodColor: Color
){
    val context = LocalContext.current
    val songs = SongManager.moodSongs[moodName] ?: emptyList()
    val rotation = remember { Animatable(0f) }

    var currentSongIndex by remember { mutableStateOf(startIndex) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isPlaying by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }
    var duration by remember { mutableStateOf(1f) } // avoid division by zero


    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
        }
    }


    fun playSong(index: Int) {
        if (songs.isNotEmpty()) {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(context, songs[index].resId)
            mediaPlayer?.start()
            isPlaying = true

            duration = mediaPlayer?.duration?.toFloat() ?: 1f

            mediaPlayer?.setOnCompletionListener {
                // Move to the next song in the list
                currentSongIndex = (currentSongIndex + 1) % songs.size
                playSong(currentSongIndex)
            }
        }
    }

    LaunchedEffect(isPlaying, mediaPlayer) {
        while (true) {
            if (isPlaying && mediaPlayer != null) {
                progress = mediaPlayer!!.currentPosition.toFloat()
                duration = mediaPlayer!!.duration.toFloat()
            }
            kotlinx.coroutines.delay(500)
        }
    }

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            while (isPlaying) {
                rotation.animateTo(
                    targetValue = rotation.value + 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 8000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
            }
        } else {
            rotation.stop()
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        moodColor.copy(alpha = 0.8f),
                        Color.Black
                    )
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Moodify",
                fontSize = 28.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Box(
                modifier = Modifier
                    .size(220.dp)
                    .background(Color.White.copy(alpha = 0.15f), shape = RoundedCornerShape(100.dp))
                    .shadow(20.dp, shape = RoundedCornerShape(100.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo2),
                    contentDescription = "Album Art",
                    modifier = Modifier
                        .size(200.dp)
                        .graphicsLayer(rotationZ = rotation.value)
                        .clip(CircleShape)
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$moodName Vibes",
                    fontSize = 22.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = if (songs.isNotEmpty()) "Now playing: ${songs[currentSongIndex].title}" else "No songs found",
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
            }

            if (songs.isNotEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Slider(
                        value = if (duration > 0) progress / duration else 0f,
                        onValueChange = { value ->
                            mediaPlayer?.seekTo((value * duration).toInt())
                            progress = value * duration
                        },
                        valueRange = 0f..1f,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(4.dp),
                        colors = androidx.compose.material3.SliderDefaults.colors(
                            thumbColor = Color.White,
                            activeTrackColor = Color.White,
                            inactiveTrackColor = Color.Gray.copy(alpha = 0.4f)
                        )
                    )
                    Spacer(modifier = Modifier.height(7.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(horizontal = 6.dp)
                    ) {
                        Text(
                            text = formatTime(progress.toLong()),
                            fontSize = 12.sp,
                            color = Color.LightGray
                        )

                        Text(
                            text = formatTime(duration.toLong()),
                            fontSize = 12.sp,
                            color = Color.LightGray
                        )
                    }
                }
            }

            // Music Controls
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                val buttonModifier = Modifier

                    .shadow(10.dp, RoundedCornerShape(50))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF2C2C2E),
                                Color(0xFF1C1C1E)
                            )
                        ),
                        RoundedCornerShape(50)
                    )

                // Previous Button
                Box(
                    modifier = buttonModifier
                        .size(60.dp)
                        .clickable {
                        if (songs.isNotEmpty()) {
                            currentSongIndex =
                                if (currentSongIndex - 1 < 0) songs.lastIndex else currentSongIndex - 1
                            playSong(currentSongIndex)
                        }
                    },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_backward), // your PNG here
                        contentDescription = "Previous",
                        modifier = Modifier.size(32.dp)
                    )
                }

                // Play-Pause Button
                Box(
                    modifier = buttonModifier
                        .size(90.dp)
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    Color(0xFF505050),
                                    Color(0xFF2A2A2A)
                                )
                            ),
                            RoundedCornerShape(50)
                        )
                        .clickable {
                            if (songs.isNotEmpty()) {
                                if (isPlaying) {
                                    mediaPlayer?.pause()
                                    isPlaying = false
                                } else {
                                    if (mediaPlayer == null) {
                                        playSong(currentSongIndex)
                                    } else {
                                        mediaPlayer?.start()
                                        isPlaying = true
                                    }
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(
                            id = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
                        ),
                        contentDescription = "Play/Pause",
                        modifier = Modifier.size(100.dp)
                    )
                }

                // Next Button
                Box(
                    modifier = buttonModifier
                        .size(60.dp)
                        .clickable {
                        if (songs.isNotEmpty()) {
                            currentSongIndex = (currentSongIndex + 1) % songs.size
                            playSong(currentSongIndex)
                        }
                    },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_forward),
                        contentDescription = "Next",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

        }
    }
}
// Helper function
fun formatTime(milliseconds: Long): String {
    val totalSeconds = milliseconds / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}
