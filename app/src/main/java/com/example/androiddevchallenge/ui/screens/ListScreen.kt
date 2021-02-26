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

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import coil.transform.CircleCropTransformation
import com.example.androiddevchallenge.data.Dog
import com.example.androiddevchallenge.ui.MainViewModel
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun DogListScreen(navController: NavController, mainViewModel: MainViewModel) {
    val dogs = mainViewModel.dogs.observeAsState()
    ListContent(
        dogs = dogs.value ?: emptyList(),
        onDogClick = { id ->
            mainViewModel.onDogClick(id)
            navController.navigate(Screen.Details.route)
        }
    )
}

@Composable
fun ListContent(dogs: List<Dog>, onDogClick: (Int) -> Unit) {
    val scrollState = rememberLazyListState()
    Surface(color = MaterialTheme.colors.background) {
        LazyColumn(state = scrollState) {
            items(dogs.size) { index ->
                if (index == 0) {
                    // add header
                }
                DogRow(
                    dog = dogs[index],
                    onDogClick = onDogClick
                )
            }
        }
    }
}

@Composable
fun DogRow(dog: Dog, onDogClick: (Int) -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(color = MaterialTheme.colors.surface)
            .clickable(onClick = { onDogClick.invoke(dog.id) })
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        CoilImage(
            data = dog.imageUrl,
            requestBuilder = {
                transformations(CircleCropTransformation())
            },
            contentDescription = "${dog.name}'s image",
            modifier = Modifier.size(50.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(dog.name, fontWeight = FontWeight.Bold)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(dog.breed, style = MaterialTheme.typography.body2)
            }
        }
    }
}
