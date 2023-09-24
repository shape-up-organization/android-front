package com.shapeup.ui.viewModels.logged

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.shapeup.service.friends.getAllFriendshipMock
import com.shapeup.service.users.getUserDataMock
import com.shapeup.ui.utils.helpers.Navigator

class JourneyViewModel : ViewModel() {
    lateinit var navigator: Navigator

    val friends = mutableStateOf<List<User>>(emptyList())
    val userData = mutableStateOf(getUserDataMock)

    private fun getFriends(): List<User>? {
        // TODO: implement getFriends from service
        friends.value = getAllFriendshipMock
        return friends.value
    }

    private fun getUser(username: String): User? {
        if (userData.value.username == username) return userData.value

        friends.value.forEach {
            if (it.username == username) {
                return it
            }
        }

        // TODO: implement userGet from service before returning null
        return null
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

    private fun logOut() {}

    val handlers = JourneyHandlers(
        getFriends = ::getFriends,
        getUser = ::getUser,
        getUserRelation = ::getUserRelation,
        logOut = ::logOut
    )
}

data class JourneyData(
    val friends: MutableState<List<User>>,
    val userData: MutableState<User>
)

val journeyDataMock = JourneyData(
    friends = mutableStateOf(emptyList()),
    userData = mutableStateOf(getUserDataMock)
)

data class JourneyHandlers(
    val getFriends: () -> List<User>?,
    val getUser: (username: String) -> User?,
    val getUserRelation: (username: String) -> EUserRelation,
    val logOut: () -> Unit
)

val journeyHandlersMock = JourneyHandlers(
    getFriends = { getAllFriendshipMock },
    getUser = { getUserDataMock },
    getUserRelation = { EUserRelation.USER },
    logOut = {}
)

data class User(
    val firstName: String,
    val lastName: String,
    val online: Boolean,
    val profilePicture: String? = null,
    val username: String,
    val xp: Int
)

enum class EUserRelation {
    USER,
    FRIEND,
    NON_FRIEND,
    NON_FRIEND_RECEIVED,
    NON_FRIEND_REQUESTED
}
