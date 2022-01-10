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
    var freq1: Int = 7,
    var freq2: Int = 0,
    var freq3: Int = 0,
    var date1_debut: Int = 0,
    var date1_fin: Int = 0,
    var date2_debut: Int = 0,
    var date2_fin: Int = 0,
    var date3_debut: Int = 0,
    var date3_fin: Int = 0,
    var photo: String = "",
    var dateDernier: LocalDate = LocalDate.parse("2000-01-01")
) {
    fun doitEtreArrosee(): Boolean {
        var frequence: Int = 0
        val aujourdhuiDoY = LocalDate.now().withYear(2000).dayOfYear // Entre 1 et 366

        if (freq2 != 0 && freq3 != 0) {
            if (date1_debut <= aujourdhuiDoY && aujourdhuiDoY <= date1_fin) frequence = freq1
            else if (date2_debut <= aujourdhuiDoY && aujourdhuiDoY <= date2_fin) frequence = freq2
            else frequence = freq3
        } else if (freq2 != 0) {
            if (date1_debut <= aujourdhuiDoY && aujourdhuiDoY <= date1_fin) frequence = freq1
            else frequence = freq2
        } else {
            frequence = freq1
        }

        val datePlusFreq = dateDernier.plusDays(frequence.toLong())
        val aujourdhui = LocalDate.now()
        return datePlusFreq.isBefore(aujourdhui) or datePlusFreq.isEqual(aujourdhui)
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