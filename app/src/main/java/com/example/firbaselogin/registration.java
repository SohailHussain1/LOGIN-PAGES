package com.example.firbaselogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class registration extends AppCompatActivity {

    TextView alreadyAcc;
    EditText email,password,conform_password;
    Button reg;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    ProgressDialog progressDialog;
    FirebaseAuth mAuth;                                             //step 3
    FirebaseUser firebaseUser;                                     //step 3

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);
        alreadyAcc=findViewById(R.id.already);
        email=findViewById(R.id.email_in);
        password=findViewById(R.id.password);
        conform_password=findViewById(R.id.password);
        reg=findViewById(R.id.register);
        progressDialog=new ProgressDialog(this);


        mAuth=FirebaseAuth.getInstance();                                   //step 4
        firebaseUser=mAuth.getCurrentUser();                                //step 4


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Authention();
            }
        });



        alreadyAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(registration.this,MainActivity.class));
            }
        });
    }

    private void Authention() {
        String s_email=email.getText().toString();
        String s_password=password.getText().toString();
        String confrimPassword=conform_password.getText().toString();


        if (!s_email.matches(emailPattern))
        {
            email.setError("Enter Connext Email");
        }
        else if (s_password.isEmpty() || s_password.length()<6)
        {
            password.setError("Enter Proper Password");
        }
        else if (!s_password.equals(confrimPassword))
        {
            conform_password.setError("Password Not match Both field");
        }
        else
        {
            progressDialog.setMessage("Please Wait While Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            //step 5

            mAuth.createUserWithEmailAndPassword(s_email,s_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        sendUsertoNextActivity();                                                    //step 6

                        Toast.makeText(registration.this, " Registration Successful", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(registration.this, ""+task.getException(), Toast.LENGTH_SHORT).show();    //show error
                    }

                }

                private void sendUsertoNextActivity() {
                    Intent intent=new Intent(registration.this,last.class);                 //step 7
                    intent. setFlags (Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);       // step 8
                    startActivity(intent);
                }
            });



        }

    }
}