package com.example.mood_mediaplayer.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mood_mediaplayer.R

data class MoodCard(val name: String, val color1: Color, val color2: Color, val icon: Int)

@Composable
fun MoodSelectionScreen(onMoodSelected: (String) -> Unit) {
    val moods = listOf(
        MoodCard("Happy", Color(0xFFFFC107), Color(0xFFFF9800), R.drawable.happy),
        MoodCard("Sad", Color(0xFF2196F3), Color(0xFF3F51B5), R.drawable.sad),
        MoodCard("Chill", Color(0xFF4DB6AC), Color(0xFF00796B), R.drawable.chill),
        MoodCard("Romantic", Color(0xFFE91E63), Color(0xFFAD1457), R.drawable.romantic),
        MoodCard("Energetic", Color(0xFFFF5722), Color(0xFFF44336), R.drawable.energetic)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F2027),
                        Color(0xFF203A43),
                        Color(0xFF2C5364),

                    )
                )
            )
            .padding(15.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 15.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.04f),
                                Color.White.copy(alpha = 0.12f)
                            )
                        )
                    )
                    .padding(15.dp),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(top = 0.dp, bottom = 1.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo2),
                            contentDescription = "App Logo",
                            modifier = Modifier
                                .size(60.dp)
                                .padding(end = 2.dp)
                                .clip(CircleShape)
                                .shadow(
                                    elevation = 15.dp,
                                    ambientColor = Color(0xFF00FF9C),
                                    spotColor = Color(0xFF00C6FF)
                                )
                        )

                        Text(
                            text = "Moodify",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Serif,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Feel the music that feels you 🎵",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.75f)
                    )
                }
            }


            Spacer(modifier = Modifier.height(15.dp))



            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(moods) { mood ->
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .background(
                                Brush.linearGradient(listOf(mood.color1, mood.color2)),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable { onMoodSelected(mood.name) }
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = mood.icon),
                                contentDescription = mood.name,
                                modifier = Modifier.size(80.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = mood.name,
                                fontSize = 18.sp,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}
