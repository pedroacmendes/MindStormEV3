package com.example.mindstormev3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private EditText editEmail;
    private Button btnResetPassword;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        editEmail = (EditText) findViewById(R.id.editEmail);
        btnResetPassword = (Button) findViewById(R.id.btnResetPassword);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();
                resetPassword(email);
            }
        });

    }

    private void resetPassword(String email) {

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(ResetPassword.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            alert("Um email foi enviado");
                            finish();
                        } else{
                            alert("Email nao registado");
                        }
                    }
                });
    }

    private void alert(String s) {
        Toast.makeText(ResetPassword.this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conection.getFirebaseAuth();
    }
}
