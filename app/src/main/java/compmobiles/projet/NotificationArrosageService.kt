package compmobiles.projet

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class NotificationArrosageService : Service() {
    val CHANNEL_ID = "notification arrosage"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val dao = PlantesDatabase.getDatabase(application).plantesDao()
        var plantes: List<Plante>? = null
        val t = Thread {
            plantes = dao.loadAllPlantes()
        }
        t.start()
        t.join()

        val nbPlantesAArroser = filtrerPlantesAArroser(plantes!!).size
        if (nbPlantesAArroser > 0) { // Il y a des plantes à arroser
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //val name: CharSequence = getString(R.string.channel_name)
                //val description = getString(R.string.channel_description)
                val name = "Notification d'arrosage"
                val description = "Pour vous rappeler d'arroser vos plantes"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, name, importance)
                channel.description = description
                val notificationManager = getSystemService( NotificationManager::class.java )
                notificationManager.createNotificationChannel(channel)
            }

            val listeArrosageActivityIntent = Intent(this, ListeArrosageActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                this, 2, listeArrosageActivityIntent,
                PendingIntent.FLAG_IMMUTABLE)

            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Arrosage de vos plantes")
                .setContentText("Vous avez " + nbPlantesAArroser + " plantes à arroser, cliquez ici")
                .setContentIntent(pendingIntent)
                //.setAutoCancel(true)
                .setSmallIcon(android.R.drawable.btn_minus)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                //.setLargeIcon( BitmapFactory.decodeResource( resources, R.drawable.large ) )
                .build()

            //(getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
            //    .notify(3, notification)

            startForeground(3, notification)
        }

        Log.d("Service", "Terminé")

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}