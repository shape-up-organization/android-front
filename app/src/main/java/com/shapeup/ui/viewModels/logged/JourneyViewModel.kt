package com.shapeup.ui.viewModels.logged

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.shapeup.api.services.friends.getAllFriendshipMock
import com.shapeup.api.services.friends.getFriendsMessagesMock
import com.shapeup.api.services.users.getUserDataMock
import com.shapeup.api.utils.helpers.SharedData
import com.shapeup.service.users.getAllSearchUserDataMock
import java.time.LocalDateTime
import kotlin.random.Random

class JourneyViewModel : ViewModel() {
    lateinit var sharedData: SharedData

    val friends = mutableStateOf<List<Friend>>(emptyList())
    val userData = mutableStateOf(getUserDataMock)

    private fun signOut() {
        sharedData.delete("jwtToken")
        sharedData.delete("email")
        sharedData.delete("password")
    }

    private fun getFriends(): List<Friend> {
        // TODO: implement getFriends from service
        val friendsList = getAllFriendshipMock

        // TODO: implement getFriendsMessages from service
        fun getFriendsMessages(username: String): MutableList<Message> {
            return getFriendsMessagesMock.filter {
                it.receiverName == username || it.senderName == username
            }.toMutableList()
        }

        val friendsWithMessages = friendsList.map {
            Friend(
                messages = getFriendsMessages(it.username),
                online = getFriendStatus(it.username),
                user = it
            )
        }

        friends.value = friendsWithMessages
        return friends.value
    }

    private fun getFriendStatus(username: String): Boolean {
        // TODO: implement getFriendStatus from service
        return Random.nextBoolean()
    }

    private fun getUser(username: String): User? {
        return when (getUserRelation(username)) {
            EUserRelation.USER -> JourneyMappers.userDataToUser(userData.value)

            EUserRelation.FRIEND -> friends.value.find {
                it.user.username == username
            }?.user

            // TODO: implement userGet from service before returning null
            else -> null
        }
    }

    private fun getUserRelation(username: String): EUserRelation {
        if (userData.value.username == username) return EUserRelation.USER

        friends.value.forEach {
            if (it.user.username == username) {
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

    private fun sendMessage(messageText: String, friendUsername: String) {
        println("message: $messageText")

        val mountedMessage = Message(
            date = LocalDateTime.now().toString(),
            message = messageText,
            receiverName = friendUsername,
            senderName = userData.value.username
        )

        // TODO: send message service
        friends.value.forEach {
            if (it.user.username == friendUsername) {
                it.messages.add(mountedMessage)
            }
        }
    }

    private fun getRankFriends(): List<User> {
        return getAllFriendshipMock.sortedByDescending { it.xp }
    }

    private fun getRankGlobal(): List<User> {
        return getAllFriendshipMock.sortedByDescending { it.xp }
    }

    private fun getSearchUsers(): List<User> {
        return getAllSearchUserDataMock
    }

    private fun logOut() {}

    val handlers = JourneyHandlers(
        signOut = ::signOut,
        getFriends = ::getFriends,
        getFriendStatus = ::getFriendStatus,
        getUser = ::getUser,
        getUserRelation = ::getUserRelation,
        logOut = ::logOut,
        updateProfilePicture = ::updateProfilePicture,
        updateUserData = ::updateUserData,
        sendMessage = ::sendMessage,
        getRankFriends = ::getRankFriends,
        getRankGlobal = ::getRankGlobal,
        getSearchUsers = ::getSearchUsers
    )
}

data class JourneyData(
    val friends: MutableState<List<Friend>>,
    val userData: MutableState<UserData>
)

val journeyDataMock = JourneyData(
    friends = mutableStateOf(emptyList()),
    userData = mutableStateOf(getUserDataMock)
)

data class JourneyHandlers(
    val signOut: () -> Unit,
    val getFriends: () -> List<Friend>?,
    val getFriendStatus: (username: String) -> Boolean,
    val getUser: (username: String) -> User?,
    val getUserRelation: (username: String) -> EUserRelation,
    val logOut: () -> Unit,
    val updateProfilePicture: (profilePicture: Uri) -> Unit,
    val updateUserData: (newUserData: UserDataUpdate) -> Unit,
    val sendMessage: (messageText: String, friendUsername: String) -> Unit,
    val getRankFriends: () -> List<User>,
    val getRankGlobal: () -> List<User>,
    val getSearchUsers: () -> List<User>
)

val journeyHandlersMock = JourneyHandlers(
    signOut = {},
    getFriends = {
        val friendsList = getAllFriendshipMock

        friendsList.map {
            Friend(
                user = it,
                messages = getFriendsMessagesMock.filter { message ->
                    message.receiverName == it.username || message.senderName == it.username
                }.toMutableList()
            )
        }
    },
    getFriendStatus = { Random.nextBoolean() },
    getUser = { JourneyMappers.userDataToUser(getUserDataMock) },
    getUserRelation = { EUserRelation.USER },
    logOut = {},
    updateProfilePicture = {},
    updateUserData = {},
    sendMessage = { _, _ -> },
    getRankFriends = {
        getAllFriendshipMock.sortedByDescending { it.xp }
    },
    getRankGlobal = {
        getAllFriendshipMock.sortedByDescending { it.xp }
    },
    getSearchUsers = {
        getAllSearchUserDataMock
    }
)

data class User(
    val firstName: String,
    val lastName: String,
    val id: String,
    val online: Boolean,
    val profilePicture: String? = null,
    val username: String,
    val xp: Int,
    val friendshipStatus: FriendshipStatus? = null
)

data class UserData(
    val bio: String? = "",
    val birth: String,
    val cellPhone: String,
    val email: String,
    val firstName: String,
    val id: String,
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

data class Message(
    val date: String,
    val message: String,
    val receiverName: String,
    val senderName: String
)

data class Friend(
    val messages: MutableList<Message>,
    val online: Boolean = false,
    val user: User
)

data class FriendshipStatus(
    val haveFriendRequest: Boolean,
    val isFriend: Boolean,
    val userSenderFriendshipRequest: String
)

object JourneyMappers {
    val userDataToUser: (userData: UserData) -> User = {
        User(
            firstName = it.firstName,
            lastName = it.lastName,
            id = it.id,
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
