package com.shapeup.ui.viewModels.logged

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.auth0.android.jwt.JWT
import com.shapeup.api.services.friends.AcceptFriendshipRequestPayload
import com.shapeup.api.services.friends.AcceptFriendshipRequestStatement
import com.shapeup.api.services.friends.DeleteFriendPayload
import com.shapeup.api.services.friends.DeleteFriendStatement
import com.shapeup.api.services.friends.DeleteFriendshipRequestPayload
import com.shapeup.api.services.friends.DeleteFriendshipRequestStatement
import com.shapeup.api.services.friends.EFriendsApi
import com.shapeup.api.services.friends.FriendBase
import com.shapeup.api.services.friends.GetAllFriendshipStatement
import com.shapeup.api.services.friends.RequestFriendshipPayload
import com.shapeup.api.services.friends.RequestFriendshipStatement
import com.shapeup.api.services.friends.getAllFriendshipMock
import com.shapeup.api.services.friends.getFriendsMessagesMock
import com.shapeup.api.services.users.EUsersApi
import com.shapeup.api.services.users.GetRankPayload
import com.shapeup.api.services.users.GetRankStatement
import com.shapeup.api.services.users.GetUserPayload
import com.shapeup.api.services.users.GetUserStatement
import com.shapeup.api.services.users.RankType
import com.shapeup.api.services.users.SearchByFullNamePayload
import com.shapeup.api.services.users.SearchByUsernamePayload
import com.shapeup.api.services.users.SearchUsersStatement
import com.shapeup.api.services.users.UpdateProfilePicturePayload
import com.shapeup.api.services.users.UpdateProfilePictureStatement
import com.shapeup.api.services.users.UpdateProfileStatement
import com.shapeup.api.services.users.UserFieldPayload
import com.shapeup.api.services.users.UserSearch
import com.shapeup.api.services.users.getAllSearchUserDataMock
import com.shapeup.api.services.users.getRankGlobalDataMock
import com.shapeup.api.services.users.getUserDataEmpty
import com.shapeup.api.services.users.getUserDataMock
import com.shapeup.api.utils.constants.SharedDataValues
import com.shapeup.api.utils.helpers.SharedData
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import kotlin.random.Random

class JourneyViewModel : ViewModel() {
    lateinit var sharedData: SharedData

    val initialLoad = mutableStateOf(true)
    val friends = mutableStateOf<List<Friend>>(emptyList())
    val userData = mutableStateOf(getUserDataEmpty)

    private fun setupUser(partialData: UserDataPartial? = null) {
        val sharedJwtToken = sharedData.get(SharedDataValues.JwtToken.value)

        if (!sharedJwtToken.isNullOrBlank()) {
            val jwt = JWT(sharedJwtToken)

            userData.value = UserData(
                biography = partialData?.biography ?: jwt.getClaim("biography").asString() ?: "",
                birth = partialData?.birth ?: jwt.getClaim("birth").asString() ?: "",
                cellPhone = partialData?.cellPhone ?: jwt.getClaim("cellPhone").asString() ?: "",
                email = partialData?.email ?: sharedData.get(SharedDataValues.Email.value) ?: "",
                firstName = partialData?.firstName ?: jwt.getClaim("name").asString() ?: "",
                id = partialData?.id ?: jwt.getClaim("id").asString() ?: "",
                lastName = partialData?.lastName ?: jwt.getClaim("lastName").asString() ?: "",
                password = partialData?.password ?: sharedData.get(SharedDataValues.Password.value)
                ?: "",
                profilePicture = partialData?.profilePicture ?: jwt.getClaim("profilePicture")
                    .asString(),
                username = partialData?.username ?: jwt.getClaim("username").asString() ?: "",
                xp = partialData?.xp ?: jwt.getClaim("xp").asInt() ?: 0
            )
        }
    }

    private suspend fun getAllFriendship(): GetAllFriendshipStatement {
        val friendsApi = EFriendsApi.create(sharedData)

        val response = friendsApi.getAllFriendship()

        println(response)

        return response
    }

