package com.example.sofivamassage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class RegisterMainActivity2 extends AppCompatActivity {
    private EditText EditText_register_name,EditText_register_surname,EditText_register_email,EditText_register_password,EditText_register_return_password,EditText_register_phone,EditText_register_data;

    private Button btn_register_main;
    private ProgressBar progressBar;
    private DatePickerDialog picker;
    private static  final String TAG = "RegisterMainActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_main2);
        Toast.makeText(this, "Зареєструватися зараз?", Toast.LENGTH_SHORT).show();
        EditText_register_name = findViewById(R.id.EditText_register_name);
        EditText_register_surname = findViewById(R.id.EditText_register_surname);
        EditText_register_email = findViewById(R.id.EditText_register_email);
        EditText_register_password = findViewById(R.id.EditText_register_password);
        EditText_register_return_password = findViewById(R.id.EditText_register_return_password);
        EditText_register_phone = findViewById(R.id.EditText_register_phone);
        EditText_register_data = findViewById(R.id.EditText_register_date);

        //Setting up DatePicker on EditText
        EditText_register_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                //Date Picker Dialog
                picker = new DatePickerDialog(RegisterMainActivity2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        EditText_register_data.setText(dayOfMonth + "/" + (month + 1) + "/" + year );

                    }
                }, year, month ,day);
                picker.show();
            }
        });
        btn_register_main = findViewById(R.id.btn_register_main);
        btn_register_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TextName = EditText_register_name.getText().toString();
                String TextSurname = EditText_register_surname.getText().toString();
                String TextEmail = EditText_register_email.getText().toString();
                String TextPassword = EditText_register_password.getText().toString();
                String TextReturnPassword = EditText_register_return_password.getText().toString();
                String TextPhone = EditText_register_phone.getText().toString();
                String TextDate = EditText_register_data.getText().toString();

                if (TextUtils.isEmpty(TextName)) {
                    Toast.makeText(RegisterMainActivity2.this,"Введіть дані у полі Ім'я",Toast.LENGTH_LONG).show();
                    EditText_register_name.setError("Ви ввели невірні дані у полі Ім'я");
                    EditText_register_name.requestFocus();
                } if (TextUtils.isEmpty(TextSurname)) {
                    Toast.makeText(RegisterMainActivity2.this,"Введіть дані у полі Прізвище",Toast.LENGTH_LONG).show();
                    EditText_register_surname.setError("Ви ввели невірні дані у полі Прізвище");
                    EditText_register_surname.requestFocus();
                } if (TextUtils.isEmpty(TextEmail)) {
                    Toast.makeText(RegisterMainActivity2.this,"Введіть дані у полі Email ",Toast.LENGTH_LONG).show();
                    EditText_register_email.setError("Ви ввели невірні дані у полі Email");
                } if (!Patterns.EMAIL_ADDRESS.matcher(TextEmail).matches()) {
                    Toast.makeText(RegisterMainActivity2.this,"Введіть повторно ваш Email",Toast.LENGTH_LONG).show();
                    EditText_register_email.setError("Пароль невалідний");
                    EditText_register_email.requestFocus();
                } if (TextUtils.isEmpty(TextPassword)) {
                    Toast.makeText(RegisterMainActivity2.this,"Введіть дані у полі пароль",Toast.LENGTH_LONG).show();
                    EditText_register_password.setError("Ви ввели невірні дані у полі пароль");
                    EditText_register_password.requestFocus();
                } if (TextPassword.length() < 5 ) {
                    Toast.makeText(RegisterMainActivity2.this,"Пароль повинен містити от 5 сімволів",Toast.LENGTH_LONG).show();
                    EditText_register_password.setError("Надто слабкий пароль");
                    EditText_register_password.requestFocus();
                } if (TextUtils.isEmpty(TextReturnPassword)) {
                    Toast.makeText(RegisterMainActivity2.this,"Введіть дані у полі повторно пароль",Toast.LENGTH_LONG).show();
                    EditText_register_return_password.setError("Ви ввели невірні дані у полі повторно пароль");
                    EditText_register_return_password.requestFocus();
                } if (TextReturnPassword.length() < 5 ) {
                    Toast.makeText(RegisterMainActivity2.this,"Повторно пароль повинен містит от 5 сімволів",Toast.LENGTH_LONG).show();
                    EditText_register_return_password.setError("Надто слабкий пароль");
                    EditText_register_return_password.requestFocus();
                } if (TextUtils.isEmpty(TextPhone)) {
                    Toast.makeText(RegisterMainActivity2.this,"Введіть дані у полі номер телефону",Toast.LENGTH_LONG).show();
                    EditText_register_phone.setError("Ви ввели невірні дані у полі номер телефону");
                    EditText_register_phone.requestFocus();
                } if (TextPhone.length() < 10) {
                    Toast.makeText(RegisterMainActivity2.this,"Номер телефону повинен містити до 10 символів",Toast.LENGTH_LONG).show();
                    EditText_register_phone.setError("Надто слабкий номер телефону");
                    EditText_register_phone.requestFocus();
                } if (TextUtils.isEmpty(TextDate)) {
                    Toast.makeText(RegisterMainActivity2.this, "Введіть дані у полі дата народження", Toast.LENGTH_LONG).show();
                    EditText_register_data.setError("Ви ввели невірні дані у полі дата народження");
                    EditText_register_data.requestFocus();
                    EditText_register_password.clearComposingText();
                    EditText_register_return_password.clearComposingText();
                } else {
                    registerUser(TextName,TextEmail,TextSurname,TextDate,TextPhone,TextPassword);
                }


            }
        });



    }

    private void registerUser(String textName, String textEmail, String textSurname, String textDate, String textPhone, String textPassword) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        //Create User Profile
        auth.createUserWithEmailAndPassword(textEmail,textPassword).addOnCompleteListener(RegisterMainActivity2.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    //Enter User Data into the Firebase Realtime Database.
                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textName,textDate,textSurname,textPhone);

                    //Extracting User reference  from Database for "Register User"
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Зареєстрований користувач");


                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                //Send Verification Email
                                firebaseUser.sendEmailVerification();

                                Toast.makeText(RegisterMainActivity2.this, "Користувач успішно зареєстрований Будь ласка перевірте ваш Email", Toast.LENGTH_SHORT).show();

                                //Open User Profile after successful registration
                                Intent intent = new Intent(RegisterMainActivity2.this,UserProfileMainActivity2.class);
                                //To Prevent User from returning back to Register Activity on pressing  back button after registration
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish(); //to close Register Activity
                            } else {
                                Toast.makeText(RegisterMainActivity2.this,"Помилка при реєстрації",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }


                        }
                    });


                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e ) {
                        EditText_register_password.setError("Ваш пароль є занадто слабким, Будь ласка використовуйте суміш алфавітів,цифр та спеціальних знаків");
                        EditText_register_password.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e ) {
                        EditText_register_password.setError("Ваш пароль не дійсний");
                        EditText_register_password.requestFocus();
                    } catch (FirebaseAuthUserCollisionException e ) {
                        EditText_register_password.setError("Користувач вже зареєстрований з цим Email,Використовувайте інший");
                        EditText_register_password.requestFocus();
                    } catch (Exception e ) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(RegisterMainActivity2.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        });

    }


}