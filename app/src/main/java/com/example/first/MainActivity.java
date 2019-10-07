package com.example.first;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText email, password, mobile;
    Button signup, toolbar;
    public static String strPassword, strEmail;
    long numMobile;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);
        mobile = findViewById(R.id.mobile);
        mAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbar);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmail = email.getText().toString();
                strPassword = password.getText().toString();

                mAuth.createUserWithEmailAndPassword(strEmail, strPassword)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "signup successfully",
                                            Toast.LENGTH_SHORT).show();
                                    sendVerificationEmail();
                                } else {
                                    /*
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);*/
                                }

                                // ...
                            }
                        });
            }
        });
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startToolbarActivity();
            }
        });
    }
    private void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainActivity.this, login.class));
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this, "signup Failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void startToolbarActivity(){
        Intent intent = new Intent(this, toolbar.class);
        startActivity(intent);
    }
}
