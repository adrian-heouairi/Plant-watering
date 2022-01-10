package compmobiles.projet

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import compmobiles.projet.databinding.ActivityAlarmeBinding
import compmobiles.projet.databinding.ActivityListePlantesBinding
import java.lang.Exception
import java.lang.NumberFormatException

class AlarmeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAlarmeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.butAlarme.setOnClickListener {
            var heure: Int = -1
            var minute: Int = -1

            try {
                heure = binding.heure.text.toString().toInt()
                minute = binding.minute.text.toString().toInt()
                if (heure < 0 || heure > 23) throw NumberFormatException()
                if (minute < 0 || minute > 59) throw NumberFormatException()
            } catch (e: NumberFormatException) {
                Toast.makeText(this,
                    "Erreur : l'heure ou la minute est invalide", Toast.LENGTH_LONG
                ).show()

                return@setOnClickListener
            }

            val intent = Intent(this, NotificationArrosageService::class.java)
            //intent.putExtra(NotificationArrosageService.MEDIA_STATE, NotificationArrosageService.START)

            val pendingIntent = PendingIntent.getService(this, 1, intent,
                    PendingIntent.FLAG_IMMUTABLE)

            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, heure)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )

            Toast.makeText(this,
                "Vous serez notifié à " + heure + "h" + minute +
                        " chaque jour si vous avez des plantes à arroser",
                Toast.LENGTH_LONG
            ).show()
        }

        binding.butNotification.setOnClickListener {
            val intent = Intent(this, NotificationArrosageService::class.java)
            startService(intent)
        }
    }
}