package com.technology.android_mvvm.data.model

import com.google.gson.annotations.SerializedName

data class ExampleModel(
    @SerializedName("id")
    var id: Int = -1,
    @SerializedName("title")
    var title: String = "",
    @SerializedName("description")
    var description: String = ""
)
