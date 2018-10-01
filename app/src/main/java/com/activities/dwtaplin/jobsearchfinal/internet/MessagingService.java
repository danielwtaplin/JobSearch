package com.activities.dwtaplin.jobsearchfinal.internet;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import com.activities.dwtaplin.jobsearchfinal.R;
import com.activities.dwtaplin.jobsearchfinal.database.LocalDatabaseManager;
import com.activities.dwtaplin.jobsearchfinal.database.ServerManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {
    private static final String CHANNEL_ID = "notify_channel";
    private static String TOKEN;
    @Override
    public void onNewToken(String token) {
        TOKEN = token;
        LocalDatabaseManager localDatabaseManager = new LocalDatabaseManager(getApplicationContext());
        localDatabaseManager.updateToken(token);
        if(localDatabaseManager.userExists())
            new ServerManager(getApplicationContext()).updateToken(localDatabaseManager.getUserServerId(), token);

    }
    public static String getToken(){
        return TOKEN;
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
            if(remoteMessage.getNotification() != null){
                createNotificationChannel();
                RemoteMessage.Notification notification = remoteMessage.getNotification();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(() -> {
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                            .setSmallIcon(R.drawable.cards_icon)
                            .setContentTitle(notification.getTitle())
                            .setContentText(notification.getBody())
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                    notificationManager.notify(1, mBuilder.build());
                }, 1000 );
            }

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
