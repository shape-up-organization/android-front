package com.shapeup.api.services.users

import com.shapeup.ui.viewModels.logged.FriendshipStatus
import com.shapeup.ui.viewModels.logged.User
import com.shapeup.ui.viewModels.logged.UserData

val getUserDataMock = UserData(
    biography = "Lorem ipsum dolor sit amet lorem ipsum dolor sit amet lorem ipsum dolor sit amet",
    birth = "2003/09/09",
    cellPhone = "11983068373",
    email = "Georgia",
    firstName = "Georgia",
    id = "1",
    lastName = "Johnston",
    password = "123",
    profilePicture = "https://picsum.photos/id/57/2448/3264",
    username = "g_johnston",
    xp = 700
)

val getAllSearchUserDataMock = listOf(
    User(
        firstName = "Georgia Collins",
        id = "1",
        lastName = "Johnston",
        profilePicture = "https://picsum.photos/id/57/2448/3264",
        username = "g_johnston",
        xp = 1200,
        online = true,
        friendshipStatus = FriendshipStatus(
            haveFriendRequest = true,
            isFriend = false,
            userSenderFriendshipRequest = ""
        )
    ),

    User(
        firstName = "Georgia",
        id = "2",
        lastName = "Johnston",
        profilePicture = "https://picsum.photos/id/57/2448/3264",
        username = "g_johnston",
        xp = 700,
        online = false,
        friendshipStatus = FriendshipStatus(
            haveFriendRequest = false,
            isFriend = true,
            userSenderFriendshipRequest = ""
        )
    )
)
