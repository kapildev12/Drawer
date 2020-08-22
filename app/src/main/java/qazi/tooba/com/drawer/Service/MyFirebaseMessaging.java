package qazi.tooba.com.drawer.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import qazi.tooba.com.drawer.R;

/**
 * Created by sickbay on 3/28/2018.
 */

public class MyFirebaseMessaging extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {

        if (remoteMessage.getNotification() == null
                || remoteMessage.getNotification().getTitle() == null
                || remoteMessage.getNotification().getBody() == null)
            return;

        // this outside the main thread so we use handler to use

        // this outside the main thread so we use handler to use

        switch (remoteMessage.getNotification().getTitle()) {
            case "Cancel": {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyFirebaseMessaging.this,
                                remoteMessage.getNotification().getBody(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent("No_Driver");
                getApplicationContext().sendBroadcast(intent);
                break;
            }
            case "Accept": {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyFirebaseMessaging.this,
                                remoteMessage.getNotification().getBody().split(";")[0],
                                Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent("Driver");
                intent.putExtra("name", remoteMessage.getNotification().getBody().split(";")[1]);
                intent.putExtra("number", remoteMessage.getNotification().getBody().split(";")[2]);
                intent.putExtra("lat", remoteMessage.getNotification().getBody().split(";")[3]);
                intent.putExtra("lng", remoteMessage.getNotification().getBody().split(";")[4]);
                intent.putExtra("driver", remoteMessage.getNotification().getBody().split(";")[5]);
                getApplicationContext().sendBroadcast(intent);
                break;
            }

            case "Location": {
                Intent intent = new Intent("Location");
                intent.putExtra("lat", remoteMessage.getNotification().getBody().split(";")[0]);
                intent.putExtra("lng", remoteMessage.getNotification().getBody().split(";")[1]);
                getApplicationContext().sendBroadcast(intent);
                break;
            }
            case "Arrived":
                showArrivedNotification(remoteMessage.getNotification().getBody());
                break;
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    private void showArrivedNotification(String body) {

        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(), 0, new Intent(), PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(), "default");
        builder.setAutoCancel(true)
                .setDefaults(android.app.Notification.DEFAULT_LIGHTS | android.app.Notification.DEFAULT_SOUND)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Arrived")
                .setContentText(body)
                .setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }
}
