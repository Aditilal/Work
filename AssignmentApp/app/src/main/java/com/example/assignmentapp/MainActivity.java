package com.example.assignmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "channel_id01";
    public static final int NOTIFICATION_ID = 1;
    EditText et1;
    Dialog dialog;

Button ToastClick, GenNotification, ChangeText, ModalBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(MainActivity.NOTIFICATION_ID);
        et1=findViewById(R.id.et1);
        ToastClick=findViewById(R.id.ToastClick);
        GenNotification=findViewById(R.id.GenNotification);
        ChangeText=findViewById(R.id.ChangeText);
        ModalBtn=findViewById(R.id.ModalBtn);
        dialog=new Dialog(this);
        if(et1.getText().toString()=="")
        {
            et1.setText("Please Enter a data");
        }
        else
        {
             goAhead();
        }
        public void goAhead(){
            //This generates a toast
        ToastClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toshow=et1.getText().toString();
                Toast.makeText(MainActivity.this, toshow,
                        Toast.LENGTH_LONG).show();
            }
        });
        GenNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//Generates a notification with dismiss action button
showNotifiction();
            }
        });
        ChangeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Changes the text to completed
                et1.setText("Completed");
            }
        });
        ModalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//Modal PopUp
                openDialog();
            }
        });
    }
    }
    public void openDialog() {

        dialog.setContentView(R.layout.layout_dailog);

        ImageView imageViewClose=dialog.findViewById(R.id.imageViewClose);
        EditText dialogBox=dialog.findViewById(R.id.dialogBox);


        String text1=et1.getText().toString();
        dialogBox.setText(text1);
        dialog.show();

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void showNotifiction() {
        createNotificationChannel();

        Intent likeIntent = new Intent(this,MainActivity.class);
        likeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent likePIntent = PendingIntent.getActivity(this,0,likeIntent, PendingIntent.FLAG_ONE_SHOT );
        NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);


        String notif=et1.getText().toString();
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_notif);
        builder.setContentTitle("Hey!");
        builder.setContentText(notif);
        builder.addAction(R.drawable.ic_dismiss,"DISMISS",likePIntent);
        //Used a Default case for the notification
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setOngoing(true);

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());




    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "My Notification";
            String description="My notification description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,name,importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

}
