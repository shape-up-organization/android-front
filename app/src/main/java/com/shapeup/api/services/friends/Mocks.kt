package com.shapeup.api.services.friends

import com.shapeup.ui.viewModels.logged.Message
import com.shapeup.ui.viewModels.logged.User

val getAllFriendshipMock = listOf(
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
        message = "Hello there!!!! Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
            "Vivamus hendrerit nisi at vehicula. Vestibulum ante ipsum primis in faucibus " +
            "orci luctus et ultrices posuere cubilia Curae; Sed efficitur, urna non cursus " +
            "varius, purus nisi tristique tellus, eget congue purus sem id mi. Vivamus " +
            "consectetur, eros in interdum hendrerit, nunc tellus congue nulla, at pulvinar " +
            "purus sem at ex. Suspendisse potenti. Donec nec semper nisl. Sed quis libero " +
            "at dolor semper elementum. Maecenas id tincidunt felis.",
        receiverName = "will_sigma",
        senderName = "g_johnston"
    ),
    Message(
        date = "2023/09/14",
        message = "Hello there!!!!",
        receiverName = "g_johnston",
        senderName = "fffred"
    ),
    Message(
        date = "2023/09/15",
        message = "Hey, what's up?",
        receiverName = "g_johnston",
        senderName = "will_sigma"
    ),
    Message(
        date = "2023/09/16",
        message = "Not much, just working on some code.",
        receiverName = "g_johnston",
        senderName = "g_johnston"
    ),
    Message(
        date = "2023/09/17",
        message = "That sounds interesting!",
        receiverName = "g_johnston",
        senderName = "will_sigma"
    ),
    Message(
        date = "2023/09/18",
        message = "Yeah, it's a challenging project.",
        receiverName = "g_johnston",
        senderName = "g_johnston"
    ),
    Message(
        date = "2023/09/19",
        message = "Good morning!",
        receiverName = "will_sigma",
        senderName = "g_johnston"
    ),
    Message(
        date = "2023/09/20",
        message = "Hello!",
        receiverName = "g_johnston",
        senderName = "barto"
    ),
    Message(
        date = "2023/09/21",
        message = "How's your day going?",
        receiverName = "g_johnston",
        senderName = "barto"
    ),
    Message(
        date = "2023/09/22",
        message = "It's going well, thanks!",
        receiverName = "barto",
        senderName = "g_johnston"
    ),
    Message(
        date = "2023/09/23",
        message = "What are your plans for the weekend?",
        receiverName = "g_johnston",
        senderName = "barto"
    ),
    Message(
        date = "2023/09/24",
        message = "This is a really long message that contains a lot of text. It goes on and " +
            "on, and it's quite verbose. Lorem ipsum dolor sit amet, consectetur adipiscing " +
            "elit. Vivamus hendrerit nisi at vehicula. Vestibulum ante ipsum primis in " +
            "faucibus orci luctus et ultrices posuere cubilia Curae; Sed efficitur, urna " +
            "non cursus varius, purus nisi tristique tellus, eget congue purus sem id mi. " +
            "Vivamus consectetur, eros in interdum hendrerit, nunc tellus congue nulla, at " +
            "pulvinar purus sem at ex. Suspendisse potenti. Donec nec semper nisl. Sed quis " +
            "libero at dolor semper elementum. Maecenas id tincidunt felis.",
        receiverName = "g_johnston",
        senderName = "will_sigma"
    ),
    Message(
        date = "2023/09/25",
        message = "Another long message here. Lorem ipsum dolor sit amet, consectetur adipiscing " +
            "elit. Vivamus hendrerit nisi at vehicula. Vestibulum ante ipsum primis in " +
            "faucibus orci luctus et ultrices posuere cubilia Curae; Sed efficitur, urna " +
            "non cursus varius, purus nisi tristique tellus, eget congue purus sem id mi. " +
            "Vivamus consectetur, eros in interdum hendrerit, nunc tellus congue nulla, at " +
            "pulvinar purus sem at ex. Suspendisse potenti. Donec nec semper nisl. Sed quis " +
            "libero at dolor semper elementum. Maecenas id tincidunt felis.",
        receiverName = "g_johnston",
        senderName = "barto"
    )
)
