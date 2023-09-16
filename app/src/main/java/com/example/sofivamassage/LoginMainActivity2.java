package com.example.sofivamassage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginMainActivity2 extends AppCompatActivity {
    private EditText EditText_login_email,EditText_login_password;
    private Button btn_login;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;
    private static final String TAG = "LoginMainActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main2);
       Toast.makeText(LoginMainActivity2.this,"Увійти зараз?",Toast.LENGTH_LONG).show();
       EditText_login_email = findViewById(R.id.EditText_login_email);
       EditText_login_password = findViewById(R.id.EditText_login_password);
       btn_login = findViewById(R.id.btn_login);
       progressBar = findViewById(R.id.progressbar_login);

       authProfile = FirebaseAuth.getInstance();
       btn_login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String TextEmail = EditText_login_email.getText().toString();
               String TextPassword = EditText_login_password.getText().toString();
               if (TextUtils.isEmpty(TextEmail)) {
                   Toast.makeText(LoginMainActivity2.this,"Введіть дані у полі Email",Toast.LENGTH_LONG).show();
                   EditText_login_email.setError("Ви ввели невірні дані у полі Email");
                   EditText_login_email.requestFocus();
               } if (!Patterns.EMAIL_ADDRESS.matcher(TextEmail).matches()) {
                   Toast.makeText(LoginMainActivity2.this,"Введіть повторно ваш Email",Toast.LENGTH_LONG).show();
                   EditText_login_email.setError("Невірний Email");
                   EditText_login_email.requestFocus();
               } if (TextUtils.isEmpty(TextPassword)) {
                   Toast.makeText(LoginMainActivity2.this,"Введіть дані у полі пароль",Toast.LENGTH_LONG).show();
                   EditText_login_password.setError("Ви ввели невірні дані у полі пароль");
                   EditText_login_password.requestFocus();
               } if (TextPassword.length() < 5 ) {
                   Toast.makeText(LoginMainActivity2.this,"Пароль повине мати от 5 символів",Toast.LENGTH_LONG).show();
                   EditText_login_password.setError("Надто слабкий пароль");
                   EditText_login_password.requestFocus();
               } else {
                   EditText_login_password.clearComposingText();
                   loginUser(TextEmail,TextPassword);
               }
           }
       });


           }

    private void loginUser(String textEmail, String textPassword) {
        authProfile.signInWithEmailAndPassword(textEmail,textPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    //Get Instance of the current User
                    FirebaseUser firebaseUser = authProfile.getCurrentUser();

                    //Check if email is verified before user can access their profile
                    if (firebaseUser.isEmailVerified()){
                        Toast.makeText(LoginMainActivity2.this,"Ви увійшли зараз",Toast.LENGTH_LONG).show();

                        //Open User Profile
                    } else {
                        firebaseUser.sendEmailVerification();
                        authProfile.signOut(); //Sign Out user
                        showAlertDialog();
                    }
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e ) {
                        EditText_login_email.setError("Користувач існує або більше не дісний Будь ласка зареєструйтися знову");
                        EditText_login_email.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e ) {
                        EditText_login_email.setError("Недійсний обликовий запис Будь ласка перевірти");
                        EditText_login_email.requestFocus();
                    } catch (Exception e ) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(LoginMainActivity2.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }

                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void showAlertDialog() {
        //Setup the Alert Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginMainActivity2.this);
        builder.setTitle("Email не зареєстрований");
        builder.setMessage("Будь ласка перевірте ваш Email зараз.Ви не можете війти без пітвердження Email");


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
    //Check  if User is already logged in. In  such case, straightaway take the user to the User's profile

    @Override
    protected void onStart() {
        super.onStart();
        if (authProfile.getCurrentUser() != null) {
            Toast.makeText(LoginMainActivity2.this,"Увійти зараз!",Toast.LENGTH_LONG).show();

            //Start the UserProfileMainActivity2
            startActivity(new Intent(LoginMainActivity2.this, UserProfileMainActivity2.class));
            finish();
        } else {
            Toast.makeText(LoginMainActivity2.this,"Ви повині зареєструватися зараз!",Toast.LENGTH_LONG).show();

        }
    }
}
