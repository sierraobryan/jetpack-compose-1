/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.transform.CircleCropTransformation
import com.example.androiddevchallenge.data.About
import com.example.androiddevchallenge.data.Dog
import com.example.androiddevchallenge.ui.MainViewModel
import com.example.androiddevchallenge.ui.theme.purple500
import dev.chrisbanes.accompanist.coil.CoilImage
import java.util.Locale

@Composable
fun DetailsScreen(mainViewModel: MainViewModel) {
    val dog = mainViewModel.dog.observeAsState()
    DetailsContent(dog = dog.value)
}

@Composable
fun DetailsContent(dog: Dog?) {
    Surface(color = MaterialTheme.colors.background) {
        dog?.let { DogDetails(dog = it) } ?: EmptyState()
    }
}

@Composable
fun DogDetails(dog: Dog, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    Column(
        modifier
            .padding(8.dp)
            .background(color = MaterialTheme.colors.surface)
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier
                .fillMaxWidth()
        ) {
            CoilImage(
                data = dog.imageUrl,
                requestBuilder = {
                    transformations(CircleCropTransformation())
                },
                contentDescription = "${dog.name}'s image",
                modifier = Modifier.size(100.dp)
            )
            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(dog.name, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(dog.breed, style = MaterialTheme.typography.body2)
                    Text(dog.location, style = MaterialTheme.typography.body2)
                }
            }
        }
        Divider(
            color = purple500,
            modifier = modifier.padding(start = 70.dp, end = 70.dp, top = 30.dp)
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Text(text = dog.ageRange.toUpperCase(Locale.getDefault()))
            DotSymbol()
            Text(text = dog.sex.toUpperCase(Locale.getDefault()))
            DotSymbol()
            Text(text = dog.size.toUpperCase(Locale.getDefault()))
        }
        Divider(
            color = purple500,
            modifier = modifier.padding(start = 70.dp, end = 70.dp, top = 20.dp, bottom = 20.dp)
        )
        Text(text = dog.meet)
        AboutSection(
            about = dog.about,
            colors = dog.color,
            modifier = modifier.padding(top = 20.dp, bottom = 20.dp)
        )
        Text(
            text = "Interested in learning more about ${dog.name}?",
            modifier = modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(bottom = 10.dp)
        )
        Button(
            onClick = {
                val browserIntent = Intent(
                    Intent.ACTION_VIEW, Uri.parse(dog.adoptionUrl)
                )
                startActivity(context, browserIntent, null)
            },
            modifier = modifier.align(alignment = Alignment.CenterHorizontally)
        ) {
            Text(text = "Learn more".toUpperCase(Locale.getDefault()))
        }
    }
}

@Composable
fun AboutSection(about: About, colors: String?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        AboutRowSingle(title = "Color", item = colors)
        AboutRowSingle(title = "Coat Length", item = about.coatLength)
        AboutRowSingle(
            title = "House trained",
            item = if (about.houseTrained) "Yes" else null
        )
        AboutRow(title = "Health", list = about.health)
        AboutRow(title = "Good in a home with", list = about.goodInHomeWith)
        AboutRow(title = "Needs a home without", list = about.needsHomeWithout)
        AboutRowSingle(
            title = "Adoption Fee",
            item = about.adoptionFee?.let { "$$it" }
        )
    }
}

@Composable
fun AboutRowSingle(title: String, item: String?, modifier: Modifier = Modifier) {
    item?.let {
        Row(
            modifier = modifier.padding(bottom = 8.dp)
        ) {
            Text(text = "$title: ", fontWeight = FontWeight.Bold)
            Text(text = item)
        }
    }
}

@Composable
fun AboutRow(title: String, list: List<String>, modifier: Modifier = Modifier) {
    if (list.isNotEmpty()) {
        Row(
            modifier = modifier.padding(bottom = 8.dp)
        ) {
            Text("$title: ", fontWeight = FontWeight.Bold)
            Text(text = list.joinToString(", "))
        }
    }
}

@Composable
fun DotSymbol(modifier: Modifier = Modifier) {
    Text(text = "•", modifier = modifier)
}

@Composable
fun EmptyState() {
}
