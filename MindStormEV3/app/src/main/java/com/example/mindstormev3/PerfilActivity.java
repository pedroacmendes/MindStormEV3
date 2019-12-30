package com.example.mindstormev3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PerfilActivity extends AppCompatActivity {

    private TextView textEmail;
    private TextView textId;
    private Button btn_logout;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        textEmail = (TextView) findViewById(R.id.txt_email);
        textId = (TextView) findViewById(R.id.txt_id);
        btn_logout = (Button) findViewById(R.id.btn_voltar);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Conection.logOut();
                finish();
            }
        });
    }
/*
    @Override
    protected void onStart() {
        super.onStart();
        auth = Conection.getFirebaseAuth();
        user = Conection.getFirebaseUser();
        verificaUser();
    }

    private void verificaUser() {
        if(user == null){
            finish();
        } else {
            textEmail.setText("Email: " + user.getEmail());
            textId.setText("ID: " + user.getUid());
        }
    }
    */
}
