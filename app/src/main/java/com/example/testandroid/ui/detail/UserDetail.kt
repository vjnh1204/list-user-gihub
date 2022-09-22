package com.example.testandroid.ui.detail

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.example.testandroid.R
import com.example.testandroid.model.User

@Composable
fun UserDetailScreen(loginId: String,userDetailViewModel: UserDetailViewModel = hiltViewModel()){
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
                Text(text = "Waiting for loading", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black )
            }
        }
        else{
            AvatarBox(user = state[0])
            Box(modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .border(1.dp, Color.LightGray))
            DetailBox(user = state[0])
        }


    }

}

@Composable
fun AvatarBox(user: User){
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.6f)
        ){
        Box(modifier = Modifier
            .fillMaxWidth(0.1f)
            .fillMaxHeight(0.1f)
            .clickable {
                onBackPressedDispatcher?.onBackPressed()
            }){
            Image(painter = painterResource(id = R.drawable.close), contentDescription = "CloseButton", contentScale = ContentScale.Crop)
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
            Box(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.1f), contentAlignment = Alignment.Center){
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
            Box(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.1f), contentAlignment = Alignment.Center){
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
            Box(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.1f), contentAlignment = Alignment.Center){
                Icon(Icons.Filled.Link, contentDescription = "Avatar",Modifier.size(40.dp))
            }
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp),
                contentAlignment = Alignment.CenterStart
            ){
                if (user.blog !=null){
                    OpenBlogLink(text = user.blog, modifier = Modifier.align(Alignment.CenterStart))
//                    Text(text = user.blog, fontSize = 20.sp, modifier = Modifier.align(Alignment.CenterStart), color = Color.Cyan)
                }
                else {
                    Text(text = "None",fontSize = 20.sp, modifier = Modifier.align(Alignment.CenterStart))
                }
            }
        }
    }
}

@Composable
fun OpenBlogLink(text:String,modifier: Modifier){
    val annotatedString = buildAnnotatedString {
        append(text)
        addStringAnnotation(
            tag="URL",
            annotation = text,
            start = 0,
            end = text.length
        )
        addStyle(
            style = SpanStyle(
                fontSize = 20.sp,
                color = Color.Cyan,
            ),
            start = 0,
            end = text.length
        )
    }
    val urlHandler = LocalUriHandler.current
    ClickableText(modifier = modifier, text = annotatedString, onClick = {
        annotatedString
            .getStringAnnotations("URL",it,it)
            .firstOrNull()?.let { stringAnnotation ->
                urlHandler.openUri(stringAnnotation.item)
            }
    })
}