package compmobiles.projet

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import compmobiles.projet.databinding.ActivityEditerPlanteBinding
import java.io.File
import java.time.LocalDate

class EditerPlanteActivity : AppCompatActivity(){
    private lateinit var binding: ActivityEditerPlanteBinding

    /*var localUri : Uri? = null
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

    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val bundle = intent.extras
        val idPlante: Int =bundle?.getInt("planteId") ?: 0

        binding = ActivityEditerPlanteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val dao = PlantesDatabase.getDatabase(application).plantesDao()

        //val selectButton = binding.butPhoto

        //selectButton.setOnClickListener {
        //    getContent.launch("image/*")
        //}

        val suppressionButton =binding.supprimer
        val validationButton = binding.valider

        /*var nom_com = binding.nomcom.toString()
        var nom_lat = binding.nomlat.toString()
        var freq1 = binding.freq1.toString().toInt()
        var freq2 = binding.freq2.toString().toInt()
        var freq3 = binding.freq3.toString().toInt()
        var datedebut1 = binding.datedebut1.toString().toInt()
        var datefin1 = binding.datefin1.toString().toInt()
        var datedebut2 = binding.datedebut2.toString().toInt()
        var datefin2 = binding.datefin2.toString().toInt()
        var datedebut3 = binding.datedebut3.toString().toInt()
        var datefin3 = binding.datefin3.toString().toInt()
        var photo= localUri.toString()*/

        var plante = Plante(0,"")
        if (idPlante!=0){
            val t = Thread {
                plante = dao.loadPlante(idPlante)
            }
            t.start()
            t.join()
            /*nom_com= plante.nom_com
            nom_lat = plante.nom_lat
            freq1 = plante.freq1
            freq2 = plante.freq2
            freq3 = plante.freq3
            datedebut1 = plante.date1_debut
            datefin1 = plante.date1_fin
            datedebut2 = plante.date2_debut
            datefin2 = plante.date2_fin
            datedebut3 = plante.date3_debut
            datefin3 = plante.date3_fin
            photo = plante.photo*/
        }

        binding.nomcom.setText(plante.nom_com)
        binding.nomlat.setText(plante.nom_lat)
        binding.freq1.setText(plante.freq1)
        binding.freq2.setText(plante.freq2)
        binding.freq3.setText(plante.freq3)
        binding.datedebut1.setText(plante.date1_debut)
        binding.datefin1.setText(plante.date1_fin)
        binding.datedebut2.setText(plante.date2_debut)
        binding.datefin2.setText(plante.date2_fin)
        binding.datedebut3.setText(plante.date3_debut)
        binding.datefin3.setText(plante.date3_fin)
        //localUri.toString()

        validationButton.setOnClickListener {
            val t = Thread {
                if (idPlante!=0){
                    dao.updatePlante(
                        Plante(
                            idPlante,
                            binding.nomcom.toString(),
                            binding.nomlat.toString(),
                            binding.freq1.toString().toInt(),
                            binding.freq2.toString().toInt(),
                            binding.freq3.toString().toInt(),
                            binding.datedebut1.toString().toInt(),
                            binding.datefin1.toString().toInt(),
                            binding.datedebut2.toString().toInt(),
                            binding.datefin2.toString().toInt(),
                            binding.datedebut3.toString().toInt(),
                            binding.datefin3.toString().toInt()
                        )
                    )
                }
                else {
                    dao.insertPlante(
                        Plante(
                            0,
                            binding.nomcom.toString(),
                            binding.nomlat.toString(),
                            binding.freq1.toString().toInt(),
                            binding.freq2.toString().toInt(),
                            binding.freq3.toString().toInt(),
                            binding.datedebut1.toString().toInt(),
                            binding.datefin1.toString().toInt(),
                            binding.datedebut2.toString().toInt(),
                            binding.datefin2.toString().toInt(),
                            binding.datedebut3.toString().toInt(),
                            binding.datefin3.toString().toInt()
                        )
                    )
                }
            }
            t.start()
            t.join()
            finish()
        }

        suppressionButton.setOnClickListener {
            val t = Thread {
                dao.deletePlante(plante)
            }
            t.start()
            t.join()
            finish()
        }
    }
}