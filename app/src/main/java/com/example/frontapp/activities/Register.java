package com.example.frontapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.frontapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText registerName,registerEmail,registerPassword,registerConfirmationPassword;
    Button registerUserButton, back2Login;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerName = findViewById(R.id.registerFullName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerConfirmationPassword = findViewById(R.id.registerConfirmationPassword);
        registerUserButton = findViewById(R.id.registerButton);
        back2Login = findViewById(R.id.back2Loggin);

        fAuth = FirebaseAuth.getInstance();

        back2Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), loginActivity.class));
                finish();
            }
        });


        registerUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //comprovem que les dades són valides

                String name = registerName.getText().toString();
                String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();
                String confirmedPassword  = registerConfirmationPassword.getText().toString();

                if(name.isEmpty()){
                    registerName.setError("name is required");
                }
                else if(email.isEmpty()){
                    registerEmail.setError("email is required");
                }
                else if(password.isEmpty()){
                    registerPassword.setError("password is required");
                }
                else if(confirmedPassword.isEmpty()){
                    registerConfirmationPassword.setError("confirmed password is required");
                }
                else if(!password.equals(confirmedPassword)){
                    registerConfirmationPassword.setError("password don't match");
                }
                //dades validades
                //registrar a firebases
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "dades validades", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            //send user to next page
                            //hem de registrar el user a la nostre BD
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            //borra les activitats obertes abans de pasar a la seguent
                            finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    });
                }

            }
        });

    }
}