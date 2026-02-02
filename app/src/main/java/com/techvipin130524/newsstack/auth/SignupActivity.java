package com.techvipin130524.newsstack.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.techvipin130524.newsstack.MainActivity;
import com.techvipin130524.newsstack.R;

public class SignupActivity extends AppCompatActivity {
    private EditText fullName, emailSignup, passwordSignup;
    private Button btnSignUp;
    private TextView txtSignin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        fullName = findViewById(R.id.fullName);
        emailSignup = findViewById(R.id.emailSignup);
        passwordSignup = findViewById(R.id.passwordSignup);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtSignin = findViewById(R.id.txtSignin);

        // Handle Sign-Up Button Click
        btnSignUp.setOnClickListener(v -> registerUser());

        // Handle Sign-In Redirect
        txtSignin.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is already signed in (non-null)
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            // The user is already logged in, take them to the main activity
            updateUI();
        }
    }

    private void updateUI() {
        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void registerUser() {
        String name = fullName.getText().toString().trim();
        String email = emailSignup.getText().toString().trim();
        String password = passwordSignup.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            fullName.setError("Full Name is required!");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            emailSignup.setError("Email is required!");
            return;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            passwordSignup.setError("Password must be at least 6 characters!");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d("Success", "createUserWithEmail:success");
                            Toast.makeText(SignupActivity.this, "Sign Up Successfully", Toast.LENGTH_SHORT).show();
                            updateUI();

//                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
//                            startActivity(intent);
//                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            // Log.d("error", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
}