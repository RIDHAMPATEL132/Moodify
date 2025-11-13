package com.example.mood_mediaplayer.data

import com.example.mood_mediaplayer.R

data class Song(val title: String, val resId: Int)

object SongManager {
    val moodSongs = mapOf(
        "Happy" to listOf(
            Song("Love You Zindagi", R.raw.love_you_zindagi),
            Song("Main Koi Aisa Geet Gaoon", R.raw.main_koi_aesa_geet_gau)
        ),
        "Sad" to listOf(
            Song("Humdard", R.raw.humdard),
            Song("Duaa", R.raw.duaa)
        ),
        "Chill" to listOf(
            Song("Make Some Noise for Desi Boys", R.raw.make_some_noise_for_desi_boys),
            Song("Subha Hone Na De", R.raw.subha_hone_na_de)
        ),
        "Romantic" to listOf(
            Song("Chaleya", R.raw.chaleya),
            Song("Tainu Khabar Nahi", R.raw.tainu_khabr_nahi),
            Song("Agar Tum Sath Ho To", R.raw.agar_tum_sath_ho_to),
            Song("Saiyaraa", R.raw.saiyaraa)
        ),
        "Energetic" to listOf(
            Song("Challa", R.raw.challa),
            Song("Dangal", R.raw.dangal),
            Song("Desire", R.raw.desire)
        )
    )
}
