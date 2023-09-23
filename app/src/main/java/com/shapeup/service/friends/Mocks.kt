package com.shapeup.service.friends

import com.shapeup.ui.viewModels.logged.User

val getAllFriendshipMock = listOf(
    User(
        firstName = "Bartolomeo",
        lastName = "Kingston",
        online = true,
        profilePicture = "https://picsum.photos/id/57/2448/3264",
        username = "barto",
        xp = 120
    ),
    User(
        firstName = "Fred",
        lastName = "August",
        online = false,
        profilePicture = null,
        username = "fffred",
        xp = 450
    ),
    User(
        firstName = "Will",
        lastName = "Sigma",
        online = true,
        profilePicture = "https://picsum.photos/id/56/2880/1920",
        username = "will_sigma",
        xp = 890
    )
)
