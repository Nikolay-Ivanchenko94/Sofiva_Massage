package com.example.sofivamassage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileMainActivity2 extends AppCompatActivity {
    private TextView TextView_image_profile_dp,TextView_show_name,TextView_show_surname,TextView_show_email,TextView_show_dob,TextView_show_phone;
    private  String name,surname,email,dob,phone;
    private ImageView imageView;
    private FirebaseAuth authProfile;
    private ProgressBar ProgressBar_user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_main2);

        TextView_show_name = findViewById(R.id.TextView_show_name);
        TextView_show_surname = findViewById(R.id.TextView_show_surname);
        TextView_show_email = findViewById(R.id.TextView_show_email);
        TextView_show_dob = findViewById(R.id.TextView_show_dob);
        TextView_show_phone = findViewById(R.id.TextView_show_phone);
        TextView_image_profile_dp = findViewById(R.id.TextView_image_profile_dp);
        ProgressBar_user = findViewById(R.id.ProgressBar_user);



        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        if (firebaseUser == null){
            Toast.makeText(UserProfileMainActivity2.this,"Щось пішло не так! Інформація про користувача недоступна",Toast.LENGTH_LONG).show();

        } else {
            checkIfEmailVerified(firebaseUser);
            ProgressBar_user.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }
    }
         //Users coming to UserProfileMainActivity2 after successful registration

    private void checkIfEmailVerified(FirebaseUser firebaseUser) {
        if(!firebaseUser.isEmailVerified()){
            showAlertDialog();
        }

    }

    private void showAlertDialog() {
        //Setup the Alert Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileMainActivity2.this);
        builder.setTitle("Email не зареєстрований");
        builder.setMessage("Будь ласка перевірте ваш Email зараз.Ви не можете війти без пітвердження Email наступний час");


        //Open Email Apps if User click/taps Continue button
        builder.setPositiveButton("Продовжити", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //Create the AlertDialog
        AlertDialog alertDialog = builder.create();

        //Show the AlertDialog
        alertDialog.show();
    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();

        //Exctracting User Reference from Database from "Registered Users"
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Зареєстрований користувач");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readWriteUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readWriteUserDetails != null) {
                    name = firebaseUser.getDisplayName();
                    surname = firebaseUser.getDisplayName();
                    email = firebaseUser.getEmail();
                    dob = readWriteUserDetails.date;
                    phone = readWriteUserDetails.phone;

                    TextView_image_profile_dp.setText("Привіт, " + name + "!");
                    TextView_show_name.setText(name);
                    TextView_show_surname.setText(surname);
                    TextView_show_email.setText(email);
                    TextView_show_dob.setText(dob);
                    TextView_show_phone.setText(phone);

                }
                ProgressBar_user.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfileMainActivity2.this,"Щось пішло не так!",Toast.LENGTH_LONG).show();
                ProgressBar_user.setVisibility(View.GONE);
            }
        });
    }
    }