    private fun setupFriends(friendsList: List<FriendBase>?): List<Friend> {
        // TODO: implement getFriendsMessages from service
        fun getFriendsMessages(username: String): MutableList<Message> {
            return getFriendsMessagesMock.filter {
                it.receiverName == username || it.senderName == username
            }.toMutableList()
        }

        val friendsWithMessages = friendsList?.map {
            Friend(
                messages = getFriendsMessages(it.username),
                online = getFriendStatus(it.username),
                user = it
            )
        }

        friends.value = friendsWithMessages ?: emptyList()
        return friends.value
    }

    private fun getFriendStatus(username: String): Boolean {
        // TODO: implement getFriendStatus from service
        return Random.nextBoolean()
    }

    private suspend fun getUser(username: String): GetUserStatement {
        return when (getUserRelationByUsername(username)) {
            EUserRelation.USER -> GetUserStatement(
                data = JourneyMappers.userDataToUserSearch(userData.value),
                status = HttpStatusCode.OK
            )

            else -> {
                val usersApi = EUsersApi.create(sharedData)

                val response = usersApi.findByUsername(
                    GetUserPayload(
                        username = username
                    )
                )

                println(response)

                return response
            }
        }
    }

    private fun getUserRelationByUsername(username: String): EUserRelation {
        if (userData.value.username == username) return EUserRelation.USER

        friends.value.forEach {
            if (it.user.username == username) {
                return EUserRelation.FRIEND
            }
        }

        return EUserRelation.NON_FRIEND
    }

    private fun getUserRelationByUser(user: UserSearch): EUserRelation {
        if (userData.value.username == user.username) {
            return EUserRelation.USER
        }

        if (user.friendshipStatus?.isFriend == true) {
            return EUserRelation.FRIEND
        }


        if (user.friendshipStatus?.haveFriendRequest == true) {
            if (user.friendshipStatus.userSenderFriendshipRequest == userData.value.username) {
                return EUserRelation.NON_FRIEND_REQUESTED
            }
            return EUserRelation.NON_FRIEND_RECEIVED
        }

        return EUserRelation.NON_FRIEND
    }

    private suspend fun updateProfilePicture(
        profilePicture: ProfilePicture
    ): UpdateProfilePictureStatement {
        val userPic =
            if (userData.value.profilePicture.isNullOrBlank())
                userData.value.profilePicture
            else Uri.parse(
                userData.value.profilePicture
            )

        if (profilePicture.uri != null && userPic != profilePicture.uri) {
            val usersApi = EUsersApi.create(sharedData)

            val response = usersApi.updateProfilePicture(
                UpdateProfilePicturePayload(
                    file = listOf(profilePicture.byteArray),
                )
            )

            println(response)

            return response

        }

        return UpdateProfilePictureStatement(
            status = HttpStatusCode.NotModified
        )
    }

