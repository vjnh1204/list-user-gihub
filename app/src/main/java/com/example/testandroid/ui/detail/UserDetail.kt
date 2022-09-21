package com.example.testandroid.ui.detail

import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.example.testandroid.model.User
import com.example.testandroid.ui.theme.Shapes
import com.example.testandroid.utils.Routes

@Composable
fun UserDetailScreen(navController: NavController,loginId: String,painter: Painter,userDetailViewModel: UserDetailViewModel = hiltViewModel()){
    fun getUser(){
        userDetailViewModel.getUser(loginId)
    }
    getUser()
    val state by userDetailViewModel.state.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 20.dp, vertical = 10.dp)) {
        if (state.isEmpty() ){
            Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .wrapContentSize(align = Alignment.BottomCenter)
                        .padding(50.dp)
                )
                Text(text = "Loading", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.Black )
            }
        }
        else{
            AvatarBox(user = state[0], painter = painter, navController = navController)
            Box(modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .border(1.dp, Color.LightGray))
            DetailBox(user = state[0])
        }


    }

}

@Composable
fun AvatarBox(user: User,painter: Painter,navController: NavController){
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.6f)
        ){
        Box(modifier = Modifier
            .fillMaxWidth(0.1f)
            .fillMaxHeight(0.1f)
            .clickable {
                navController.navigate(Routes.Home.route) {
                    popUpTo(Routes.Home.route) {
                        inclusive = true
                    }
                }
            }){
            Image(painter = painter, contentDescription = "CloseButton", contentScale = ContentScale.Crop)
        }
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = rememberImagePainter(
                data = user.avatarUrl,
                builder = {
                    scale(Scale.FILL)
                    transformations(CircleCropTransformation())
                }
            ), contentDescription = user.avatarUrl,
                modifier = Modifier
                    .fillMaxHeight(0.6f)
                    .fillMaxWidth(0.6f))
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp), horizontalAlignment = Alignment.CenterHorizontally){
                    if (user.name!= null){
                        Text(text = user.name, color = Color.Black, fontSize = 26.sp, fontWeight = FontWeight.Light)
                    }
                    if (user.bio!= null){
                        Text(text = user.bio, color = Color.Black, fontSize = 18.sp, textAlign = TextAlign.Center)
                    }

            }
        }

    }
}

@Composable
fun DetailBox(user: User){
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f),
        horizontalArrangement = Arrangement.Center){
            Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.1f), contentAlignment = Alignment.Center){
                Icon(Icons.Filled.Person, contentDescription = "Avatar",Modifier.size(40.dp))
            }
            Column(modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxHeight()
                .weight(0.5f, true)
                , verticalArrangement = Arrangement.Center) {
                Text(text = user.login.toString(), style = MaterialTheme.typography.h6)
                if (user.siteAdmin == true){
                    Box(modifier = Modifier
                        .height(30.dp)
                        .padding(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(Color.LightGray)){
                        Text(text = "STAFF",style= MaterialTheme.typography.subtitle2, modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
            horizontalArrangement = Arrangement.Center) {
            Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.1f), contentAlignment = Alignment.Center){
                Icon(Icons.Filled.LocationOn, contentDescription = "Avatar",Modifier.size(40.dp))
            }
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp),
                contentAlignment = Alignment.CenterStart
            ){
                if (user.location !=null){
                    Text(text = user.location,fontSize = 20.sp, modifier = Modifier.align(Alignment.CenterStart))
                }
                else {
                    Text(text = "None",fontSize = 20.sp, modifier = Modifier.align(Alignment.CenterStart))
                }
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            horizontalArrangement = Arrangement.Center) {
            Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.1f), contentAlignment = Alignment.Center){
                Icon(Icons.Filled.Link, contentDescription = "Avatar",Modifier.size(40.dp))
            }
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp),
                contentAlignment = Alignment.CenterStart
            ){
                if (user.blog !=null){
                    Text(text = user.blog, fontSize = 20.sp, modifier = Modifier.align(Alignment.CenterStart))
                }
                else {
                    Text(text = "None",fontSize = 20.sp, modifier = Modifier.align(Alignment.CenterStart))
                }
            }
        }
    }
}