package red.lemon.flashlight;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RemoteViews;

public class MainActivity extends Activity {

    private boolean isLighOn = false;

    private Camera camera;

    private ImageButton button;

    @Override
    protected void onStop() {
        super.onStop();

        if (camera != null) {
//            camera.release();
        }
    }

    /*private void createNotification(){
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager = (NotificationManager) getSystemService(ns);

        Notification notification = new Notification(R.drawable.ic_launcher, null, System.currentTimeMillis());
        RemoteViews notificationView = new RemoteViews(getPackageName(), R.layout.notification_switch);

        //the intent that is started when the notification is clicked (works)
        Intent notificationIntent = new Intent(this, SettingsActivity.class);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        notification.contentView = notificationView;
        notification.contentIntent = pendingNotificationIntent;
        notification.flags |= Notification.FLAG_NO_CLEAR;

        //this is the intent that is supposed to be called when the button is clicked
        Intent switchIntent = new Intent(this, switchButtonListener.class);
        PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(this, 0, switchIntent, 0);

        notificationView.setOnClickPendingIntent(R.id.buttonswitch, pendingSwitchIntent);

        notificationManager.notify(1, notification);
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (ImageButton) findViewById(R.id.buttonFlashlight);

        WebView adsDisplay = (WebView) findViewById(R.id.adsDisplay);
        adsDisplay.getSettings().setJavaScriptEnabled(true);
        adsDisplay.loadUrl("http://sandrosantos.art.br/ads.html");

        Context context = this;
        PackageManager pm = context.getPackageManager();

        // if device support camera?
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Log.e("err", "Device has no camera!");
            return;
        }

        camera = Camera.open();
        final Parameters p = camera.getParameters();

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                /*Notification noti = new Notification.Builder(arg0.getContext())
                        .setContentTitle("The light turn on")
                        .setContentText("assunto")
                        .setSmallIcon(R.drawable.ic_launcher)
                        .build();

                String ns = Context.NOTIFICATION_SERVICE;

                NotificationManager notificationManager = (NotificationManager) getSystemService(ns);
                notificationManager.notify(1, noti);*/

                if (isLighOn) {

                    Log.i("info", "torch is turn off!");

                    p.setFlashMode(Parameters.FLASH_MODE_OFF);
                    camera.setParameters(p);
                    camera.stopPreview();

                    button.setImageResource(R.drawable.off_button);

                    isLighOn = false;

                } else {

                    Log.i("info", "torch is turn on!");

                    p.setFlashMode(Parameters.FLASH_MODE_TORCH);

                    camera.setParameters(p);
                    camera.startPreview();

                    button.setImageResource(R.drawable.on_button);

                    isLighOn = true;

                }

            }
        });

    }
}
