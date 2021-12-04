package concordia.assignment1.btag;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import concordia.assignment1.btag.observerPattern.Observer;
import concordia.assignment1.btag.observerPattern.Subject;
import concordia.assignment1.btag.testModel.TestStates;
import concordia.assignment1.btag.ui.Authorization.ExitHandler;
import concordia.assignment1.btag.ui.Authorization.Login;
import concordia.assignment1.btag.ui.Monitoring;

public class MainActivity extends AppCompatActivity {

    private final TestStates testStates = new TestStates();
    private final Random random = new Random();
    private double probability;
    private boolean FLAG = true;

    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final String currentUserID = Objects.requireNonNull(user).getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckBox thiefCheck = findViewById(R.id.antiThief);
        TextView sessionEmail = findViewById(R.id.activeSession);
        TextView loginDate = findViewById(R.id.dateTxt);
        TextView subscription = findViewById(R.id.subs);
        Button logout = findViewById(R.id.logOut);
        Button isMoved = findViewById(R.id.isMoved);
        Button allInfo = findViewById(R.id.showInfo);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        sessionEmail.setText(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail());
        long time = Objects.requireNonNull(mAuth.getCurrentUser().getMetadata()).getLastSignInTimestamp();

        FirebaseDatabase.getInstance().getReference("SUBSCRIBE").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                thiefCheck.setChecked(snapshot.hasChild(currentUserID));
                subscription.setText("Subscription Status: " + snapshot.hasChild(currentUserID));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Subject subject = new Subject() {
            @Override
            public void notifySubscribers(boolean state) {
                Toast.makeText(getApplicationContext(),
                        "Subscription is " + state,
                        Toast.LENGTH_SHORT).show();
            }
        };

        Observer observer = new Observer() {
            @Override
            public void update() {
                subject.setState(true);
                FirebaseDatabase.getInstance().getReference("SUBSCRIBE").addValueEventListener(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (subject.isState()) {
                            if (snapshot.hasChild(currentUserID)) {
                                FirebaseDatabase.getInstance().getReference("SUBSCRIBE").
                                        child(currentUserID).removeValue();
                                subject.notifySubscribers(false);

                            } else {
                                FirebaseDatabase.getInstance().getReference("SUBSCRIBE").
                                        child(currentUserID).setValue(true);
                                subject.notifySubscribers(true);

                            }
                            subject.setState(false);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        };

        loginDate.setText(String.valueOf(convertTime(time)));

        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        });

        allInfo.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, Monitoring.class);
            i.putExtra("FLAG", FLAG);
            startActivity(i);
            finish();
        });

        thiefCheck.setOnClickListener(v -> {
            if (thiefCheck.isChecked()) {
                isMoved.setContextClickable(true);
                observer.update();
                probability = random.nextDouble();
            } else {
                isMoved.setContextClickable(false);
                observer.update();
            }
        });

        isMoved.setOnClickListener(v -> {

            if (probability > 0.7) {
                FLAG = compare(testStates.aggregateDef(), testStates.aggregate2());
                Intent i = new Intent(MainActivity.this, Monitoring.class);
                i.putExtra("FLAG", FLAG);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(false);
                builder.setTitle("ALARM\nYour bike is moved!");
                builder.setPositiveButton("OK", (dialog, item) -> dialog.dismiss());
                builder.setNegativeButton("See details", (dialog, item) ->
                        startActivity(i));
                builder.setIcon(R.drawable.warning);
                builder.show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Your bike is Stable ",
                        Toast.LENGTH_SHORT).show();
            }

        });
    }

    public String convertTime(long time) {
        Date date = new Date(time);
        @SuppressLint("SimpleDateFormat")
        Format format = new SimpleDateFormat("yyyy/MM/dd\nHH:mm:ss");
        return format.format(date);
    }

    public static boolean compare(List t1, List t2) {
        return t1.equals(t2);
    }

    @Override
    public void onBackPressed() {
        ExitHandler.exitDialog(MainActivity.this);
    }

}