package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
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
import com.google.firebase.ktx.Firebase;

public class CreateAccount extends AppCompatActivity {

    EditText email_text, password, confirm_password;
    Button create_acc_btn;
    ProgressBar progress_bar;
    TextView login_text_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        email_text = findViewById(R.id.email_text);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        create_acc_btn = findViewById(R.id.create_acc_btn);
        progress_bar = findViewById(R.id.progress_bar);
        login_text_btn = findViewById(R.id.login_text_btn);


        create_acc_btn.setOnClickListener(v -> createAccount());
        login_text_btn.setOnClickListener(v -> finish());


    }

    void createAccount() {
        String email = email_text.getText().toString();
        String password1 = password.getText().toString();
        String confirmPassword = confirm_password.getText().toString();

        boolean isValidated = validateData(email,password1,confirmPassword);
        if(!isValidated){
            return;
        }

        createFirebaseAcc(email,password1);
    }

    void createFirebaseAcc(String email, String password1){
        changeInProgress(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener(CreateAccount.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        changeInProgress(false);
                        if(task.isSuccessful()){
                            Toast.makeText(CreateAccount.this,"Successfully created acc, Check Email to verify",Toast.LENGTH_SHORT).show();
                            firebaseAuth.getCurrentUser().sendEmailVerification();
                            firebaseAuth.signOut();
                            finish();
                        }else
                        {
                            Toast.makeText(CreateAccount.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                }



        );


    }

    void changeInProgress(boolean inProgress){
        if (inProgress){
            progress_bar.setVisibility(View.VISIBLE);
            create_acc_btn.setVisibility(View.GONE);
        }
        else {
            progress_bar.setVisibility(View.GONE);
            create_acc_btn.setVisibility(View.VISIBLE);
        }
    }

    boolean validateData(String email, String password1, String confirmPassword) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_text.setError("Invalid Email!");
            return false;
        }
        if(password1.length()<6){
            password.setError(("Length should be > 6"));
            return false;
        }

        if (!password1.equals(confirmPassword)){
            confirm_password.setError("Please check again");
            return false;
        }
        return true;

    }

}