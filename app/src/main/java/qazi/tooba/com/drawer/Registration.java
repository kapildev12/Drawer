package qazi.tooba.com.drawer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import qazi.tooba.com.drawer.Common.Common;
import qazi.tooba.com.drawer.Model.Rider;


/**
 * Created by Qazi on 10/29/2017.
 */


public class Registration extends AppCompatActivity implements View.OnClickListener {
    Button start;
    EditText reg_phone, reg_password, reg_email, reg_name, reg_allergies, medication, GaurName, GaurPhone, relations, DOB, BloodGroup, confirmPass;
    RadioButton gender;
    DatabaseReference users;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        reg_email = (EditText) findViewById(R.id.email);
        reg_password = (EditText) findViewById(R.id.pass);
        confirmPass = (EditText) findViewById(R.id.confirmPass);
        reg_phone = (EditText) findViewById(R.id.phoneNo);
        reg_name = (EditText) findViewById(R.id.name);
        reg_allergies = (EditText) findViewById(R.id.Allergies);
        medication = (EditText) findViewById(R.id.medication);
        GaurName = (EditText) findViewById(R.id.GuarName);
        GaurPhone = (EditText) findViewById(R.id.GaudPhone);
        relations = (EditText) findViewById(R.id.Relations);
        //  gender= (RadioButton)findViewById(R.id.gend);
        DOB = (EditText) findViewById(R.id.DOB);
        BloodGroup = (EditText) findViewById(R.id.BloodGroup);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference(Common.user_rider);
        findViewById(R.id.signUp).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.signUp) {
            registerUser();
        }
    }

    private void registerUser() {
        final String phone = reg_email.getText().toString();
        final String password = reg_password.getText().toString();
        final String confirmPassword = confirmPass.getText().toString();
        final String number = reg_phone.getText().toString();

        if (TextUtils.isEmpty(reg_name.getText().toString())) {
            Toast.makeText(Registration.this, "Enter Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(reg_email.getText().toString())) {
            Toast.makeText(Registration.this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Common.isEmailValid(phone)) {
            Toast.makeText(Registration.this, "Invalid E-mail Format", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(Registration.this, "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(Registration.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TextUtils.equals(password, confirmPassword)) {
            Toast.makeText(Registration.this, "Password and Confirm password did not match!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(reg_phone.getText().toString())) {
            Toast.makeText(Registration.this, "Enter Phone no", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!number.startsWith("03")) {
            Toast.makeText(Registration.this, "Number must be in Pakistani Format", Toast.LENGTH_SHORT).show();
            return;
        }
        if (number.length() < 11) {
            Toast.makeText(Registration.this, "Number is too short", Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog dialog = new ProgressDialog(Registration.this);
        dialog.setMessage("Signing up...");
        dialog.show();

        mAuth.createUserWithEmailAndPassword(reg_email.getText().toString(), reg_password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Rider rider = new Rider();
                rider.setName(reg_name.getText().toString());
                rider.setEmail(reg_email.getText().toString());
                rider.setPassword(reg_password.getText().toString());
                rider.setAllergies(reg_allergies.getText().toString());
                rider.setBloodGroup(BloodGroup.getText().toString());
                rider.setDOB(DOB.getText().toString());
                rider.setPhone(reg_phone.getText().toString());
                rider.setGaurName(GaurName.getText().toString());
                rider.setRelations(relations.getText().toString());
                rider.setGaurPhone(GaurPhone.getText().toString());
                rider.setMedication(medication.getText().toString());

                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(rider).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Registration.this, "Sign up Successful", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Registration.this, "Not Successful" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.cancel();

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Registration.this, "Not Successful" + e.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

    }
}
