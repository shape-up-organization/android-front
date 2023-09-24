package com.shapeup.service.friends

import com.shapeup.ui.viewModels.logged.Message
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
        lastName = "Augustss Don Corleo De San Judas Tireon",
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

val getFriendsMessagesMock = listOf(
    Message(
        date = "2023/09/09",
        message = "Hello there!",
        receiverName = "fffred",
        senderName = "g_johnston"
    ),
    Message(
        date = "2023/09/10",
        message = "Hello there!!!!",
        receiverName = "g_johnston",
        senderName = "will_sigma"
    ),
    Message(
        date = "2023/09/12",
        message = "Hello there!!!!",
        receiverName = "g_johnston",
        senderName = "will_sigma"
    ),
    Message(
        date = "2023/09/13",
        message = "Hello there!!!! Lorem ipsum dolor sit amet lorem ipsum dolor.",
        receiverName = "will_sigma",
        senderName = "g_johnston"
    ),
    Message(
        date = "2023/09/14",
        message = "Hello there!!!!",
        receiverName = "g_johnston",
        senderName = "fffred"
    )
)
