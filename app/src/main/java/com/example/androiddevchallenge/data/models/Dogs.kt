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
package com.example.androiddevchallenge.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Dogs(
    val dogs: List<Dog>
)

@JsonClass(generateAdapter = true)
data class Dog(
    val id: Int,
    val name: String,
    val breed: String,
    val location: String,
    @Json(name = "age_range") val ageRange: String,
    val sex: String,
    val size: String,
    val color: String?,
    val meet: String,
    @Json(name = "image_url") val imageUrl: String,
    @Json(name = "url") val adoptionUrl: String,
    val about: About
)

@JsonClass(generateAdapter = true)
data class About(
    @Json(name = "coat_length") val coatLength: String?,
    @Json(name = "house_trained") val houseTrained: Boolean = false,
    @Json(name = "health") val health: List<String>,
    @Json(name = "good_in_home") val goodInHomeWith: List<String> = emptyList(),
    @Json(name = "home_without") val needsHomeWithout: List<String> = emptyList(),
    @Json(name = "fee") val adoptionFee: Int?
)
