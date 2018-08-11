package com.adamapps.chatpanther.user;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adamapps.chatpanther.Home;
import com.adamapps.chatpanther.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserInfo extends AppCompatActivity {

    EditText userName,userAbout;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        userName = findViewById(R.id.user_display_name);
        userAbout = findViewById(R.id.user_about_text);
        saveBtn = findViewById(R.id.saveUserInfo);

        if(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()!=null){
            userName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userName.getText().toString().isEmpty()){
                    YoYo.with(Techniques.Shake).duration(500).playOn(userName);
                    return;
                }
                if(userAbout.getText().toString().isEmpty()){
                    YoYo.with(Techniques.Shake).duration(500).playOn(userAbout);
                    return;
                }
                if(!userName.getText().toString().isEmpty()&&!userAbout.getText().toString().isEmpty()){
                    YoYo.with(Techniques.RubberBand).duration(500).playOn(v);
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("name",userName.getText().toString());
                    map.put("about",userAbout.getText().toString());

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(userName.getText().toString())
                            .build();
                    FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates);
                    FirebaseDatabase.getInstance().getReference("UserInfo")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    finish();
                                    startActivity(new Intent(UserInfo.this, Home.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserInfo.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });



    }
}
