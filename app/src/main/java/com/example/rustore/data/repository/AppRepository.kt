package com.example.rustore.data.repository


import com.example.rustore.data.models.App
import com.example.rustore.data.models.AppCategory
import com.example.rustore.data.models.AgeRating
import com.example.rustore.R

class AppRepository {

    fun getAllApps(): List<App> {
        return listOf(
            App(
                id = "1",
                name = "СберБанк Онлайн",
                developer = "Сбербанк",
                description = "Мобильное приложение СберБанка для управления счетами, картами и переводами",
                shortDescription = "Банковские услуги и платежи",
                category = AppCategory.FINANCE,
                ageRating = AgeRating.SIX_PLUS,
                iconRes = R.drawable.ic_sber, // Создадим позже
                screenshots = listOf(R.drawable.sber_screen_1, R.drawable.sber_screen_2)
            ),
            App(
                id = "2",
                name = "Госуслуги",
                developer = "Минцифры России",
                description = "Получение государственных и муниципальных услуг в электронной форме",
                shortDescription = "Государственные услуги онлайн",
                category = AppCategory.GOVERNMENT,
                ageRating = AgeRating.ZERO_PLUS,
                iconRes = R.drawable.ic_gosuslugi,
                screenshots = listOf(R.drawable.gosuslugi_screen_1, R.drawable.gosuslugi_screen_2)
            ),
            App(
                id = "3",
                name = "Яндекс Метро",
                developer = "Яндекс",
                description = "Приложение для построения маршрутов в метро с расчетом времени",
                shortDescription = "Навигация по метро",
                category = AppCategory.TRANSPORT,
                ageRating = AgeRating.ZERO_PLUS,
                iconRes = R.drawable.ic_metro,
                screenshots = listOf(R.drawable.metro_screen_1, R.drawable.metro_screen_2)
            ),
            App(
                id = "4",
                name = "Калькулятор",
                developer = "Simple Apps",
                description = "Простой и удобный калькулятор для повседневных вычислений",
                shortDescription = "Простой калькулятор",
                category = AppCategory.TOOLS,
                ageRating = AgeRating.ZERO_PLUS,
                iconRes = R.drawable.ic_calculator,
                screenshots = listOf(R.drawable.calculator_screen_1, R.drawable.calculator_screen_2)
            ),
            App(
                id = "5",
                name = "Шашки",
                developer = "GameDev Studio",
                description = "Классическая игра в шашки с разными уровнями сложности",
                shortDescription = "Классические шашки",
                category = AppCategory.GAMES,
                ageRating = AgeRating.SIX_PLUS,
                iconRes = R.drawable.ic_checkers,
                screenshots = listOf(R.drawable.checkers_screen_1, R.drawable.checkers_screen_2)
            ),
            App(
                id = "6",
                name = "Т-Банк",
                developer = "АО ТБанк",
                description = "Т-Банк — лучшее приложение для жизни. С нами просто и удобно управлять деньгами, выгодно тратить и получать кэшбэк. Мы предвосхищаем ожидания в продуктах и предоставляем уникальный сервис в каждом из них. Защищаем наших клиентов от мошенников всеми технологичными способами — а круглосуточная поддержка готова быстро помочь.",
                shortDescription = "Управляйте финансами в приложении",
                category = AppCategory.FINANCE,
                ageRating = AgeRating.SIX_PLUS,
                iconRes = R.drawable.ic_tbank,
                screenshots = listOf(R.drawable.tbank_screen_1, R.drawable.tbank_screen_2)
            ),
            App(
                id = "7",
                name = "Сапёр",
                developer = "Касилов Михаил Петрович",
                description = "Та самая игра, в которую играл каждый теперь на андроид. Есть выбор уровня сложности.",
                shortDescription = "Классическая игра в сапёр",
                category = AppCategory.GAMES,
                ageRating = AgeRating.SIX_PLUS,
                iconRes = R.drawable.ic_saper,
                screenshots = listOf(R.drawable.saper_screen_1, R.drawable.saper_screen_2)
            )


        )
    }

    fun getAppsByCategory(category: AppCategory): List<App> {
        return getAllApps().filter { it.category == category }
    }

    fun getAppById(id: String): App? {
        return getAllApps().find { it.id == id }
    }

    fun getCategories(): Map<AppCategory, Int> {
        val apps = getAllApps()
        return AppCategory.values().associateWith { category ->
            apps.count { it.category == category }
        }
    }
}