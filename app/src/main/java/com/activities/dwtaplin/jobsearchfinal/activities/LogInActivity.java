package com.activities.dwtaplin.jobsearchfinal.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.activities.dwtaplin.jobsearchfinal.R;
import com.activities.dwtaplin.jobsearchfinal.actors.User;
import com.activities.dwtaplin.jobsearchfinal.database.LocalDatabaseManager;
import com.activities.dwtaplin.jobsearchfinal.database.ServerManager;

import java.util.Locale;

public class LogInActivity extends AppCompatActivity {
    private EditText editUserName, editPassWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getSupportActionBar().setTitle("Job Search");
        getSupportActionBar().setSubtitle("Log in");
        Button btnLogIn = findViewById(R.id.btnLogIn);
        Button btnRegister = findViewById(R.id.btnRegister);
        btnLogIn.setOnClickListener(view -> logInButtonPressed());
        btnRegister.setOnClickListener(view -> registerButtonPressed());
        AssetManager assetManager = this.getApplicationContext().getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, String.format(Locale.US, "fonts/%s", "PictorialSignature.ttf"));
        TextView txtSlogan = findViewById(R.id.txtSlogan);
        txtSlogan.setTypeface(typeface);
        TextView txtForgotten = findViewById(R.id.txtForgotten);
        txtForgotten.setOnClickListener(view -> forgottenPasswordDialog());
        editPassWord = findViewById(R.id.editPassword);
        editUserName = findViewById(R.id.editUsername);
    }

    private void forgottenPasswordDialog() {
        AlertDialog.Builder alertAdd = new AlertDialog.Builder(this);
        LayoutInflater factory = LayoutInflater.from(this);
        final View view = factory.inflate(R.layout.reset_dialog, null);
        alertAdd.setView(view);
        alertAdd.show();
    }

    private void logInButtonPressed() {
        if((!editUserName.getText().toString().isEmpty()) && (!editPassWord.getText().toString().isEmpty()))
            new BackgroundWorker().execute();
        else
            Toast.makeText(this, "Email and password fields must both be entered", Toast.LENGTH_SHORT).show();
    }

    private void registerButtonPressed() {
        startActivity(new Intent(this, RegistrationActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(1);
    }

    private class BackgroundWorker extends AsyncTask{
        private boolean loggedIn = false;
        private User user;
        public BackgroundWorker() {

        }

        @Override
        protected Object doInBackground(Object[] objects) {
            ServerManager serverManager = new ServerManager(LogInActivity.this);
            user = serverManager.logIn(editUserName.getText().toString(), editPassWord.getText().toString());
            serverManager.updateToken(user.getServerId(), new LocalDatabaseManager(LogInActivity.this).getToken());
            if(user != null)
                loggedIn = true;
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(loggedIn){
                new LocalDatabaseManager(LogInActivity.this).updateUser(user);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }else{
                Toast.makeText(LogInActivity.this, "Invalid email password combination", Toast.LENGTH_SHORT).show();
                editPassWord.setText("");
            }
        }
    }
}
