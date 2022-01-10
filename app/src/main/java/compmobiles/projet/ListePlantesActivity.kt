package compmobiles.projet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import compmobiles.projet.databinding.ActivityListePlantesBinding

class ListePlantesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListePlantesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListePlantesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val dao = PlantesDatabase.getDatabase(application).plantesDao()

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Ajout d'une plante", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            val intent= Intent(this, EditerPlanteActivity::class.java)
            startActivity(intent)
            Thread {
                Log.d("BDD", dao.insertPlante(Plante(0, "Abc")).toString())
            }.start()
        }

        var liveDataPlantes: LiveData<List<Plante>>? = null
        val t = Thread {
            liveDataPlantes = dao.loadAllPlantesLiveData()
        }
        t.start()
        t.join()

        val listePlantesAdapter = ListePlantesAdapter(this, listOf<Plante>())
        //binding.recyclerplantes.hasFixedSize()
        binding.recyclerplantes.layoutManager = LinearLayoutManager(this)
        binding.recyclerplantes.adapter = listePlantesAdapter

        liveDataPlantes!!.observe(this) {
            listePlantesAdapter.plantes = it
            listePlantesAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_liste_plantes, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_alarme -> {
                val intent = Intent(this, AlarmeActivity::class.java)
                startActivity(intent)

                true
            }
            R.id.action_arrosage -> {
                val intent = Intent(this, ListeArrosageActivity::class.java)
                startActivity(intent)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}