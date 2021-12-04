package concordia.assignment1.btag.ui.Authorization;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import concordia.assignment1.btag.MainActivity;
import concordia.assignment1.btag.R;
import concordia.assignment1.btag.components.Bike;
import concordia.assignment1.btag.components.User;

public class Register extends AppCompatActivity {

    private EditText firstName, lastName, email, password, bikeModel, bikeSize, tagID;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReferenceUSER, mDatabaseReferenceBIKE;
    private long mixId = 0;

    private String userFirstName, userLastName, userEmail,
            userPassword, userBikeModel, userBikeSize, userBikeTagID;

    private User user;
    private Bike bike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // UI Object Definition
        firstName = findViewById(R.id.firstNameTxtReg);
        lastName = findViewById(R.id.lastNameTxtReg);
        email = findViewById(R.id.emailTxtReg);
        password = findViewById(R.id.passwordTxtReg);
        bikeModel = findViewById(R.id.bikeModelTxtReg);
        bikeSize = findViewById(R.id.bikeSizeTxtReg);
        tagID = findViewById(R.id.bikeTagIdTxtReg);

        Button register = findViewById(R.id.registerBtn);

        TextView goToLogin = findViewById(R.id.goToLogin);


        // Defining Google Firebase
        mDatabaseReferenceUSER = FirebaseDatabase.getInstance().getReference().child("USER_INFO");
        mDatabaseReferenceBIKE = FirebaseDatabase.getInstance().getReference().child("BIKE_INFO");

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(Register.this, MainActivity.class));
            finish();
        }

        mDatabaseReferenceUSER.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Creating Incremental ID on FireBase
                mixId = (snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabaseReferenceBIKE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mixId = (snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        register.setOnClickListener(v -> {
            userFirstName = firstName.getText().toString().trim();
            userLastName = lastName.getText().toString().trim();
            userEmail = email.getText().toString().trim();
            userPassword = password.getText().toString().trim();
            userBikeModel = bikeModel.getText().toString().trim();
            userBikeSize = bikeSize.getText().toString().trim();
            userBikeTagID = tagID.getText().toString().trim();

            if (userFirstName.isEmpty() || userLastName.isEmpty() || userEmail.isEmpty()
                    || userPassword.isEmpty() || userBikeModel.isEmpty() || userBikeSize.isEmpty()
                    || userBikeTagID.isEmpty())

                Toast.makeText(getApplicationContext(),
                        "Please fill the boxes.",
                        Toast.LENGTH_SHORT).show();
            else {
                mAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user = new User(userFirstName, userLastName, userEmail, mixId + 1);
                        bike = new Bike(userBikeModel, Integer.parseInt(userBikeSize));

                        mDatabaseReferenceUSER.child(String.valueOf(mixId + 1)).setValue(user);
                        mDatabaseReferenceBIKE.child(String.valueOf(mixId + 1)).setValue(bike);

                        Toast.makeText(getApplicationContext(),
                                "Registration Successful.",
                                Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(Register.this, MainActivity.class));
                    } else
                        Toast.makeText(getApplicationContext(),
                                "Error.",
                                Toast.LENGTH_SHORT).show();

                });
            }
        });

        goToLogin.setOnClickListener(v -> {
            startActivity(new Intent(Register.this, Login.class));
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        ExitHandler.exitDialog(Register.this);
    }
}