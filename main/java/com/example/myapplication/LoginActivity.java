package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    EditText email_text, password;
    Button login_btn;
    ProgressBar progress_bar;
    TextView create_account_text_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        email_text = findViewById(R.id.email_text);
        password = findViewById(R.id.password);
        login_btn= findViewById(R.id.login_btn);
        progress_bar = findViewById(R.id.progress_bar);
        create_account_text_btn = findViewById(R.id.create_account_text_btn);

        login_btn.setOnClickListener((v)->loginUser());
        create_account_text_btn.setOnClickListener((v)->startActivity(new Intent(LoginActivity.this, CreateAccount.class)));


    }

    void loginUser(){
            String email = email_text.getText().toString();
            String password1 = password.getText().toString();

            boolean isValidated = validateData(email,password1);
            if(!isValidated){
                return;
            }
        loginAccinFirebase(email,password1);


        }

        void loginAccinFirebase(String email,String password1){
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            changeInProgress(true);
            firebaseAuth.signInWithEmailAndPassword(email, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    changeInProgress(false);
                    if (task.isSuccessful()){
                        if(firebaseAuth.getCurrentUser().isEmailVerified()){
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();

                        }else {
                            Toast.makeText(LoginActivity.this,"Email not verified, try again",Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(LoginActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }

                }
            });


        }




    void changeInProgress(boolean inProgress){
        if (inProgress){
            progress_bar.setVisibility(View.VISIBLE);
            login_btn.setVisibility(View.GONE);
        }
        else {
            progress_bar.setVisibility(View.GONE);
            login_btn.setVisibility(View.VISIBLE);
        }
    }

    boolean validateData(String email, String password1) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_text.setError("Invalid Email!");
            return false;
        }
        if (password1.length()<6){
            password.setError(("Length should be > 6"));
            return false;
        }


        return true;

    }



}