package my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ncorti.slidetoact.SlideToActView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.DonorDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.MainActivity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;

public class SignUpDonorActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_id)
    EditText etID;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.link_to_acc)
    TextView link_to_acc;
    @BindView(R.id.cb_anonymous)
    CheckBox cb_anonymous;
    @BindView(R.id.btn_confirm)
    SlideToActView btn_confirm;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registration_donor);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btn_confirm.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NonNull SlideToActView slideToActView) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser() {
        progressBar.setVisibility(View.VISIBLE);
        String email, password;
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();

        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Registration successful!",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress bar
                            progressBar.setVisibility(View.GONE);

                            // if the user created intent to login activity
                            Intent intent
                                    = new Intent(SignUpDonorActivity.this,
                                    MainActivity.class);
                            startActivity(intent);
                            saveDonorData();
                        } else {

                            // Registration failed
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Registration failed!!"
                                            + " Please try again later",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress bar
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void saveDonorData() {
        DonorDataEntity donorDataEntity = new DonorDataEntity(etName.getText().toString(), etEmail.getText().toString(), cb_anonymous.isChecked(), etPhone.getText().toString());

        CollectionReference dbDonor = db.collection("donorDetails");
        dbDonor.add(donorDataEntity).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(SignUpDonorActivity.this, "Data added into Firebase Firestore successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpDonorActivity.this, "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