    private suspend fun updateUserData(newUserData: UserDataUpdate): UpdateProfileStatement {
        val picResponse = updateProfilePicture(newUserData.profilePicture)

        val usersApi = EUsersApi.create(sharedData)

        val response = usersApi.updateUserField(
            UserFieldPayload(
                biography = newUserData.bio,
                name = newUserData.firstName,
                lastName = newUserData.lastName,
                username = newUserData.username,
            )
        )

        println(response)

        if (response.status == HttpStatusCode.OK && response.data?.token != null) {
            sharedData.save(SharedDataValues.JwtToken.value, response.data.token)
            setupUser(
                UserDataPartial(
                    profilePicture = picResponse.data?.pictureProfile
                )
            )
        }


        return UpdateProfileStatement(
            pictureStatus = picResponse.status,
            userDataStatus = response.status
        )
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

    private suspend fun getRank(type: RankType): GetRankStatement {
        val usersApi = EUsersApi.create(sharedData)

        val response = usersApi.getRank(
            GetRankPayload(
                type = type,
                page = 0,
                size = 20
            )
        )

        println(response)

        return response
    }

    private suspend fun searchUsers(searchedUser: String): SearchUsersStatement {
        val usersApi = EUsersApi.create(sharedData)

        val response = when (searchedUser.startsWith("@")) {
            true -> usersApi.searchUsersByUsername(
                SearchByUsernamePayload(
                    username = searchedUser.drop(1)
                )
            )

            else -> usersApi.searchUsersByFullName(
                SearchByFullNamePayload(
                    fullName = searchedUser
                )
            )
        }

        println(response)

        return response
    }

    private suspend fun requestFriendship(username: String): RequestFriendshipStatement {
        val friendsApi = EFriendsApi.create(sharedData)

        val response = friendsApi.sendFriendshipRequest(
            RequestFriendshipPayload(
                username = username
            )
        )

        println(response)

        return response
    }

    private suspend fun acceptFriendshipRequest(username: String): AcceptFriendshipRequestStatement {
        val friendsApi = EFriendsApi.create(sharedData)

        val response = friendsApi.acceptFriendshipRequest(
            AcceptFriendshipRequestPayload(
                username = username
            )
        )

        println(response)

        return response
    }

    private suspend fun deleteFriendshipRequest(username: String): DeleteFriendshipRequestStatement {
        val friendsApi = EFriendsApi.create(sharedData)

        val response = friendsApi.deleteFriendshipRequest(
            DeleteFriendshipRequestPayload(
                username = username
            )
        )

        println(response)

        return response
    }

    private suspend fun deleteFriend(username: String): DeleteFriendStatement {
        val friendsApi = EFriendsApi.create(sharedData)

        val response = friendsApi.deleteFriend(
            DeleteFriendPayload(
                username = username
            )
        )

        println(response)

        return response
    }

    val handlers = JourneyHandlers(
        setupUser = ::setupUser,
        getAllFriendship = ::getAllFriendship,
        setupFriends = ::setupFriends,
        getFriendStatus = ::getFriendStatus,
        getUser = ::getUser,
        getUserRelationByUsername = ::getUserRelationByUsername,
        getUserRelationByUser = ::getUserRelationByUser,
        updateProfilePicture = ::updateProfilePicture,
        updateUserData = ::updateUserData,
        sendMessage = ::sendMessage,
        getRank = ::getRank,
        searchUsers = ::searchUsers,
        requestFriendship = ::requestFriendship,
        acceptFriendshipRequest = ::acceptFriendshipRequest,
        deleteFriendshipRequest = ::deleteFriendshipRequest,
        deleteFriend = ::deleteFriend,
    )
}

data class JourneyData(
    val initialLoad: MutableState<Boolean>,
    val friends: MutableState<List<Friend>>,
    val userData: MutableState<UserData>
)

val journeyDataMock = JourneyData(
    initialLoad = mutableStateOf(true),
    friends = mutableStateOf(emptyList()),
    userData = mutableStateOf(getUserDataMock)
)

data class JourneyHandlers(
    val setupUser: () -> Unit,
    val getAllFriendship: suspend () -> GetAllFriendshipStatement,
    val setupFriends: suspend (friendsList: List<FriendBase>?) -> List<Friend>,
    val getFriendStatus: (username: String) -> Boolean,
    val getUser: suspend (username: String) -> GetUserStatement,
    val getUserRelationByUsername: (username: String) -> EUserRelation,
    val getUserRelationByUser: (user: UserSearch) -> EUserRelation,
    val updateProfilePicture: suspend (profilePicture: ProfilePicture) -> UpdateProfilePictureStatement,
    val updateUserData: suspend (newUserData: UserDataUpdate) -> UpdateProfileStatement,
    val sendMessage: (messageText: String, friendUsername: String) -> Unit,
    val getRank: suspend (type: RankType) -> GetRankStatement,
    val searchUsers: suspend (searchedUser: String) -> SearchUsersStatement,
    val requestFriendship: suspend (username: String) -> RequestFriendshipStatement,
    val acceptFriendshipRequest: suspend (username: String) -> AcceptFriendshipRequestStatement,
    val deleteFriendshipRequest: suspend (username: String) -> DeleteFriendshipRequestStatement,
    val deleteFriend: suspend (username: String) -> DeleteFriendStatement
)

val journeyHandlersMock = JourneyHandlers(
    setupUser = {},
    getAllFriendship = {
        GetAllFriendshipStatement(
            status = HttpStatusCode.OK
        )
    },
    setupFriends = {
        val friendsList = getAllFriendshipMock

        friendsList.map {
            Friend(
                user = JourneyMappers.userToFriend(it),
                messages = getFriendsMessagesMock.filter { message ->
                    message.receiverName == it.username || message.senderName == it.username
                }.toMutableList()
            )
        }
    },
    getFriendStatus = { Random.nextBoolean() },
    getUser = {
        GetUserStatement(
            data = JourneyMappers.userDataToUserSearch(getUserDataMock),
            status = HttpStatusCode.OK
        )
    },
    getUserRelationByUsername = { EUserRelation.USER },
    getUserRelationByUser = { EUserRelation.USER },
    updateProfilePicture = {
        UpdateProfilePictureStatement(
            status = HttpStatusCode.OK
        )
    },
    updateUserData = {
        UpdateProfileStatement(
            pictureStatus = HttpStatusCode.NotModified,
            userDataStatus = HttpStatusCode.OK
        )
    },
    sendMessage = { _, _ -> },
    getRank = {
        GetRankStatement(
            data = getRankGlobalDataMock.sortedByDescending { it.xp },
            status = HttpStatusCode.OK
        )
    },
    searchUsers = {
        SearchUsersStatement(
            data = getAllSearchUserDataMock,
            status = HttpStatusCode.OK
        )
    },
    requestFriendship = {
        RequestFriendshipStatement(
            status = HttpStatusCode.OK
        )
    },
    acceptFriendshipRequest = {
        AcceptFriendshipRequestStatement(
            status = HttpStatusCode.OK
        )
    },
    deleteFriendshipRequest = {
        DeleteFriendshipRequestStatement(
            status = HttpStatusCode.OK
        )
    },
    deleteFriend = {
        DeleteFriendStatement(
            status = HttpStatusCode.OK
        )
    }
)

data class User(
    val biography: String? = null,
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
    val biography: String? = "",
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

data class UserDataPartial(
    val biography: String? = null,
    val birth: String? = null,
    val cellPhone: String? = null,
    val email: String? = null,
    val firstName: String? = null,
    val id: String? = null,
    val lastName: String? = null,
    val password: String? = null,
    val profilePicture: String? = null,
    val username: String? = null,
    val xp: Int? = null
)

data class ProfilePicture(
    val uri: Uri? = null,
    val byteArray: ByteArray?
)

data class UserDataUpdate(
    val bio: String? = null,
    val firstName: String,
    val lastName: String,
    val profilePicture: ProfilePicture,
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
    val user: FriendBase
)

@Serializable
data class FriendshipStatus(
    val haveFriendRequest: Boolean,
    val isFriend: Boolean,
    val userSenderFriendshipRequest: String? = null
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

    val userDataToUserSearch: (userData: UserData) -> UserSearch = {
        UserSearch(
            biography = it.biography,
            firstName = it.firstName,
            lastName = it.lastName,
            profilePicture = it.profilePicture,
            username = it.username,
            xp = it.xp
        )
    }

    val userToUserSearch: (user: User) -> UserSearch = {
        UserSearch(
            firstName = it.firstName,
            lastName = it.lastName,
            profilePicture = it.profilePicture,
            username = it.username,
            xp = it.xp,
            friendshipStatus = it.friendshipStatus
        )
    }

    val userToFriend: (user: User) -> FriendBase = {
        FriendBase(
            id = it.id,
            firstName = it.firstName,
            lastName = it.lastName,
            fullName = "${it.firstName} ${it.lastName}",
            username = it.username,
            xp = it.xp,
            profilePicture = it.profilePicture,
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
