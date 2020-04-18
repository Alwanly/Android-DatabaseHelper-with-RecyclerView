package com.example.mad.articlenews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    DatabaseReference db;
    private static final int RC_SIGN_IN = 101 ;
    ProgressDialog loading;
    
    EditText etEmail,etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        db = FirebaseDatabase.getInstance().getReference("Users");
        
        mAuth = FirebaseAuth.getInstance();
        
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);


    }

    public void LoginGoogle(View view) {
        Toast.makeText(this, "ada", Toast.LENGTH_SHORT).show();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.this,gso);
        Login();
    }

    private void Login() {
        Toast.makeText(this, "ada 2 ", Toast.LENGTH_SHORT).show();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.w("error2", "Error ");
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this,HomeActivity.class));
        }

    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("11", "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            loading.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent login = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(login);
                            finish();
                        } else {

                            // If sign in fails, display a message to the user.
                            Log.w("error", "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }


    public void login(View view) {

        if (etEmail.getText().toString().equals("admin@admin.com")){
            Intent admin = new Intent(this,HomeActivity.class);
            startActivity(admin);
            finish();
            loading.dismiss();
        }else {
            mAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        loading.dismiss();
                        FirebaseUser user = mAuth.getCurrentUser();
                        Authentication(user.getUid());
                    } else {
                        loading.dismiss();
                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void Authentication(String uid) {

        Query query = db.equalTo(uid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                Intent login = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(login);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void regis(View view) {

        Intent regis = new Intent(this, RegistrationActivity.class);
        startActivity(regis);
    }
}
