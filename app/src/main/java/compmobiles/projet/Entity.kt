package compmobiles.projet

import androidx.room.*
import java.time.LocalDate
import java.util.*

@Entity
data class Plante(
    @PrimaryKey(autoGenerate = true)
    var idPlante: Int = 0,
    var nom_com: String,
    var nom_lat: String = "",
    var dateDernier: LocalDate = LocalDate.parse("2000-01-01"),
    var freq1: Int = 7,
    var freq2: Int = 0,
    var freq3: Int = 0,
    var date1: LocalDate? = null,
    var date2: LocalDate? = null,
    var date3: LocalDate? = null,
    var freq_nut1 : Int = 0,
    var freq_nut2 : Int = 0,
    var freq_nut3 : Int = 0,
    var photo: String = ""
) {
    fun doitEtreArrosee(): Boolean {
        if (freq2 != 0 && freq3 != 0) {

        } else if (freq2 != 0) {

        } else {
            val datePlusFreq = dateDernier.plusDays(freq1.toLong())
            val aujourdhui = LocalDate.now()
            return datePlusFreq.isBefore(aujourdhui) or datePlusFreq.isEqual(aujourdhui)
        }
        return true
    }
}

fun filtrerPlantesAArroser(plantes: List<Plante>): List<Plante> {
    val plantesAArroser = mutableListOf<Plante>()
    for (i in plantes) {
        if (i.doitEtreArrosee()) {
            plantesAArroser.add(i)
        }
    }
    return plantesAArroser
}