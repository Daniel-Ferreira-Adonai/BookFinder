package com.example.BookFinder

import android.annotation.SuppressLint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.ui.text.googlefonts.Font
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.BookFinder.ui.theme.BookFinderTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            BookFinderTheme {
                NavHost(navController = navController, startDestination = "mainScreen") {
                    composable("mainScreen") { app(navController) }
                    composable("searchScreen") { SearchScreen(navController) }
                }
            }
        }
    }
}


// these are the navItem, the navItem class is a class to display the bottomBar icons
sealed class navItem(
    val icon: ImageVector,
    val label: String,
    var color: Color,
    var page: Int
) {
    data object main: navItem(icon = Icons.Default.Home, "Home", Color(0xFF00BCD4),0)
    data object map:   navItem(icon = Icons.Default.Map, "Mapa", Color(0xFF4CAF50),1)
    data object favorites: navItem(icon = Icons.Default.Favorite, "Favorito", Color(0xFFFF5252),2)
    data object community: navItem(icon = Icons.Default.PeopleAlt, "Comunidade", Color(0xFFFF9800),3)

}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun app(navController: NavHostController) {





    val navItems = listOf(
        navItem.main,
        navItem.map,
        navItem.favorites,
        navItem.community

    )

    var selectedItem by remember {
        mutableStateOf(navItems.first())
    }
    val pagerState = rememberPagerState {
        navItems.size
    }
    val coroutineScope = rememberCoroutineScope()


    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF1E88E5),
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White,

            ),
            navigationIcon = {
                Row(){
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        Icons.Default.AccountCircle,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

            },
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "BookFinder", fontWeight = FontWeight.Bold)

                }
            },
            actions = {

                IconButton(onClick = {
                    navController.navigate("searchScreen")
                }) {
                    Icon(Icons.Default.Search, contentDescription = "", modifier = Modifier.size(28.dp))
                }
                Spacer(modifier = Modifier.width(4.dp))


            }
        )
    },

        bottomBar = {

            BottomAppBar(
                containerColor = Color(0xFF1E88E5),
                contentColor = Color.White,
                modifier = Modifier.height(70.dp)

            ) {
                for (item in navItems) {
                    NavigationBarItem(
                        selected = item == selectedItem,
                        onClick = { selectedItem = item
                            coroutineScope.launch { pagerState.scrollToPage(item.page) }},
                        icon = {
                            Icon(
                                item.icon,
                                contentDescription = "",
                                tint = if (item == selectedItem) item.color else Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        },
                        label = {
                            Text(text = item.label, color = Color.White)
                        })
                }
            }

        }


    ) { innerPadding ->
        HorizontalPager(state = pagerState, Modifier.padding(innerPadding)) {
                page ->
            val item = navItems[page]

            when(item){
                navItem.main -> {
                    selectedItem = navItems[pagerState.currentPage]
                    MainScreen(navController)
                }
                navItem.map -> {
                    selectedItem = navItems[pagerState.currentPage]
                    MapScreen()
                }
                navItem.favorites ->{
                    selectedItem = navItems[pagerState.currentPage]
                    FavoritesScreen()
                }
                navItem.community -> {
                    selectedItem = navItems[pagerState.currentPage]
                    CommunityScreen()
                }
            }


        }
    }
}

// do not include this
@Composable
fun MainScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .padding(30.dp)
            .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        SearchBar(navController, false)



        Text("Last books")
        BookCard()
        BookCard()


    }





    }



@Composable
fun MapScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        Text(
            "Map Screen",
            modifier = Modifier.align(Alignment.Center),
            style = TextStyle.Default.copy(fontSize = 32.sp)
        )
    }
}
@Composable
fun FavoritesScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        Text(
            "Favorites Screen",
            modifier = Modifier.align(Alignment.Center),
            style = TextStyle.Default.copy(fontSize = 32.sp)
        )
    }
}
@Composable
fun CommunityScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        Text(
            "Community Screen",
            modifier = Modifier.align(Alignment.Center),
            style = TextStyle.Default.copy(fontSize = 32.sp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {

                    SearchBar(navController, true)
                 },
                navigationIcon = {
                    Row(modifier = Modifier.width(30.dp)){
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                                ,modifier.size(22.dp)
                            )
                        }
                    }

                    
                },
                
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1E88E5),
                    titleContentColor = Color.White
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
        ) {

        }
    }
}
@Composable

fun SearchBar(navController: NavHostController, state: Boolean, modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }

    val leadingIcon: @Composable (() -> Unit)? = if (!state) {
        {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    } else {
        null
    }

    TextField(
        value = text,
        onValueChange = { text = it },
        leadingIcon = leadingIcon,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedContainerColor = Color(0xFF1E88E5),
            focusedContainerColor = Color(0xFF1E88E5),
            unfocusedLeadingIconColor = Color.White,
            focusedLeadingIconColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(
                stringResource(R.string.place_Holder_Search_Bar),
                style = TextStyle(fontSize = 14.sp)
            )
        },
        textStyle = TextStyle(fontSize = 14.sp),
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable { navController.navigate("searchScreen") },
        enabled = state,
        singleLine = true
    )
}

@Composable
fun cardMeme(@DrawableRes drawable: Int){ Image(
    painter = painterResource(drawable),
    contentDescription = null,
    contentScale = ContentScale.Crop,
    modifier = Modifier
        .size(88.dp)
        .clip(CircleShape)
)
}


// this is for test
@Composable

fun cardWithName(name: String, modifier: Modifier = Modifier) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Hello ")
                Text(text = name)
            }
            ElevatedButton(
                onClick = { /* TODO */ }
            ) {
                Text("Show more")
            }
        }
    }
}
// this is for test
@Composable
fun BookCard() {
    var likes by remember {
        mutableStateOf(120)
    }
    var isLiked by remember {
        mutableStateOf(true)
    }
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.harrypp),
                    contentDescription = "Harry Potter",
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .size(60.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Harry Potter",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Text(
                    "J.K. Rowling",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (isLiked) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorites",
                    tint = Color.Red,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable {isLiked = !isLiked
                            if(isLiked) likes += 1 else likes -= 1}
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = likes.toString(),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp)
                )
            }
        }
    }
}



@Preview
@Composable
private fun BookCardprev() {
    BookCard()
}

@Preview
@Composable
private fun appPreview() {


}