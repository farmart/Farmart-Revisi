package com.example.fujimiya.farmartrevisi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    Firebase Fref;
    EditText Fusername,Fpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Fusername = (EditText) findViewById(R.id.etUserName);
        Fpassword = (EditText) findViewById(R.id.etPass);
        Firebase.setAndroidContext(this);
        Fref = new Firebase("https://farmartcorp.firebaseio.com/anggota");
        Fref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(SignInActivity.this,"Data Berhasil Diambil", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public void signup(View view) {
        finish();
        Intent i = new Intent(SignInActivity.this,PilihActivity.class);
        startActivity(i);
    }

    public void signin(View view) {

        Fref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){

                    String nama = (String) child.child("username").getValue();
                    String password = (String) child.child("password").getValue();



                    if (child.child("username").getValue().toString().equals(Fusername.getText().toString()) && child.child("password").getValue().toString().equals(Fpassword.getText().toString() ) && child.child("status").getValue().toString().equals("Admin") ){
                        Toast.makeText(getApplicationContext(),"Anda berhasil login "+nama,Toast.LENGTH_LONG).show();
                        finish();
                        Intent i = new Intent(SignInActivity.this,AdminActivity.class);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Username dan password salah, silahkan coba kembali",Toast.LENGTH_LONG).show();
                    }

                    //Toast.makeText(getApplicationContext(),"Anda berhasil login "+password,Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}
