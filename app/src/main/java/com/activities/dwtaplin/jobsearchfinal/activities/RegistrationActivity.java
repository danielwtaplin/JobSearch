package com.activities.dwtaplin.jobsearchfinal.activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.activities.dwtaplin.jobsearchfinal.R;
import com.activities.dwtaplin.jobsearchfinal.actors.User;
import com.activities.dwtaplin.jobsearchfinal.database.LocalDatabaseManager;
import com.activities.dwtaplin.jobsearchfinal.database.ServerManager;

import java.util.ArrayList;
import java.util.Locale;

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText editFirstName, editLastName, editUserName, editEmail, editPassword, editConfirm, editDescription;
    private Spinner spinnerQual, spinnerLoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setTitle("Job Search");
        getSupportActionBar().setSubtitle("Registration");
        TextView txtWelcome = findViewById(R.id.txtWelcome);
        AssetManager assetManager = this.getApplicationContext().getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, String.format(Locale.US, "fonts/%s", "PictorialSignature.ttf"));
        txtWelcome.setTypeface(typeface);
        spinnerQual = findViewById(R.id.spinnerQual);
        spinnerQual.setAdapter(ArrayAdapter.createFromResource(this, R.array.qualification, R.layout.spinner_resource));
        spinnerLoc = findViewById(R.id.spinnerLoc);
        spinnerLoc.setAdapter(ArrayAdapter.createFromResource(this, R.array.location, R.layout.spinner_resource));
        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editUserName = findViewById(R.id.editUserName);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editConfirm = findViewById(R.id.editPasswordConfirm);
        editDescription = findViewById(R.id.editDescription);
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(view -> registerButtonClick());
        TextView txtProfile = findViewById(R.id.txtProfilePic);
        ImageView imgProfile = findViewById(R.id.imgProfile);
        txtProfile.setOnClickListener(view -> selectImage());
        imgProfile.setOnClickListener(view -> selectImage());

    }

    private void selectImage() {

    }

    private void registerButtonClick() {
        if(editFirstName.getText().toString().isEmpty()){
            Toast.makeText(this, "Must enter first name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(editLastName.getText().toString().isEmpty()){
            Toast.makeText(this, "Must enter last name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(editUserName.getText().toString().isEmpty()){
            Toast.makeText(this, "Must pick a username", Toast.LENGTH_SHORT).show();
            return;
        }
        if(editEmail.getText().toString().isEmpty()){
            Toast.makeText(this, "Must enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(editPassword.getText().toString().isEmpty()){
            Toast.makeText(this, "Must enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!editConfirm.getText().toString().equals(editPassword.getText().toString())){
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            editConfirm.setText("");
            return;
        }
        String desc = !editDescription.getText().toString().isEmpty() ? editDescription.getText().toString() : "";
        String firstName = editFirstName.getText().toString();
        String lastName = editLastName.getText().toString();
        String userName = editUserName.getText().toString();
        String email = editEmail.getText().toString();
        String passWord = editPassword.getText().toString();
        String city = spinnerLoc.getSelectedItem().toString();
        ArrayList quals = new ArrayList();
        quals.add(spinnerQual.getSelectedItem().toString());
        User user = new User(firstName, lastName, userName, city, quals, email, desc);
        new BackgroundWorker(user, passWord).execute();
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class BackgroundWorker extends AsyncTask{
        private User user;
        private String passWord;
        private boolean success;
        public BackgroundWorker(User user, String passWord){
            this.user= user;
            this.passWord = passWord;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            success = new ServerManager(RegistrationActivity.this).register(user, passWord, new LocalDatabaseManager(RegistrationActivity.this).getToken());
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(success){
                Toast.makeText(RegistrationActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                editConfirm.setText("");
                editFirstName.setText("");
                editLastName.setText("");
                editUserName.setText("");
                editEmail.setText("");
                editPassword.setText("");
                editDescription.setText("");
                new LocalDatabaseManager(RegistrationActivity.this).updateUser(user);
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                Toast.makeText(RegistrationActivity.this, "Welcome " + user.getFirstName(), Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(RegistrationActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
