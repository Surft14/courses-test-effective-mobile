# Приложение "Курсы" (Android тестовое задание)

## Описание

Мобильное приложение для просмотра списка курсов с возможностью поиска, сортировки, добавления в избранное и просмотра подробной информации о каждом курсе.

## Функционал

- Кэширование данных в локальную БД (Room)
- Поиск курсов по названию и описанию
- Сортировка по дате (возрастание/убывание)
- Добавление/удаление курсов в избранное
- Экран с деталями курса
- Современный UI и Material Design

## Технологии

- Kotlin
- MVVM
- Clean Architecture (data/app модули)
- Room
- Retrofit + Gson
- Koin (Dependency Injection)
- Coroutines + StateFlow
- AdapterDelegates4
- ViewBinding / Jetpack Compose (оставь нужное)
- Material Components

## Структура проекта

```

/app          - UI, ViewModel, Activity/Fragment
/data         - работа с API, Room, репозитории, модели
/di           - Koin модули
/network      - API (Retrofit)

````

## Архитектура

Проект построен по MVVM с разделением на модули data/app.  
В качестве DI используется Koin.


---

**Разработчик:** \[Журавлев Костя]
**Контакты:** \[mr.darck31@gmail.com / [телеграм](https://t.me/Surft14)]

