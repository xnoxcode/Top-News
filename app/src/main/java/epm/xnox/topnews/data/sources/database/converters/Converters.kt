package epm.xnox.topnews.data.sources.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import epm.xnox.topnews.domain.model.Source

class Converters {
    @TypeConverter
    fun fromSource(source: Source?): String? {
        return Gson().toJson(source)
    }

    @TypeConverter
    fun toSource(sourceString: String?): Source? {
        return Gson().fromJson(sourceString, Source::class.java)
    }
}