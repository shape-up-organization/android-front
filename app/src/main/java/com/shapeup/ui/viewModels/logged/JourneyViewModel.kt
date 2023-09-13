package com.shapeup.ui.viewModels.logged

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.shapeup.service.friends.getAllFriendshipMock
import com.shapeup.service.users.getUserDataMock
import com.shapeup.ui.utils.helpers.Navigator

class JourneyViewModel : ViewModel() {
    var navigator: Navigator? = null

//    val friends = mutableStateOf<List<User>>(emptyList())
    val friends = mutableStateOf<List<User>>(getAllFriendshipMock)
    val selectedUser = mutableStateOf<User?>(null)
    val userData = mutableStateOf(getUserDataMock)

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
        getUserRelation = ::getUserRelation,
        logOut = ::logOut
    )
}

class JourneyData(
    val friends: MutableState<List<User>>,
    val selectedUser: MutableState<User?>,
    val userData: MutableState<User>
)

class JourneyHandlers(
    val getUserRelation: (String) -> EUserRelation,
    val logOut: () -> Unit
)

enum class EUserRelation {
    USER,
    FRIEND,
    NON_FRIEND
}

data class User(
    val firstName: String,
    val lastName: String,
    val online: Boolean,
    val profilePicture: String? = null,
    val username: String,
    val xp: Int
)
