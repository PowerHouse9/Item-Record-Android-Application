package com.bill.ordersystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignupActivity extends AppCompatActivity {

    private Button signInButton;
    private Button signupButton;
    private EditText passText;
    private EditText emailText;

    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        signInButton = findViewById(R.id.btn_signin);
        signupButton = findViewById(R.id.btn_signup);
        passText = findViewById(R.id.txt_password);
        emailText = findViewById(R.id.txt_email);

        progressBar = findViewById(R.id.progressbar);

        signInActivityButton();
        signupFunction();
    }

    private void registerUser(){
        String Email = emailText.getText().toString().trim();
        String Password = passText.getText().toString().trim();

        if (TextUtils.isEmpty(Email)){
            emailText.setError("Please Enter Valid Email Address");
            emailText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            emailText.setError("Please Enter Valid Email Address");
            emailText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Password)){
            passText.setError("Please Enter Valid Password");
            passText.requestFocus();
            return;
        }

        if(Password.length()<6){
            passText.setError("Minimum Password Length Must Be 6");
            passText.requestFocus();
            return;
        }

        //if validations are ok, we will show progress bar first
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()){

                            //if user is successfully registered and logged in
                            Toast.makeText(SignupActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignupActivity.this, HomepageActivity.class);
                            //activity clear top is used for making the user don't go back to login activity again when back button is pressed
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        }else {
                            //to check if user already registered
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(), "You are Already Registered", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }

    private void signupFunction(){
        signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (view == signupButton)
                {
                    registerUser();
                }
            }
        });
    }

    private void signInActivityButton() {

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
