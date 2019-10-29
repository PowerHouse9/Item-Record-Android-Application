package com.bill.ordersystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    private Button signInButton;
    private Button signupButton;
    private EditText emailText;
    private EditText passText;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        signInButton = findViewById(R.id.btn_signin);
        signupButton = findViewById(R.id.btn_signup);
        emailText = findViewById(R.id.txt_email);
        passText = findViewById(R.id.txt_password);

        progressBar = findViewById(R.id.progressbar);

        signInButton();
        signUpActivityButton ();
    }


    private void userLogin() {

        final String Email = emailText.getText().toString().trim();
        final String Password = passText.getText().toString().trim();

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

        mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){

                    Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
                    //activity clear top is used for making the user don't go back to login activity again when back button is pressed
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        // to check if the user already logged in, if already logged in directly go to homepage
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this, HomepageActivity.class));
            finish();
        }
    }

    private void signInButton() {
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin ();
            }
        });

    }

    private void signUpActivityButton () {

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
