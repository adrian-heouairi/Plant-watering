package compmobiles.projet

import androidx.lifecycle.ViewModel

class ListeArrosageViewModel : ViewModel() {
    var plantesAArroser: List<Plante>? = null
    var plantesCochees: MutableList<Plante>? = null
}