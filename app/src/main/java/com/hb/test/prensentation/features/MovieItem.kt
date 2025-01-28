package com.hb.test.prensentation.features

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.hb.test.domain.model.Movie
import com.hb.test.prensentation.theme.dp_10
import com.hb.test.prensentation.theme.dp_24
import com.hb.test.prensentation.theme.dp_4
import com.hb.test.prensentation.theme.dp_60

@Composable
fun MovieItem(movie: Movie, onMovieClicked: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dp_10))
            .background(Color.LightGray)
            .padding(dp_4)
            .clickable { onMovieClicked() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            movie.title,
            modifier = Modifier.padding(dp_4),
            style = MaterialTheme.typography.labelLarge
        )
        AsyncImage(
            model = movie.imageUrl,
            modifier = Modifier
                .padding(dp_4)
                .clip(RoundedCornerShape(dp_24))
                .width(dp_60)
                .height(dp_60),
            contentScale = ContentScale.Crop,
            contentDescription = ""
        )
    }
}
