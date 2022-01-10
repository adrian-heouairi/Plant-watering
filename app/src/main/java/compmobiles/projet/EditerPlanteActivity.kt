package compmobiles.projet

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import compmobiles.projet.databinding.ActivityEditerPlanteBinding
import java.io.File
import java.time.LocalDate

class EditerPlanteActivity : AppCompatActivity(){
    private lateinit var binding: ActivityEditerPlanteBinding

    var localUri : Uri? = null
    /* la propriété getContent pour enregistrer une méthode callback
    * appelée au retour de la nouvelle activité.
    * Ici cette méthode callback est implementée par une lambda qui
    * qui obtient l’Uri de l’image comme paramètre. */
    val getContent = registerForActivityResult(
        ActivityResultContracts
            .GetContent())
    { uri: Uri? ->
        if (uri == null) return@registerForActivityResult
/* inputStream avec l’image de la plante */
        val inputStream = getContentResolver().openInputStream(uri)
/* fabriquer le nom de fichier local pour stocker l’image */
        val fileNamePrefix = "plante"
        val preferences = getSharedPreferences("numImage", Context.
        MODE_PRIVATE)
        val numImage = preferences.getInt("numImage", 1)
        val fileName = "$fileNamePrefix$numImage"
/* ouvrir outputStream vers un fichier local */
        val file = File(this.filesDir, fileName)
        val outputStream = file.outputStream()
/* sauvegarder le nouveau compteur d’image */
        preferences.edit().putInt("numImage",numImage+1).commit()
/* copier inputStream qui pointe sur l’image de la galerie
* vers le fichier local */
        inputStream?.copyTo(outputStream)
/* mémoriser Uri de fichier local dans la propriété localUri */
        localUri = file.toUri()
        outputStream.close()
        inputStream?.close()
//éventuellement afficher l’image dans ImageView
        binding.imageView.setImageURI(localUri)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditerPlanteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val dao = PlantesDatabase.getDatabase(application).plantesDao()

        val selectButton = binding.butPhoto

        selectButton.setOnClickListener {
            getContent.launch("image/*")
        }

        val validationButton = binding.valider
        val idPlante = 0
        val nom_com: String = binding.nomcom.toString()
        val nom_lat: String = binding.nomlat.toString()
        val freq1: Int = binding.freq1.toString().toInt()
        val freq2: Int = binding.freq2.toString().toInt()
        val freq3: Int = binding.freq3.toString().toInt()
        val date1: LocalDate? = LocalDate.parse(binding.date1.toString())
        val date2: LocalDate? = LocalDate.parse(binding.date2.toString())
        val date3: LocalDate? = LocalDate.parse(binding.date3.toString())
        val freq_nut1 : Int = binding.freqnut1.toString().toInt()
        val freq_nut2 : Int = binding.freqnut2.toString().toInt()
        val freq_nut3 : Int = binding.freqnut3.toString().toInt()
        val photo: String = localUri.toString()
        validationButton.setOnClickListener {
            if (dao.loadPlante(idPlante)!=null){
                dao.updatePlante(
                    Plante(
                        0, nom_com, nom_lat, LocalDate.now(), freq1, freq2, freq3,
                        date1, date2, date3, freq_nut1, freq_nut2, freq_nut3, photo
                    )
                )
            }
            else {
                dao.insertPlante(
                    Plante(
                        0, nom_com, nom_lat, LocalDate.now(), freq1, freq2, freq3,
                        date1, date2, date3, freq_nut1, freq_nut2, freq_nut3, photo
                    )
                )
            }
        }
    }
}