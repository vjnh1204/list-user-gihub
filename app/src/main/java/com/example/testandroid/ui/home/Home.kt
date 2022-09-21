package com.example.testandroid.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.example.testandroid.model.User


@Composable
fun HomeScreen(homeViewModel: HomeViewModel= hiltViewModel(), clickUserCard:(String) -> Unit ={}) {
    val state by homeViewModel.state.collectAsState()

    LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)){
        if (state.isEmpty()){
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.Center)
                        .padding(50.dp)
                        )
            }
        }
        itemsIndexed(state){ index, item ->  
            UserCard(user = item,
                Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                index, clickUserCard = clickUserCard)
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserCard(user: User,modifier: Modifier,index:Int,clickUserCard: (String) -> Unit ={}){
    Card(modifier = modifier
        .background(Color.White)
        .padding(bottom = 16.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        onClick = {clickUserCard.invoke(user.login!!)}
        ) {
        Row (modifier= Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically){
            Image(painter = rememberImagePainter(
                data = user.avatarUrl,
                builder = {
                    scale(Scale.FILL)
                    transformations(CircleCropTransformation())
                }
            ), contentDescription = user.avatarUrl,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.2f))
            Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight()
                .weight(0.5f, true)
                , verticalArrangement = Arrangement.Center) {
                Text(text = user.login.toString(), style = typography.h6)
                if (user.siteAdmin == true){
                    Box(modifier = Modifier
                        .height(60.dp)
                        .padding(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(Color.LightGray), contentAlignment = Alignment.Center){
                        Text(text = "STAFF", fontSize = 12.sp, fontWeight = FontWeight.Medium, modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
            Text(text = (index+1).toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        }
    }
}