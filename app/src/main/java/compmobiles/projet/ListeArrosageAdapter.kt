package compmobiles.projet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class ListeArrosageAdapter(val context: Context, var plantes: List<Plante>, var plantesCochees: MutableList<Plante>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class PlanteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    val listener = { view: View ->
        val ctv = view as CheckedTextView
        ctv.toggle()

        if (ctv.isChecked) {
            plantesCochees.add(view.tag as Plante)
        } else {
            plantesCochees.remove(view.tag as Plante)
        }

        /*val intent = Intent(context, ListePlantesActivity::class.java)
        val bundle = Bundle()
        bundle.putInt("planteId", (view.tag as Plante).idPlante)
        intent.putExtras(bundle)
        startActivity(context, intent, null)*/

        Unit
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val textView = LayoutInflater.from(parent.getContext())
            .inflate(android.R.layout.simple_list_item_checked, parent, false)
        textView.setOnClickListener(listener)

        return PlanteViewHolder(textView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val textView = holder.itemView as CheckedTextView
        textView.text = plantes[position].nom_com

        textView.isChecked = plantesCochees.contains(plantes[position])

        textView.tag = plantes[position]
    }

    override fun getItemCount(): Int {
        return plantes.size
    }
}