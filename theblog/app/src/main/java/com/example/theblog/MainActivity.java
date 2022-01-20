package com.example.theblog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
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

    //All variables for the firsty page

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;
    private Button loginButton;
    private Button createActButton;
    private EditText loginEmail;
    private EditText loginPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TO authinicate First setup mAuth

        mAuth = FirebaseAuth.getInstance();

        loginButton = (Button) findViewById(R.id.loginbtn);
        createActButton = (Button) findViewById(R.id.crt_ac_btn);
        loginEmail = (EditText) findViewById(R.id.loginEmail);
        loginPass = (EditText) findViewById(R.id.loginpass);


        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                mUser = firebaseAuth.getCurrentUser();

                if(mUser!= null){
                    Toast.makeText(MainActivity.this,"Signed In",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Not Signed In",Toast.LENGTH_LONG).show();
                }

            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(loginEmail.getText().toString())
                && !TextUtils.isEmpty((loginPass.getText().toString()))){

                    String email = loginEmail.getText().toString();
                    String pwd = loginPass.getText().toString();
                    login(email, pwd);


                }
                else{

                }
            }
        });



    }

    private void login(String email, String pwd) {

        mAuth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            //IN
                            Toast.makeText(MainActivity.this,"Signed In",Toast.LENGTH_LONG).show();
                        }
                        else {

                        }

                    }
                });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //Next implement onStart & onStop


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuth != null){
            mAuth.removeAuthStateListener(mAuthListener);

        }
    }
}
