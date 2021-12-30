package compmobiles.projet

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
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

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Ajout d'une plante", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val listePlantes = ArrayList<Plante>()
        for (i in 0..49) { listePlantes.add(Plante("Plante " + i)) }
        val listePlantesAdapter = ListePlantesAdapter(this, listePlantes)
        binding.recyclerplantes.hasFixedSize()
        binding.recyclerplantes.layoutManager = LinearLayoutManager(this)
        binding.recyclerplantes.adapter = listePlantesAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_liste_plantes, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_alarme -> { true }
            R.id.action_arrosage -> { true }
            else -> super.onOptionsItemSelected(item)
        }
    }
}