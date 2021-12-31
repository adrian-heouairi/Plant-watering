package compmobiles.projet

import androidx.room.*
import java.util.*

@Dao
interface PlantesDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertPlante(vararg plante: Plantes)

    @Update
    fun updatePlantes(vararg plante: Plantes)

    @Delete
    fun deletePlante(vararg plante: Plantes)

    @Query("SELECT * FROM Plantes WHERE nom_com =:nom")
    fun loadPlante(nom: String): Array<Plantes>


}