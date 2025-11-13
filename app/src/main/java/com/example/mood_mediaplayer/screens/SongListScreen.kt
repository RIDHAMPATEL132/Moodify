package com.example.mood_mediaplayer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mood_mediaplayer.R

@Composable
fun SongListScreen(
    moodName: String,
    moodColor: Color,
    onSongSelected: (Int) -> Unit,
    onBackPressed: () -> Unit
) {

    val songs = when (moodName) {
        "Happy" -> listOf("Love You Zindagi", "Main Koi Aisa Geet Gaoon")
        "Sad" -> listOf("Humdard", "Duaa")
        "Chill" -> listOf("Make Some Noise For Desi Boys", "Subha Hone Na De")
        "Romantic" -> listOf("Chaleya", "Tainu khabar nahi", "Agar tum sath ho to ", "Saiyaraa")
        "Energetic" -> listOf("Challa", "Dangal", "Desire")
        else -> listOf("No Songs Available")
    }

    val gradientColors = listOf(
        moodColor.copy(alpha = 0.95f),
        Color.Black.copy(alpha = 0.9f)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(gradientColors))
            .padding(20.dp)
    ) {

        Column {

            Text(
                text = moodName,
                fontSize = 42.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                modifier = Modifier.padding(top = 50.dp, bottom = 2.dp)
            )

            Text(
                text = "Playlist 🎧",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White.copy(alpha = 0.75f),
                modifier = Modifier.padding(bottom = 28.dp)
            )


            // Song List
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                itemsIndexed(songs) { index, song ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSongSelected(index) },
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.15f)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 18.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                painter = painterResource(id = R.drawable.ic_music_note),
                                contentDescription = null,
                                tint = Color.White.copy(alpha = 0.95f),
                                modifier = Modifier.size(26.dp)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Text(
                                text = song,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }


                }
            }
        }
    }
}

