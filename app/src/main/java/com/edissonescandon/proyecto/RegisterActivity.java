package com.edissonescandon.proyecto;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {


    private EditText mEditTextUsuario;
    private EditText mEditTextEmail;
    private EditText mEditTextDireccion;
    private EditText mEditTextTelefono;
    private EditText mEditTexPassword;
    private Button mButtonRegustrarse;

    private String usuario = "";
    private String email = "";
    private String direccion = "";
    private String telefono = "";
    private String password = "";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth=FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mEditTextUsuario = (EditText) findViewById(R.id.editTextUsuario);
        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        mEditTextDireccion = (EditText) findViewById(R.id.editTextDireccion);
        mEditTextTelefono = (EditText) findViewById(R.id.editTextTelefono);
        mEditTexPassword = (EditText) findViewById(R.id.editTextContraseña);

        mButtonRegustrarse = (Button) findViewById(R.id.Registro);

        mButtonRegustrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = mEditTextUsuario.getText().toString();
                email = mEditTextEmail.getText().toString();
                direccion = mEditTextDireccion.getText().toString();
                telefono = mEditTextTelefono.getText().toString();
                password = mEditTexPassword.getText().toString();

                if (!usuario.isEmpty() && !email.isEmpty() && !direccion.isEmpty() && !telefono.isEmpty() && !password.isEmpty()){
                    if (password.length()>=6){
                        registerUser();
                    }else {
                        Toast.makeText(RegisterActivity.this, "La contraseña tiene que tener almenos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(RegisterActivity.this,"Debe completar el formulario",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void registerUser(){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();
                    map.put("usuario",usuario);
                    map.put("email",email);
                    map.put("direccion",direccion);
                    map.put("telefono",telefono);
                    map.put("password",password);

                    String id= mAuth.getCurrentUser().getUid();

                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "No se pudieron crear el ususario", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(RegisterActivity.this, "No se pudo registrar el Usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
