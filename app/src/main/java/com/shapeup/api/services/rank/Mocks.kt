package com.shapeup.api.services.rank

import com.shapeup.ui.viewModels.logged.User

val getRankMock = listOf(
    User(
        firstName = "Bartolomeo",
        lastName = "Kingston",
        id = "2",
        online = true,
        profilePicture = "https://picsum.photos/id/57/2448/3264",
        username = "barto",
        xp = 120
    ),
    User(
        firstName = "Fred",
        lastName = "Augustss Don Corleo De San Judas Tireon Asdsasad",
        id = "3",
        online = false,
        profilePicture = null,
        username = "fffred",
        xp = 450
    ),
    User(
        firstName = "Will",
        lastName = "Sigma",
        id = "4",
        online = true,
        profilePicture = "https://picsum.photos/id/56/2880/1920",
        username = "will_sigma",
        xp = 890
    )
)