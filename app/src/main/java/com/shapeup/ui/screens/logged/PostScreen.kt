package com.shapeup.ui.screens.logged

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.shapeup.service.posts.getPostsMock
import com.shapeup.service.users.getUserDataMock
import com.shapeup.ui.components.CardPost
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.viewModels.logged.Post
import com.shapeup.ui.viewModels.logged.User

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PostPreview() {
    ShapeUpTheme {
        PostScreen(
            navigator = Navigator(),
            postData = getPostsMock[0],
            user = getUserDataMock
        )
    }
}

@Composable
fun PostScreen(
    navigator: Navigator,
    postData: Post,
    user: User
) {
    CardPost(
        fullScreen = true,
        navigator = navigator,
        postData = postData,
        user = user
    )
}
