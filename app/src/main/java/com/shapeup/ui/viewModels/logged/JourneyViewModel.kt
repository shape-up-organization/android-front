package com.shapeup.ui.viewModels.logged

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.shapeup.service.friends.getAllFriendshipMock
import com.shapeup.service.users.getUserDataMock

class JourneyViewModel : ViewModel() {
    val friends = mutableStateOf<List<User>>(emptyList())
    val userData = mutableStateOf(getUserDataMock)

    private fun getFriends(): List<User>? {
        // TODO: implement getFriends from service
        friends.value = getAllFriendshipMock
        return friends.value
    }

    private fun getUser(username: String): User? {
        return when (getUserRelation(username)) {
            EUserRelation.USER -> JourneyMappers.userDataToUser(userData.value)

            EUserRelation.FRIEND -> friends.value.find {
                it.username == username
            }

            // TODO: implement userGet from service before returning null
            else -> null
        }
    }

    private fun getUserRelation(username: String): EUserRelation {
        if (userData.value.username == username) return EUserRelation.USER

        friends.value.forEach {
            if (it.username == username) {
                return EUserRelation.FRIEND
            }
        }

        return EUserRelation.NON_FRIEND
    }

    private fun updateProfilePicture(profilePicture: Uri) {
        println("profilePicture $profilePicture")

        if (Uri.parse(userData.value.profilePicture) != profilePicture) {
            println("UPDATE PROFILE PICTURE!")
        } else {
            println("DO NOT UPDATE PROFILE PICTURE!")
        }
    }

    private fun updateUserData(newUserData: UserDataUpdate) {
        updateProfilePicture(newUserData.profilePicture)

        println("firstName ${newUserData.firstName}")
        println("lastName ${newUserData.lastName}")
        println("username ${newUserData.username}")
        println("bio ${newUserData.bio}")
    }

    private fun logOut() {}

    val handlers = JourneyHandlers(
        getFriends = ::getFriends,
        getUser = ::getUser,
        getUserRelation = ::getUserRelation,
        logOut = ::logOut,
        updateProfilePicture = ::updateProfilePicture,
        updateUserData = ::updateUserData
    )
}

data class JourneyData(
    val friends: MutableState<List<User>>,
    val userData: MutableState<UserData>
)

val journeyDataMock = JourneyData(
    friends = mutableStateOf(emptyList()),
    userData = mutableStateOf(getUserDataMock)
)

data class JourneyHandlers(
    val getFriends: () -> List<User>?,
    val getUser: (username: String) -> User?,
    val getUserRelation: (username: String) -> EUserRelation,
    val logOut: () -> Unit,
    val updateProfilePicture: (profilePicture: Uri) -> Unit,
    val updateUserData: (newUserData: UserDataUpdate) -> Unit
)

val journeyHandlersMock = JourneyHandlers(
    getFriends = { getAllFriendshipMock },
    getUser = { JourneyMappers.userDataToUser(getUserDataMock) },
    getUserRelation = { EUserRelation.USER },
    logOut = {},
    updateProfilePicture = {},
    updateUserData = {}
)

data class User(
    val firstName: String,
    val lastName: String,
    val online: Boolean,
    val profilePicture: String? = null,
    val username: String,
    val xp: Int
)

data class UserData(
    val bio: String? = "",
    val birth: String,
    val cellPhone: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val profilePicture: String? = null,
    val username: String,
    val xp: Int
)

data class UserDataUpdate(
    val bio: String? = "",
    val firstName: String,
    val lastName: String,
    val profilePicture: Uri,
    val username: String
)

object JourneyMappers {
    val userDataToUser: (userData: UserData) -> User = {
        User(
            firstName = it.firstName,
            lastName = it.lastName,
            online = true,
            profilePicture = it.profilePicture,
            username = it.username,
            xp = it.xp
        )
    }
}

enum class EUserRelation {
    USER,
    FRIEND,
    NON_FRIEND,
    NON_FRIEND_RECEIVED,
    NON_FRIEND_REQUESTED
}
