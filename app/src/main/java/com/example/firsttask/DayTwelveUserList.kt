package com.example.firsttask

data class DayTwelveUserList(val data:List<DayTweleveUser>)


data class  DayTweleveUser(val id:String?,val name:String?,val email :String?,val gender:String?, val status:String?)

data class DayTweleveUserResponse(val code:Int?, val meta:String?,val data:DayTweleveUser?)