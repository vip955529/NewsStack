package com.techvipin130524.newsstack.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.techvipin130524.newsstack.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText mail;
    Button button;
    // ProgressBar progressBar;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //ID's
        mail = findViewById(R.id.emailResetPassword);
        button = findViewById(R.id.btnResetPassword);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail = mail.getText().toString();

                resetPassword(userEmail);

            }
        });

    }

    public void resetPassword(String userEmail)
    {
        // progressBar.setVisibility(View.VISIBLE);

        auth.sendPasswordResetEmail(userEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(ForgotPasswordActivity.this,
                                    "We sent an email to reset your password",
                                    Toast.LENGTH_LONG).show();
                            button.setClickable(false);
                           //  progressBar.setVisibility(View.INVISIBLE);
                            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(ForgotPasswordActivity.this,
                                    "Sorry, There is a problem. Please try again later...",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }

}