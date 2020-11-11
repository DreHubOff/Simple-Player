package com.example.simpleplayer.application.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import androidx.annotation.NonNull;

import com.example.simpleplayer.application.ui.main.MainActivity;

public abstract class NotificationAbstract extends ContextWrapper {
    @NonNull
    private Context context;

    public NotificationAbstract(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    protected NotificationManager nm = (NotificationManager) (context.getSystemService(Context.NOTIFICATION_SERVICE));

    public abstract @NonNull Notification getNotification(@NonNull String downloadingFileName, int id, int requestCode);

    protected final @NonNull Notification prepare(
            @NonNull Notification.Builder builder,
            @NonNull String downloadingFileName,
            @NonNull int requestCode) {

        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(
                this,
                requestCode,
                notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        builder.setContentIntent(contentIntent);
        builder.setContentTitle(downloadingFileName);
        builder.setAutoCancel(false);

        return builder.build();
    }

}
