package com.example.testandroid.utils

import com.example.testandroid.utils.Constants.DETAIL_SCREEN
import com.example.testandroid.utils.Constants.HOME_SCREEN

sealed class Routes(val route:String){
    object Home: Routes(HOME_SCREEN)
    object Detail: Routes(DETAIL_SCREEN)



    fun withArgs(vararg args: String):String{
        return buildString {
            append(route)
            args.forEach {  args->
                append("/$args")
            }
        }
    }
}
