package ai.customfit.example;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ai.customfit.android.CFEvent;
import ai.customfit.android.CFGeoType;
import ai.customfit.android.CFTracker;
import ai.customfit.android.CFUser;
import ai.customfit.android.CustomFit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        User builder to easily construct user objects.
        User builder also has methods for all the predefined user fields and custom user properties.
        Just type dot(.) and all the supported methods will be visible to you.
        User builder also supports private predefined user fields and custom user properties.
         */
        final CFUser user = new CFUser.Builder("USER_CUSOTMER_ID")
                .privateEmail("dev@customfit.ai")
                .firstName("JHON")
                .customProperty("age",25)
                .build();

        /*To get the client key you need to have an account of CustomFit.ai Dashboard.
        To know how to get client key please visit https://docs.customfit.ai/customfit-ai/keys
        */
        CustomFit.init(getApplication(), "YOUR_CLIENT_KEY_GOES_HERE", user,10);

        final CustomFit.CFConfigChangeObserver observer = new CustomFit.CFConfigChangeObserver() {
            @Override
            public void onChanged(final String key) {

                //This callback will be invoked from the backend thread.
                //Switch to the UI thread if needed
                Handler uiHandler = new Handler(Looper.getMainLooper());
                uiHandler.post(new Runnable(){
                    @Override
                    public void run() {
                        if(key.equals("STRING_CONFIG_ID")) {
                            ImageView iv = findViewById(R.id.image1);
                            Picasso.get().load(CustomFit.getString(key, "https://landen.imgix.net/29al446fkkuj/assets/749qlcba.png?w=400")).into(iv);
                        } else if(key.equals("COLOR_CONFIG_ID")) {
                            View v = findViewById(R.id.mainLayout);
                            v.setBackgroundColor(CustomFit.getColor(key, R.color.colorAccent));
                        } else if(key.equals("BANNER_CONFIG_ID")) {
                            TextView textView = findViewById(R.id.quickstart_message);
                            textView.setText(CustomFit.getString(key, "Hello")+" "+user.getFirstName());
                        }


                    }
                });
            }
        };

        //Register listeners to each config for which you need to get notified about the change.
        CustomFit.registerConfigChange("COLOR_CONFIG_ID",observer);
        CustomFit.registerConfigChange("STRING_CONFIG_ID",observer);
        CustomFit.registerConfigChange("BANNER_CONFIG_ID",observer);

        /*
        Initial Call to get the Dynamic Configs.
        Each Config type has a seperate get method to get the config value.
        The first argument to the get config method is config id.
        And the second argument is the fallback value.

        First you need to create Dynamic configs in CustomFit.ai Dashboard.
        To know how to create Dynamic configs please visit 
         */
        View v = findViewById(R.id.mainLayout);
        v.setBackgroundColor(CustomFit.getColor("COLOR_CONFIG_ID", R.color.colorAccent));

        ImageView iv = findViewById(R.id.image1);
        Picasso.get().load(CustomFit.getString("STRING_CONFIG_ID", "https://landen.imgix.net/29al446fkkuj/assets/749qlcba.png?w=400")).into(iv);

        TextView textView = findViewById(R.id.quickstart_message);
        textView.setText(CustomFit.getString("BANNER_CONFIG_ID", "Hello")+" "+user.getFirstName());

        Button logTokenButton = findViewById(R.id.generateEventButton);
        logTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Event builder track events that occur on your app.
                CFTracker.trackEvent(new CFEvent.Builder("SIGN_UP")
                        .build());
            }
        });
    }
}
