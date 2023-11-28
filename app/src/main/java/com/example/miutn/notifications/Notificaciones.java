package com.example.miutn.notifications;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.miutn.R;
import com.example.miutn.activitys.MainActivity;
import com.example.miutn.utils.General;

/**
 * Esta clase estara encargada de la muestra de las notificaciones
 * Admite parametros de personalizacion
 */

public class Notificaciones {
    String DescripcionCanal,TextoMostrarSinExpandir,TextoMostrarExpando,CanalNotif;
    int Importancia;
    int Icono;

    public String getDescripcionCanal() {
        return DescripcionCanal;
    }

    public void setDescripcionCanal(@NonNull String descripcionCanal) {
        DescripcionCanal = descripcionCanal;
    }

    public String getTextoMostrarSinExpandir() {
        return TextoMostrarSinExpandir;
    }

    public void setTextoMostrarSinExpandir(@NonNull String textoMostrarSinExpandir) {
        TextoMostrarSinExpandir = textoMostrarSinExpandir;
    }

    public String getTextoMostrarExpando() {
        return TextoMostrarExpando;
    }

    public void setTextoMostrarExpando( @NonNull String textoMostrarExpando) {
        TextoMostrarExpando = textoMostrarExpando;
    }

    public String getCanalNotif() {
        return CanalNotif;
    }

    public void setCanalNotif(@NonNull String canalNotif) {
        CanalNotif = canalNotif;
    }

    public int getImportancia() {
        return Importancia;
    }

    public void setImportancia( @NonNull int importancia) {
        Importancia = importancia;
    }

    public int getIcono() {
        return Icono;
    }

    public void setIcono(@NonNull int icono) {
        Icono = icono;
    }

    public Notificaciones() {
    DescripcionCanal=General.canalDescripcion;
    Importancia=NotificationManager.IMPORTANCE_DEFAULT;
    CanalNotif=General.canalNotificaciones;
    TextoMostrarSinExpandir=General.textoMostrarSinExpandir;
    TextoMostrarExpando=General.textoMostrarExpando;
    Icono=General.icono;
    }
    private void createNotificationChannel(Context context) {
        //-->   Creacion del canal de notificacion
        NotificationChannel channel = new NotificationChannel(CanalNotif, General.nombreCanal, Importancia);
        channel.setDescription(DescripcionCanal);
        //--->  Registrar este canal en el sistema  <--
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
    public void showNotification(Context context, String title, String content) {
        // Create a Notification channel (required for Android 8.0 and higher)
        createNotificationChannel(context);
        RemoteViews notificacionPersonalizada = new RemoteViews(context.getPackageName(), R.layout.notificaciones_test);
        notificacionPersonalizada.setTextViewText(R.id.NotificacionCosa, TextoMostrarSinExpandir);

        RemoteViews notificacionPersonalizadaExpandida = new RemoteViews(context.getPackageName(), R.layout.notificacion_expandida_text);
        notificacionPersonalizadaExpandida.setTextViewText(R.id.NotificacionExpandidaDescripcion, TextoMostrarExpando);
        Intent appIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_IMMUTABLE);
        notificacionPersonalizadaExpandida.setOnClickPendingIntent(R.id.NotificationButtonTouch, pendingIntent);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, General.canalNotificaciones)
                .setSmallIcon(Icono)
                .setContentTitle(title)
                .setContentText(content)
                .setCustomContentView(notificacionPersonalizada)
                .setCustomBigContentView(notificacionPersonalizadaExpandida)
                .setCustomHeadsUpContentView(notificacionPersonalizadaExpandida)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, builder.build());
    }
}
