package com.shapeup.api.services.users

import com.shapeup.ui.viewModels.logged.FriendshipStatus
import com.shapeup.ui.viewModels.logged.UserData

val getUserDataEmpty = UserData(
    biography = "",
    birth = "10/10/2010",
    cellPhone = "",
    email = "",
    firstName = "",
    id = "",
    lastName = "",
    password = "",
    profilePicture = null,
    username = "",
    xp = 1
)

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
    UserSearch(
        firstName = "Georgia Collins",
        lastName = "Johnston",
        profilePicture = "https://picsum.photos/id/57/2448/3264",
        username = "g_johnston",
        xp = 1200,
        friendshipStatus = FriendshipStatus(
            haveFriendRequest = true,
            isFriend = false,
            userSenderFriendshipRequest = ""
        )
    ),
    UserSearch(
        firstName = "Georgia",
        lastName = "Johnston",
        profilePicture = "https://picsum.photos/id/57/2448/3264",
        username = "g_johnston",
        xp = 700,
        friendshipStatus = FriendshipStatus(
            haveFriendRequest = false,
            isFriend = true,
            userSenderFriendshipRequest = ""
        )
    )
)

val getRankGlobalDataMock = listOf(
    UserRank(
        firstName = "Georgia Collins",
        lastName = "Johnston",
        profilePicture = "https://picsum.photos/id/57/2448/3264",
        username = "g_johnston",
        xp = 1200,
    ),
    UserRank(
        firstName = "Will",
        lastName = "Sigma",
        profilePicture = "https://picsum.photos/id/56/2880/1920",
        username = "will_sigma",
        xp = 700,
    )
)
