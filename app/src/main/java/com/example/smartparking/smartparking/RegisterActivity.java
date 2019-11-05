package com.example.smartparking.smartparking;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSingin;
    private EditText editPhone;
    private EditText editName;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        editName = (EditText) findViewById(R.id.uName);
        editPhone = (EditText) findViewById(R.id.uPhone);
        buttonRegister = (Button) findViewById(R.id.uRegister);
        editTextEmail = (EditText) findViewById(R.id.uEmail);
        editTextPassword = (EditText) findViewById(R.id.uPassword);
        textViewSingin = (TextView) findViewById(R.id.uSingup);


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });


    }

    private void registerUser(){
        final String email =editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String name = editName.getText().toString().trim();
        final String phone = editPhone.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            // email is empty
            Toast.makeText(this,"Please Enter email",Toast.LENGTH_SHORT).show();
            //Stopping the function exection feature
            return;
        }
        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this,"Please Enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(name)){
            // email is empty
            Toast.makeText(this,"Please Enter Name",Toast.LENGTH_SHORT).show();
            //Stopping the function exection feature
            return;
        }
        if(TextUtils.isEmpty(phone)){
            // email is empty
            Toast.makeText(this,"Please Enter AGe",Toast.LENGTH_SHORT).show();
            // Stopping the function exection feature
            return;
        }
        //if validation are ok
        //we will first show progressbar
        // progressDialog.setMessage("Register User....");
        // progressBar.setVisibility(View,VISIBLE);
        //  progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful()){
                            //User is successfully register and loged in
                            // we will start the profile activity here
                            //right now lets display a toast only
                            //  progressDialog.dismiss();

                            User user = new User(
                                    name,
                                    email,
                                    phone
                            );
                            FirebaseDatabase.getInstance().getReference()
                                    .child("User").child("homeowner").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"Register SuccessFully",Toast.LENGTH_SHORT).show();
                                        finish();
                                        Shared shared=new Shared(RegisterActivity.this);
                                        shared.setName(email);
                                        shared.setPassword(password);
                                        Intent intent =new Intent(RegisterActivity.this,HomeMainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                    else{
                                        // progressDialog.dismiss();
                                        if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                            Toast.makeText(getApplicationContext(),"You are already Register",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(RegisterActivity.this, RegisterActivity.class));
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), " Please Try again....", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(RegisterActivity.this, RegisterActivity.class));
                                        }
                                    }
                                }
                            });

                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                          //  Toast.makeText(RegisterActivity.this,"Some Thing Wants To Wrong..",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this,RegisterActivity.class));
                        }
                    }
                });


    }
}
