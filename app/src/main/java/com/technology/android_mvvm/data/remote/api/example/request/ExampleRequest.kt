package com.technology.android_mvvm.data.remote.api.example.request

import com.google.gson.annotations.SerializedName

data class ExampleRequest(
    @SerializedName("title")
    var title: String = "",
    @SerializedName("description")
    var description: String = ""
)
