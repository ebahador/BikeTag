package concordia.assignment1.btag.ui.Authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import concordia.assignment1.btag.MainActivity;
import concordia.assignment1.btag.R;

public class Login extends AppCompatActivity {

    private EditText email, password;

    private FirebaseAuth mAuth;
    private String userEmail, userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);

        Button login = findViewById(R.id.loginBtn);

        TextView goToRegister = findViewById(R.id.goToRegister);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }

        login.setOnClickListener(v -> {
            userEmail = email.getText().toString().trim();
            userPassword = password.getText().toString().trim();
            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(getApplicationContext(),
                        "Username or Password is incorrect!\nPlease fill the boxes correctly.",
                        Toast.LENGTH_SHORT).show();
            } else {
                mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),
                                "Login successful!",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, MainActivity.class));
                    }
                });
            }
        });
        goToRegister.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, Register.class));
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        ExitHandler.exitDialog(Login.this);
    }
}