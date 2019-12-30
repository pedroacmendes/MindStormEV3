package com.example.mindstormev3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistoActivity extends AppCompatActivity {

    private Button btn_voltar;
    private Button btn_registo;
    private EditText edt_email;
    private EditText edt_password;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);

        btn_voltar = (Button) findViewById(R.id.btn_voltar);
        btn_registo = (Button) findViewById(R.id.btn_registo);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_password = (EditText) findViewById(R.id.edt_password);

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_registo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt_email.getText().toString().trim();
                String password = edt_password.getText().toString().trim();
                criarUser(email, password);
            }
        });
    }


    private void criarUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegistoActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            alert("Usuario sucesso");
                            Intent intent = new Intent(RegistoActivity.this, PerfilActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            alert("Usuario erro");
                        }
                    }
                });
    }

    private void alert(String msg){
        Toast.makeText(RegistoActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conection.getFirebaseAuth();
    }
}
