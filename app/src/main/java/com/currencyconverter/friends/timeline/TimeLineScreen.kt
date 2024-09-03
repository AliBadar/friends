package com.currencyconverter.friends.timeline

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.provider.FontsContractCompat.Columns
import com.currencyconverter.friends.R
import com.currencyconverter.friends.domain.post.Post
import com.currencyconverter.friends.timeline.state.TimeLineState
import com.currencyconverter.friends.ui.composables.ScreenTitle


class TimeLineScreenState {
    var posts by mutableStateOf(emptyList<Post>())

    fun updatePosts(newPosts: List<Post>) {

        posts = newPosts
    }
}

@Composable
fun TimeLineScreen(
    userID: String,
    timeLineViewModel: TimeLineViewModel
) {

    Log.e("userID", "userID: $userID")

    val screenState by remember {
        mutableStateOf(TimeLineScreenState())
    }

    val timeLineState by timeLineViewModel.timeLineState.observeAsState()
    timeLineViewModel.timeLineFor(userID)

    if (timeLineState is TimeLineState.Posts) {
        val posts = (timeLineState as TimeLineState.Posts).posts
        screenState.updatePosts(posts)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ScreenTitle(resource = R.string.timeline)
        PostList(screenState.posts)
    }

    timeLineViewModel.timeLineFor(userID)
}

@Composable
fun PostList(posts: List<Post>) {
    if (posts.isEmpty()) {
        Text(text = stringResource(id = R.string.emptyTimelineMessage))
    } else {
        LazyColumn {
            items(posts) { post ->
                PostItem(post)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}



@Composable
fun PostItem(
    post: Post,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .border(
                1.dp, color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            )
    )
    Text(text = post.postText,
        modifier = Modifier.padding(16.dp))
}

@Preview
@Composable
private fun PostListPreview() {
    val posts = (0..100).map { index ->
        Post("$index", "user$index", "This is post number $index", index.toLong())
    }

    PostList(posts = posts)
}
