package com.example.clientapp.Firebase;

import static android.content.ContentValues.TAG;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.clientapp.Football.APIClient;
import com.example.clientapp.MainActivity;
import com.example.clientapp.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private String username;

    public MyFirebaseMessagingService(String username) {
        this.username = username;
    }

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        sendRegistrationToServer(s, username);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle(),
                remoteMessage.getData().get("icon"));
    }

    public void sendRegistrationToServer(String token, String username){
        Call<Void> call = APIClient.createService(FirebaseAPI.class).saveToken(token, username);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Log.d("token", "save");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("token", Log.getStackTraceString(t));
            }
        });
    }

    public void getCurrentToken(String username){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                String token = task.getResult();
                Log.d("token1", token);
                sendRegistrationToServer(token, username);
            }
        });
    }

    private void sendNotification(String messageBody, String title, String icon) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        int notificationIcon;

        switch (icon){
            case "Football":
                notificationIcon = R.drawable.ic_baseline_sports_soccer_24;
                break;
            case "Basketball":
                notificationIcon = R.drawable.ic_baseline_sports_basketball_24;
                break;
            case "Volleyball":
                notificationIcon = R.drawable.ic_baseline_sports_volleyball_24;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + icon);
        }
        String channelId = "channel_id";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(notificationIcon)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel channel = new NotificationChannel(channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);

        notificationManager.notify(0, notificationBuilder.build());
    }


}
