package com.example.meds;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Objects;



public class SignupActivity extends AppCompatActivity {

    private DatabaseReference databaseRef;
    private TextInputEditText Phone;
    //private TextInputLayout Username;
    private TextInputLayout Password;
    private ProgressDialog RegisterProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singup);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        Phone = findViewById(R.id.phone);
        TextInputEditText countryCode = findViewById(R.id.CountryCode);
        Password = findViewById(R.id.password);
        Button registerBtn = findViewById(R.id.signup_btn);

        RegisterProgress = new ProgressDialog(this);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                String phone = Objects.requireNonNull(Objects.requireNonNull(Phone).getText()).toString();
                //String username = Objects.requireNonNull(Username.getEditText()).getText().toString();
                String password = Objects.requireNonNull(Password.getEditText()).getText().toString();
                String countryCode = "+65";

                if (TextUtils.isEmpty(phone)) Phone.setError("Phone cannot be empty");
                else if (TextUtils.isEmpty(password)) Password.setError("Password cannot be empty");
                else if (!isPhoneValid(phone)) Phone.setError("Phone is invalid");
                else {
                    RegisterProgress.setTitle(R.string.registering);
                    RegisterProgress.setCanceledOnTouchOutside(false);
                    RegisterProgress.show();
                    //registerNewUser(phone, password);//username, password);
                    phone = countryCode + phone;
                    Intent ver_Intent = new Intent(SignupActivity.this, VerificationActivity.class);
                    ver_Intent.putExtra("phoneNumber", phone);
                    startActivity(ver_Intent);
                }

            }
        });
    }

    private boolean isPhoneValid(String phone) {
        return (phone.length() == 8) && (phone.charAt(0) == '9' || phone.charAt(0) == '8');
    }
}