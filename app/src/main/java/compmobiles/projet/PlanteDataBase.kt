package compmobiles.projet

import java.util.*
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Plantes::class], version = 6)
abstract class PlanteDatabase : RoomDatabase(){
    abstract fun plantesDao(): PlantesDao

    companion object {
        @Volatile
        private var instance: PlanteDatabase? = null

        fun getDataBase(context : Context): PlanteDatabase{
            if( instance != null )
                return instance!!
            val db = Room.databaseBuilder( context.applicationContext,
                PlanteDatabase::class.java , "Plantes").build()
            instance = db
            return instance!!

        }
    }
}