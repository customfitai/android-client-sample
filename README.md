This library is compatible with Android SDK versions 15 and up (4.0 ICE_CREAM_SANDWICH)

How to use:

1. Declare a dependency in the build.gradle of the app module

	```
	dependencies {
        ...
        implementation 'ai.customfit:customfit-android-client:1.0.0'
        ...
        }
	```

2. Make the following entries in the build.gradle of the project

	```
	allprojects {
   repositories {
	      ....
	        maven {
            url "https://maven.customfit.ai/artifactory/gradle-release-local"
        }
   }
}
	```

3. Add the following code snipet in your AndroidManifest.xml file to setup CustomFit.ai push notification
	
	```
	<service android:name="ai.customfit.android.CFMessagingService">
        <intent-filter>
                <action android:name="com.google.firebase.MESSAGING_EVENT" />
        </intent-filter>
	</service>
	```

4. Upload the FCM server key in CustomFit.ai dashboard. To know how to upload please visit [here] (https://docs.customfit.ai/customfit-ai/set-up-customfit.ai-push-notification)

5. Download the google-services.json file from FCM console and place it under the app directory

Learn more
----------
Check out our [documentation](http://docs.customfit.ai) for detailed instructions on configuring and using CustomFit.ai. You can also find the[complete guide for integrating SDK](https://docs.customfit.ai/customfit-ai/android-sdk).

About CustomFit.ai
------------------
[CustomFit.ai] (https://customfit.ai/) is a User Journey Manager for B2C Apps. Every customer is unique, so should be their App. With CustomFit.ai one can craft hyper-personalized app experience to each of their user or segment of users. The business users (non-tech teams - sales & marketing) can create multiple variants of your App (mobile/web) without coding, making it custom-fit to your users

For example, Facebook may be at any given point in time will be running not less than 10000 variations of its app. How it is appearing & behaving for me may be completely different than yours. Also, it will constantly be monitoring the user's reactions under those experiences. CustomFit.ai is exactly giving that kind of power and scale for your app, making your app custom-fit to your users.

There are a bunch of other use cases which has benefits for every stakeholder - Engineering Team, Product Team & Marketing-Sales team. Please visit [www.customfit.ai] (https://customfit.ai/) for more info.