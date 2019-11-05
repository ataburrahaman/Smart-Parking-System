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

public class SingUpActivity extends AppCompatActivity {
    private Button buttonSingup;
    private EditText emailLog;
    private EditText passwordlog;
    private TextView textViewSingup;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null){

        }


        buttonSingup = (Button) findViewById(R.id.uSingup);
        emailLog = (EditText) findViewById(R.id.uEmail);
        passwordlog = (EditText) findViewById(R.id.uPassword);
        textViewSingup = (TextView) findViewById(R.id.uForget);

        buttonSingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        textViewSingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }
    private void userLogin(){
        final String email = emailLog.getText().toString().trim();
        final String password = passwordlog.getText().toString().trim();
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

        //if validation are ok
        //we will first show progressbar

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(SingUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //  progressDialog.dismiss();
                        if(task.isSuccessful()){
                            //start the profile activity
                            Toast.makeText(getApplicationContext(),"SuccessFully Login",Toast.LENGTH_SHORT).show();
                            finish();
                            Shared shared=new Shared(SingUpActivity.this);
                            shared.setName(email);
                            shared.setPassword(password);
                            // Validation.validation(shared);
                            Intent intent =new Intent(SingUpActivity.this,HomeMainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                            //to change the boolean value as true
                            // shared.secondTime();
                        }
                        else{

                            Toast.makeText(SingUpActivity.this,"Please Enter Currect Email and Password",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}
