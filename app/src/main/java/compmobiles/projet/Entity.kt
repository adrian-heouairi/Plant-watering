package compmobiles.projet

import androidx.room.*
import java.time.LocalDate
import java.util.*

@Entity
data class Plante(
    @PrimaryKey(autoGenerate = true)
    var idPlante: Int = 0,
    var nom_com: String,
    var nom_lat: String? = "",
    var dateDernier: LocalDate? = null,
    var freq1: Int = 7,
    var freq2: Int? = -1,
    var freq3: Int? = -1,
    var date1: LocalDate? = null,
    var date2: LocalDate? = null,
    var date3: LocalDate? = null,
    var freq_nut1 : Int? = 0,
    var freq_nut2 : Int? = 0,
    var freq_nut3 : Int? = 0,
    var photo: String? = ""
)
