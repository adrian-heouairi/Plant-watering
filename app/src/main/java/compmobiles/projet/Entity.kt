package compmobiles.projet

import androidx.room.*
import java.util.*

@Entity
data class Plantes(
    @PrimaryKey
    var nom_com: String,
    var nom_lat: String?,
    var freq1: Int,
    var freq2: Int?,
    var freq3: Int?,
    var date1: Long?,
    var date2: Long?,
    var date3: Long?,
    var freq_vit1 : Int,
    var freq_vit2 : Int,
    var freq_vit3 : Int,
    var date: Long,
    var photo: String

)
