package com.activities.dwtaplin.jobsearchfinal.internet;

import com.activities.dwtaplin.jobsearchfinal.database.ServerManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

//import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class InstanceToken extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log.d(TAG, "Refreshed token: " + refreshedToken);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        new ServerManager(getApplicationContext()).sendRegistrationToServer(refreshedToken);
        System.out.println("Token refreshed");
    }

}
