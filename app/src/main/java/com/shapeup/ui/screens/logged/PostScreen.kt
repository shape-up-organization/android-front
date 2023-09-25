package com.shapeup.ui.screens.logged

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.shapeup.service.posts.getPostsMock
import com.shapeup.service.users.getUserDataMock
import com.shapeup.ui.components.CardPost
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.viewModels.logged.EUserRelation
import com.shapeup.ui.viewModels.logged.JourneyMappers
import com.shapeup.ui.viewModels.logged.Post
import com.shapeup.ui.viewModels.logged.PostsHandlers
import com.shapeup.ui.viewModels.logged.User
import com.shapeup.ui.viewModels.logged.postsHandlersMock

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PostPreview() {
    ShapeUpTheme {
        PostScreen(
            navigator = Navigator(),
            postData = getPostsMock[0],
            postsHandlers = postsHandlersMock,
            user = JourneyMappers.userDataToUser(getUserDataMock),
            userRelation = EUserRelation.USER
        )
    }
}

@Composable
fun PostScreen(
    navigator: Navigator,
    postData: Post,
    postsHandlers: PostsHandlers,
    user: User,
    userRelation: EUserRelation
) {
    CardPost(
        fullScreen = true,
        navigator = navigator,
        postData = postData,
        postsHandlers = postsHandlers,
        user = user,
        userRelation = userRelation
    )
}
