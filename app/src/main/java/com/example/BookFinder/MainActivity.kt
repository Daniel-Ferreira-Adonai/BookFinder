package com.example.BookFinder

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.ui.text.googlefonts.Font
import com.example.BookFinder.ui.theme.BookFinderTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BookFinderTheme {
                app()
            }
        }
    }

}

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
private fun app() {
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

                Icon(Icons.Default.Search, contentDescription = "",modifier = Modifier.size(28.dp))
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
                                tint = if (item == selectedItem) item.color else Color(0xFFB0BEC5),
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
                    MainScreen()
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

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        Text(
            "Main Screen",
            modifier = Modifier.align(Alignment.Center),
            style = TextStyle.Default.copy(fontSize = 32.sp)
        )
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

@Preview
@Composable
private fun appPreview() {
    app()
}