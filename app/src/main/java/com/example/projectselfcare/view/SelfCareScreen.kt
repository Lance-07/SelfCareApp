package com.example.projectselfcare.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.DampingRatioNoBouncy
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectselfcare.R
import com.example.projectselfcare.data.DataSource
import com.example.projectselfcare.model.SelfCare
import com.example.projectselfcare.ui.theme.ProjectSelfCareTheme

@Composable
fun SelfCareScreen() {
    SelfCareList(DataSource().getData())
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun SelfCareList(selfCareList: List<SelfCare>, modifier: Modifier = Modifier) {
    val visibleState = remember { MutableTransitionState(false).apply {
        targetState = true
    }}
    Scaffold(
        topBar = {
            MyTopAppBar()
        },
//        bottomBar = {
//            MyBottomAppBar()
//        },
        modifier = modifier
    ) {
        AnimatedVisibility(
            visibleState = visibleState,
            enter = fadeIn(
                animationSpec = spring(dampingRatio = DampingRatioNoBouncy)
            ),
            exit = fadeOut()
        ) {
            LazyColumn(contentPadding = it) {
                itemsIndexed(selfCareList) {index, selfCare ->
                    SelfCareListItem(
                        selfCare = selfCare,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .animateEnterExit(
                                enter = slideInVertically(
                                    animationSpec = spring(
                                        stiffness = StiffnessVeryLow,
                                        dampingRatio = DampingRatioLowBouncy
                                    ),
                                    initialOffsetY = { it * (index + 1) }
                                )
                            )
                    )
                }
            }
        }
    }

}

@Composable
fun SelfCareListItem(selfCare: SelfCare, modifier: Modifier = Modifier) {
    var cardState by remember { mutableStateOf(false)}
    val colorState by animateColorAsState(
        targetValue = if (cardState) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.tertiaryContainer, label = "change color"
    )
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorState
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .clickable { cardState = !cardState }
        ) {
//            Box(modifier = Modifier.sizeIn(maxHeight = 150.dp)) {
//                Image(
//                    painter = painterResource(selfCare.selfCareImageSource),
//                    contentDescription = null,
//                    alignment = Alignment.Center,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .wrapContentSize()
//                        .align(Alignment.Center)
//                        .clip(MaterialTheme.shapes.medium),
//                    contentScale = ContentScale.Crop
//                )
//            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(selfCare.dayTip),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(selfCare.selfCareTip),
                style = MaterialTheme.typography.bodyLarge
            )
            if (cardState) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.sizeIn(maxHeight = 150.dp)) {
                    Image(
                        painter = painterResource(selfCare.selfCareImageSource),
                        contentDescription = null,
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize()
                            .align(Alignment.Center)
                            .clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.outline)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(selfCare.selfCareExplanation),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.onSecondary
        ),
        title = {
            Box(
                Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.name_app),
                    style = MaterialTheme.typography.displayMedium,
                    fontSize = 26.sp
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = {

            }) {
                Icon(
                    painter = painterResource(R.drawable.self_love),
                    contentDescription = "Menu",
                    tint = Color.Unspecified
                )
            }
        },
        actions = {
              Icon(
                  imageVector = Icons.Default.AccountCircle,
                  contentDescription = "Account",
                  modifier = Modifier.size(40.dp),
                  tint = MaterialTheme.colorScheme.tertiary
              )
            Spacer(modifier = Modifier.width(8.dp))
        },
        modifier = modifier
            .padding(16.dp, 10.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.medium
            )
            .clip(MaterialTheme.shapes.medium),
    )
}

@Composable
fun MyBottomAppBar() {
    BottomAppBar {
        Text(text = "This is a bottom app bar")
    }
}

@Preview
@Composable
fun SelfCareListItemPreview() {
    SelfCareListItem(
        SelfCare(R.string.self_care_tip1, R.string.day_1, R.drawable.photo_1, R.string.self_care_explanation_1)
    )
}

@Preview
@Composable
fun SelfCareListPreview() {
    SelfCareList(selfCareList = DataSource().getData())
}

@Preview
@Composable
fun SelfCareListPreviewDark() {
    ProjectSelfCareTheme(darkTheme = true) {
        SelfCareList(selfCareList = DataSource().getData())
    }
}