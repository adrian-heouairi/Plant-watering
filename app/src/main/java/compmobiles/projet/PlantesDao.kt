package compmobiles.projet

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface PlantesDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertPlante(plante: Plante) : Long

    @Update
    fun updatePlante(plante: Plante) : Int

    @Delete
    fun deletePlante(plante: Plante) : Int

    @Query("SELECT * FROM Plante WHERE nom_com = :nom")
    fun loadPlante(nom: String): Plante

    @Query("SELECT * FROM Plante")
    fun loadAllPlantes(): LiveData<List<Plante>>
}