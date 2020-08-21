package com.dev_app.mvvmnewsapp.db

import androidx.room.TypeConverter
import com.dev_app.mvvmnewsapp.models.Source


class Converters {
    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }

}