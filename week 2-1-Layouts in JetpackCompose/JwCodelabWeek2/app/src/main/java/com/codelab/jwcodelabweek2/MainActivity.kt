package com.codelab.jwcodelabweek2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.codelab.jwcodelabweek2.ui.theme.JwCodelabWeek2Theme
import kotlinx.coroutines.launch

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            JwCodelabWeek2Theme {
//                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
////                    Greeting("Android")
//                    PhotographerCardPreview()
//                }
//            }
//        }
//    }
//}

//@Composable
//fun PhotographerCard(modifier: Modifier = Modifier) {
//    Row(modifier.padding(8.dp)
//        .clip(RoundedCornerShape(4.dp))
//        .background(MaterialTheme.colors.surface)
//        .clickable(onClick = {/* ddd */})
//        .padding(16.dp)) {
//        Surface(
//            modifier = Modifier.size(50.dp),
//            shape = CircleShape,
//            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
//        ) {
//            // Image goes here
//        }
//        Column(
//            modifier = Modifier
//                .padding(start = 8.dp)
//                .align(Alignment.CenterVertically)
//        ) {
//            Text("Alfred Sisley", fontWeight = FontWeight.Bold)
//            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
//                Text("3 minutes ago", style = MaterialTheme.typography.body2)
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//fun PhotographerCardPreview() {
//    JwCodelabWeek2Theme {
//        PhotographerCard()
//    }
//}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JwCodelabWeek2Theme {
                LayoutsCodelab()
//                ScrollingList()
            }
        }
    }
}


@Composable
fun LayoutsCodelab() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "codeLab ")},
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Favorite, contentDescription = null)
                }
            })
        }
    ) { innerPadding ->
        BodyContent(
            Modifier
                .padding(innerPadding)
                .padding(8.dp))
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier){
//    Column(modifier = modifier.padding(8.dp)) {
//        Text(text = "Hi there!" )
//        Text(text = "Thanks for going through the Layouts codelab" )
//    }
//    MyOwnColumn(modifier.padding(8.dp)) {
//        Text("MyOwnColumn")
//        Text("places items")
//        Text("vertically.")
//        Text("We've done it by hand!")
//    }
    Row(modifier = modifier
        .background(color = Color.LightGray)
        .padding(16.dp)
        .size(200.dp)
        .horizontalScroll(rememberScrollState()),
        content = {
            StaggeredGrid {
                for (topic in topics) {
                    Chip(modifier = Modifier.padding(8.dp), text = topic)
                }
            }
        })
}


@Preview
@Composable
fun DefaultPreview() {
    JwCodelabWeek2Theme {
//        LayoutsCodelab()
//        ConstraintLayoutContent()
//        LargeConstraintLayout()
        Surface {
            TwoTexts(text1 = "Hi", text2 = "there")
        }
    }
}



@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberImagePainter(
                data = "https://developer.android.com/images/brand/Android_Robot.png"
            ),
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text("Item #$index", style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun ScrollingList() {
    val listSize = 100
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column {
        Row {
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(0)
                }
            }) {
                Text("Scroll to the top")
            }

            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(listSize - 1)
                }
            }) {
                Text("Scroll to the end")
            }
        }
        LazyColumn(state = scrollState) {
            items(listSize) {
                ImageListItem(it)
            }
        }
    }
}


@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        var yPosition = 0
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { placeable ->
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
        }
    }
}

val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)

// Layout modifiers under the hood

@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        val rowWidths = IntArray(rows) { 0 }
        val rowHeights = IntArray(rows) { 0 }

        val placeables = measurables.mapIndexed { index, measurable ->
            val placeable = measurable.measure(constraints)
            val row = index % rows

            rowWidths[row] += placeable.width
            rowHeights[row] = Math.max(rowHeights[row], placeable.height)
            placeable
        }

        val width = rowWidths.maxOrNull()
            ?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth)) ?: constraints.minWidth

        val height = rowHeights.sumOf { it }
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i - 1] + rowHeights[i - 1]
        }

        layout(width, height) {
            val rowX = IntArray(rows) { 0 }
            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.placeRelative(x = rowX[row], y = rowY[row])
                rowX[row] += placeable.width
            }
        }
    }
}

@Composable
fun Chip(modifier: Modifier = Modifier, text: String) {
    Card(
        modifier = modifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(Modifier.width(4.dp))
            Text(text = text)
        }
    }
}


// Constraint Layout

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        val (button1,button2, text) = createRefs()
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button 1")
        }
        Text("Text", Modifier.constrainAs(text) {
            top.linkTo(button1.bottom, margin = 16.dp)
            centerAround(button1.end)
        })
        val barrier = createEndBarrier(button1,text)
        Button(onClick = { /*TODO*/ },
        modifier = Modifier.constrainAs(button2) {
            top.linkTo(parent.top,margin = 16.dp)
            start.linkTo(barrier)
        }){
            Text(text = "button2")
        }
    }
}

@Composable
fun LargeConstraintLayout() {
    ConstraintLayout {
        val text = createRef()

        val guideline = createGuidelineFromStart(fraction = 0.5f)
        Text(
            "This is a very very very very very very very long text",
            Modifier.constrainAs(text) {
//                linkTo(start = guideline, end = parent.end)
                linkTo(guideline, parent.end)
                width = Dimension.preferredWrapContent
            }
        )
    }
}


@Composable
fun TwoTexts(modifier: Modifier = Modifier, text1: String, text2: String) {
    Row(modifier = modifier.height(IntrinsicSize.Min)) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.Start),
            text = text1
        )

        Divider(color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .wrapContentWidth(Alignment.End),
            text = text2
        )
    }
}
