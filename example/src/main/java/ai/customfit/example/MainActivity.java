/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

        String sDate = "31-Dec-1998 23:37:50";
        SimpleDateFormat formatter6=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Date date= null;
        try {
            date = formatter6.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<String> list1 = new ArrayList<String>();
        List<Number> list2 = new ArrayList<Number>();
        List<Date> listDate = new ArrayList<Date>();
        List<CFGeoType> listGeo = new ArrayList<CFGeoType>();
        list1.add("a");
        list1.add("b");
        list2.add(12);
        list2.add(50);
        listDate.add(date);

        listGeo.add(new CFGeoType(1,2));
        listGeo.add(new CFGeoType(2,3));


        final CFUser user = new CFUser.Builder("mb_1").firstName("Meera").gender("FEMALE").customProperty("age",50)
                .build();

        CustomFit.init(getApplication(), "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NvdW50X2lkIjoiNWQ4MTY5MzAtMDU5Yy0xMWU5LWExYzktNTFjMzdhMDBjNDQ1IiwicHJvamVjdF9pZCI6ImZhNTJkNWQwLTUwNzEtMTFlOS04NzM4LWQ5YWZlZDI2ODZhNCIsImVudmlyb25tZW50X2lkIjoiZmE3MjFkYTAtNTA3MS0xMWU5LTg3MzgtZDlhZmVkMjY4NmE0IiwiZGltZW5zaW9uX2lkIjoiZmNjNzBhMjAtNTA3MS0xMWU5LTg3MzgtZDlhZmVkMjY4NmE0IiwiYXBpX2FjY2Vzc19sZXZlbCI6IkNMSUVOVCIsImtleV9pZCI6ImZkNWQ1NmIwLTUwNzEtMTFlOS04NzM4LWQ5YWZlZDI2ODZhNCIsInByb2R1Y3QiOiJDVVNUT01GSVQiLCJpc3MiOiJhRmZSb0FPSTFsRTh0Vk94OUUwRnViZ2lhS3ZXc0NOViIsImlhdCI6MTU1MzY3ODYxMn0.PwNSjXj-FG_SisVSK_rfX7e0pRHwBL_mJdyA05KFEEQ", user, 10);

        final CustomFit.CFConfigChangeObserver observer = new CustomFit.CFConfigChangeObserver() {
            @Override
            public void onChanged(final String key) {
                Handler uiHandler = new Handler(Looper.getMainLooper());
                uiHandler.post(new Runnable(){
                    @Override
                    public void run() {



                        if(key.equals("daily-ninja")) {
                            ImageView iv = findViewById(R.id.image1);
                            Picasso.get().load(CustomFit.getString(key, "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")).into(iv);

                        } else if(key.equals("configcolor")) {

                            View v = findViewById(R.id.mainLayout);
                            v.setBackgroundColor(CustomFit.getColor(key, R.color.colorAccent));
                        } else if(key.equals("welcome-banner")) {

                            TextView textView = findViewById(R.id.quickstart_message);
                            textView.setText(CustomFit.getString("welcome-banner", "Hello")+" "+user.getFirstName());
                        }


                    }
                });
            }
        };

        CustomFit.registerConfigChange("daily-ninja",observer);
        CustomFit.registerConfigChange("configcolor",observer);
        CustomFit.registerConfigChange("welcome-banner",observer);


       /* CustomFit.CFConfigChangeObserver observer = new CustomFit.CFConfigChangeObserver() {
            @Override
            public void onChanged(Date hasConfigChanged) {
                TextView textView = findViewById(R.id.quickstart_message);

                //Boolean View
                TextView booleanView = findViewById(R.id.boolean_message);
                booleanView.setText("Boolean Config : "+CustomFit.getBoolean("configboolean", true));

                //Number View
                TextView numberView = findViewById(R.id.number_message);
                numberView.setText("Number Config: "+CustomFit.getNumber("confignumberdouble",100));

                //String view
                TextView stringView = findViewById(R.id.string_message);
                stringView.setText("String Config: "+CustomFit.getString("config-string", "DEFAULT String"));

                //Creating JSON object to pass for fallback value
                JsonObject device_customer_id = new JsonObject();
                device_customer_id.addProperty("device_customer_id","123" );

                //JSON View
                TextView jsonView = findViewById(R.id.json_message);
                jsonView.setText("Json Config : "+CustomFit.getJson("configjson", device_customer_id));

                //Color View
                TextView colorView = findViewById(R.id.color_message);
                colorView.setText("Color Config : "+CustomFit.getColor("configcolor", R.color.blue_grey_500));

                //Rich Text View
                TextView richtextView = findViewById(R.id.richtext_message);
                richtextView.setText("Rich Text Config : "+CustomFit.getRichText("configrichtext", new SpannableString("Hello")),TextView.BufferType.SPANNABLE);

                View v = findViewById(R.id.mainLayout);
                v.setBackgroundColor(CustomFit.getColor("configcolor", R.color.colorAccent));

                ImageView iv = findViewById(R.id.image1);
                Picasso.get().load(CustomFit.getString("baqalademo", "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")).into(iv);
                Log.d("CF", "variation changed: "+CustomFit.getString("new", "SG_DEFAULT1"));
            }
        };

        CustomFit.observeConfigChange(this,observer);
*/
        View v = findViewById(R.id.mainLayout);
        v.setBackgroundColor(CustomFit.getColor("configcolor", R.color.colorAccent));


        ImageView iv = findViewById(R.id.image1);
        Picasso.get().load(CustomFit.getString("daily-ninja", "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")).into(iv);

        TextView textView = findViewById(R.id.quickstart_message);
        textView.setText(CustomFit.getString("welcome-banner", "Hello")+" "+user.getFirstName());
        //Boolean View
        //TextView booleanView = findViewById(R.id.boolean_message);
        //booleanView.setText("Boolean Config : "+CustomFit.getBoolean("configboolean", true));

        //Number View
        //TextView numberView = findViewById(R.id.number_message);
        //numberView.setText("Number Config : "+CustomFit.getNumber("confignumberdouble",100));

        //String View
        TextView stringView = findViewById(R.id.string_message);
        stringView.setText("String Config : "+CustomFit.getString("welcome-banner", "SG_DEFAULT"));
      //  TextView stringView = findViewById(R.id.string_message);
       // stringView.setText("String Config : "+CustomFit.getString("config-string", "SG_DEFAULT"));

        //Creating JSON object to pass for fallback value
        //JsonObject device_customer_id = new JsonObject();
        //device_customer_id.addProperty("device_customer_id","123" );

        //JSON View
        //TextView jsonView = findViewById(R.id.json_message);
        //jsonView.setText("Json Config : "+CustomFit.getJson("configjson", device_customer_id));

        //Color View
        //TextView colorView = findViewById(R.id.color_message);
        //colorView.setText("Color Config : "+CustomFit.getColor("configcolor", R.color.blue_grey_500));

        //Rich Text View
        //TextView richtextView = findViewById(R.id.richtext_message);
        //richtextView.setText("Rich Text Config : "+CustomFit.getRichText("configrichtext", new SpannableString("Hello")),TextView.BufferType.SPANNABLE);

        Log.d("CF", "Initial variation fetched: "+CustomFit.getString("sg_test_flag", "SG_DEFAULT1"));


        Button logTokenButton = findViewById(R.id.generateEventButton);
        logTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CFTracker.trackEvent(new CFEvent.Builder("sgev1").eventProperty("age",30).eventProperty("name","ashwin")
                        .build());
            }
        });
    }
}
