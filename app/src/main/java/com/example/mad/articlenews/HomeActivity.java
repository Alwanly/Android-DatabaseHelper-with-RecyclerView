package com.example.mad.articlenews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Article");
        Button btn_create_list = findViewById(R.id.btn_create_list);
        Button btn_article_list = findViewById(R.id.btn_article_list);
        TextView tv_name = findViewById(R.id.tv_username);
        CircleImageView profile= findViewById(R.id.img_profile);
       mFirebaseAuth =  FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser.getEmail() != null){
            Glide.with(this).load(mFirebaseUser.getPhotoUrl()).override(300).into(profile);
            tv_name.setText("Good Morning \n"+mFirebaseUser.getDisplayName());
        }

        btn_create_list.setOnClickListener(this);
        btn_article_list.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_create_list:
                Intent create = new Intent(this,MainActivity.class);
                startActivity(create);
                break;
            case R.id.btn_article_list:
                Intent list = new Intent(this,ListActivity.class);
                startActivity(list);
        }
    }

    public void logout(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Apakah ada yakin ingin Logput ?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(HomeActivity.this.getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                // Build a GoogleSignInClient with the options specified by gso.
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(HomeActivity.this, gso);
                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mFirebaseAuth.getInstance().signOut();
                        Intent login = new Intent(HomeActivity.this,LoginActivity.class);
                        startActivity(login);
                        finish();
                    }
                });
            }
        });
     builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
             dialog.cancel();
         }
     });
     builder.show();
    }
}