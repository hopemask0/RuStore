package com.example.rustore.data.models

data class App(
    val id: String,
    val name: String,
    val developer: String,
    val description: String,
    val shortDescription: String,
    val category: AppCategory,
    val ageRating: AgeRating,
    val iconRes: Int, // ID ресурса для иконки
    val screenshots: List<Int> // ID ресурсов для скриншотов
)

enum class AppCategory {
    FINANCE,
    TOOLS,
    GAMES,
    GOVERNMENT,
    TRANSPORT
}

enum class AgeRating(val displayName: String) {
    ZERO_PLUS("0+"),
    SIX_PLUS("6+"),
    EIGHT_PLUS("8+"),
    TWELVE_PLUS("12+"),
    SIXTEEN_PLUS("16+"),
    EIGHTEEN_PLUS("18+")
}