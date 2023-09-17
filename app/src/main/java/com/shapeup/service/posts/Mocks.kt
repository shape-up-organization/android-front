package com.shapeup.service.posts

import com.shapeup.ui.viewModels.logged.Post

val getPostsMock = listOf(
    Post(
        countComments = 13,
        countLike = 22,
        createdAt = "14/04/2023",
        description = "Lorem ipsum dolor sit amet lorem ipsum dolor." +
            "\nLorem ipsum dolor sit amet lorem ipsum dolor, lorem ipsum " +
            "\n\nLorem ipsum dolor sit amet lorem ipsum dolor, lorem ipsum",
        id = "1",
        liked = true,
        photoUrls = listOf(
            "https://picsum.photos/id/42/3456/2304",
            "https://picsum.photos/id/59/2464/1632",
            "https://picsum.photos/id/76/4912/3264"
        ),
        username = "barto"
    ),
    Post(
        countComments = 48,
        countLike = 89,
        createdAt = "13/04/2023",
        description = "Lorem ipsum dolor sit amet lorem ipsum dolor." +
            "\n\nLorem ipsum dolor sit amet lorem ipsum dolor, lorem ipsum " +
            "Lorem ipsum dolor sit amet lorem ipsum dolor, lorem ipsum\n" +
            "dolor sit amet lorem ipsum dolor. Lorem ipsum dolor sit amet lorem ipsum dolor.",
        id = "2",
        liked = false,
        photoUrls = emptyList(),
        username = "g_johnston"
    ),
    Post(
        countComments = 68,
        countLike = 244,
        createdAt = "12/04/2023",
        description = "Lorem ipsum dolor sit amet lorem ipsum dolor.",
        id = "3",
        liked = false,
        photoUrls = emptyList(),
        username = "fffred"
    ),
    Post(
        countComments = 120,
        countLike = 118,
        createdAt = "11/04/2023",
        description = null,
        id = "4",
        liked = true,
        photoUrls = listOf(
            "https://picsum.photos/id/56/2880/1920",
            "https://picsum.photos/id/57/2448/3264"
        ),
        username = "will_sigma"
    ),
    Post(
        countComments = 39,
        countLike = 110,
        createdAt = "13/04/2023",
        description = "Lorem ipsum dolor sit amet lorem ipsum dolor." +
            "\n\nLorem ipsum dolor sit amet lorem ipsum dolor, lorem ipsum " +
            "Lorem ipsum dolor sit amet lorem ipsum dolor, lorem ipsum\n" +
            "Lorem ipsum dolor sit amet lorem ipsum dolor, lorem ipsum\n" +
            "Lorem ipsum dolor sit amet lorem ipsum dolor, lorem ipsum\n" +
            "Lorem ipsum dolor sit amet lorem ipsum dolor, lorem ipsum\n" +
            "Lorem ipsum dolor sit amet lorem ipsum dolor, lorem ipsum\n" +
            "Lorem ipsum dolor sit amet lorem ipsum dolor, lorem ipsum\n" +
            "Lorem ipsum dolor sit amet lorem ipsum dolor, lorem ipsum\n" +
            "Lorem ipsum dolor sit amet lorem ipsum dolor, lorem ipsum\n" +
            "Lorem ipsum dolor sit amet lorem ipsum dolor, lorem ipsum\n" +
            "Lorem ipsum dolor sit amet lorem ipsum dolor, lorem ipsum\n" +
            "Lorem ipsum dolor sit amet lorem ipsum dolor, lorem ipsum\n" +
            "Lorem ipsum dolor sit amet lorem ipsum dolor, lorem ipsum\n" +
            "dolor sit amet lorem ipsum dolor. Lorem ipsum dolor sit amet lorem ipsum dolor.",
        id = "5",
        liked = false,
        photoUrls = listOf(
            "https://picsum.photos/id/103/2592/1936"
        ),
        username = "g_johnston"
    )
)
