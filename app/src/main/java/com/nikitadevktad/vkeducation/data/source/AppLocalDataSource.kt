package com.nikitadevktad.vkeducation.data.source

import javax.inject.Inject
import com.nikitadevktad.vkeducation.data.dto.AppDto

class AppLocalDataSource @Inject constructor() {
    fun getApps(): List<AppDto> = listOf(
    AppDto(id = "quick-note",
        name = "QuickNote",
        description = "Приложение для создания заметок",
        category = "Производительность",
        iconUrl = "https://picsum.photos/id/237/200/200"
    ),
    AppDto(id = "calmalist",
        name = "Calmalist",
        description = "Планирование задач и управление делами",
        category = "Производительность",
        iconUrl = "https://picsum.photos/id/238/200/200"
    ),
    AppDto(id = "fit-river",
        name = "FitRiver",
        description = "Тренировки и отслеживание активности",
        category = "Спорт",
        iconUrl = "https://picsum.photos/id/239/200/200"
    ),
    AppDto(id = "task-loop",
        name = "TaskLoop",
        description = "Удобный планировщик ежедневных задач",
        category = "Производительность",
        iconUrl = "https://picsum.photos/id/240/200/200"
    ),
    AppDto(id = "pixtrix",
        name = "Pixtrix",
        description = "Редактор фотографий и изображений",
        category = "Фотография",
        iconUrl = "https://picsum.photos/id/241/200/200"
    ),
    AppDto(id = "tiny-chef",
        name = "TinyChef",
        description = "Рецепты и идеи для приготовления блюд",
        category = "Еда",
        iconUrl = "https://picsum.photos/id/242/200/200"
    ),
    AppDto(id = "gym-mate",
        name = "GymMate",
        description = "Персональный помощник для тренировок",
        category = "Здоровье и спорт",
        iconUrl = "https://picsum.photos/id/243/200/200"
    ),
    AppDto(id = "flow-card",
        name = "FlowCard",
        description = "Создание карточек для изучения информации",
        category = "Образование",
        iconUrl = "https://picsum.photos/id/244/200/200"
    ),
    AppDto(id = "dream-safe",
        name = "DreamSafe",
        description = "Отслеживание сна и отдыха",
        category = "Здоровье",
        iconUrl = "https://picsum.photos/id/2/200/200"
    ),
    AppDto(id = "mood-pin",
        name = "MoodPin",
        description = "Дневник настроения и эмоций",
        category = "Здоровье",
        iconUrl = "https://picsum.photos/id/3/200/200"
    ),
    AppDto(id = "shoply",
        name = "Shoply",
        description = "Удобный список покупок",
        category = "Покупки",
        iconUrl = "https://picsum.photos/id/247/200/200"
    ),
    AppDto(id = "feed-joy",
        name = "FeedJoy",
        description = "Лента интересных публикаций",
        category = "Социальные сети",
        iconUrl = "https://picsum.photos/id/248/200/200"
    ),
    AppDto(id = "scanwise",
        name = "Scanwise",
        description = "Сканирование документов",
        category = "Утилиты",
        iconUrl = "https://picsum.photos/id/249/200/200"
    ),
    AppDto(id = "memo-spark",
        name = "MemoSpark",
        description = "Заметки и сохранение идей",
        category = "Производительность",
        iconUrl = "https://picsum.photos/id/250/200/200"
    ),
    AppDto(id = "zen-radio",
        name = "ZenRadio",
        description = "Музыка и радиостанции для отдыха",
        category = "Музыка",
        iconUrl = "https://picsum.photos/id/251/200/200"
    )
    )
}
