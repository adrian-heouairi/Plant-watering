package compmobiles.projet

import java.util.*
import android.content.Context
import androidx.room.*
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun localDateToString(value: LocalDate?): String? {
        return value.toString()
    }

    @TypeConverter
    fun stringToLocalDate(value: String?): LocalDate? {
        return if (value.equals("null")) LocalDate.now() else LocalDate.parse(value)
    }
}

@Database(entities = [Plante::class], version = 6)
@TypeConverters(Converters::class)
abstract class PlantesDatabase : RoomDatabase() {
    abstract fun plantesDao(): PlantesDao

    companion object {
        @Volatile
        private var instance: PlantesDatabase? = null

        fun getDatabase(context : Context): PlantesDatabase {
            if(instance != null)
                return instance!!
            val db = Room.databaseBuilder(context.applicationContext,
                PlantesDatabase::class.java , "Plantes").build()
            instance = db
            return instance!!

        }
    }
}