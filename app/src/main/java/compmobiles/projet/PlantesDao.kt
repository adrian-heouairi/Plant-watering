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

    @Update
    fun updatePlantes(plantes: List<Plante>)

    @Delete
    fun deletePlante(plante: Plante) : Int

    @Query("SELECT * FROM Plante WHERE idPlante = :id")
    fun loadPlante(id : Int): Plante

    @Query("SELECT * FROM Plante")
    fun loadAllPlantes(): List<Plante>

    @Query("SELECT * FROM Plante")
    fun loadAllPlantesLiveData(): LiveData<List<Plante>>
}