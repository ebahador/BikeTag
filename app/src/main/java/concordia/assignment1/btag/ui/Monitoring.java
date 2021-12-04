package concordia.assignment1.btag.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import concordia.assignment1.btag.MainActivity;
import concordia.assignment1.btag.R;
import concordia.assignment1.btag.components.Bike;
import concordia.assignment1.btag.components.User;
import concordia.assignment1.btag.testModel.State;

public class Monitoring extends AppCompatActivity {

    TextView firstName, lastName, email, bikeModel, bikeSize,
            latitude, longitude,
            x, y, z, orientation, velocity;
    Button map;

    private User user = null;
    private Bike bike = null;
    Query findName, findBikeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);

        // SELECT email FROM USER_INFO WHERE email IS EQUAL TO CURRENT_USER_EMAIL
        findName = FirebaseDatabase.getInstance().getReference().
                child("USER_INFO").
                orderByChild("email").
                equalTo(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail());

        findBikeInfo = FirebaseDatabase.getInstance().getReference().
                child("BIKE_INFO").
                orderByChild("ownerEmail").
                equalTo(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail());

        firstName = findViewById(R.id.firstNameTxt);
        lastName = findViewById(R.id.lastNameTxt);
        email = findViewById(R.id.email);

        bikeModel = findViewById(R.id.bikeModelTxt);
        bikeSize = findViewById(R.id.bikeSizeTxt);

        latitude = findViewById(R.id.latitudeTxt);
        longitude = findViewById(R.id.longitudeTxt);

        x = findViewById(R.id.xInfo);
        y = findViewById(R.id.yInfo);
        z = findViewById(R.id.zInfo);
        orientation = findViewById(R.id.orieInfo);

        velocity = findViewById(R.id.velocityInfo);
        map = findViewById(R.id.viewMap);

        findName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<User> userInfo = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        user = snapshot.getValue(User.class);
                        userInfo.add(user);
                    }
                    firstName.setText(user.getFirstName());
                    lastName.setText(user.getLastName());
                    email.setText(user.getEmail());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        findBikeInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Bike> bikeInfo = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        bike = snapshot.getValue(Bike.class);
                        bikeInfo.add(bike);
                    }
                    bikeModel.setText(bike.getBikeModel());
                    bikeSize.setText(String.valueOf(bike.getSize()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        boolean FLAG = getIntent().getExtras().getBoolean("FLAG");
        if (FLAG) {
            latitude.setText(String.valueOf(State.LAT1.getStateVal()));
            longitude.setText(String.valueOf(State.LONG1.getStateVal()));

            x.setText(String.valueOf(State.X1.getStateVal()));
            y.setText(String.valueOf(State.Y1.getStateVal()));
            z.setText(String.valueOf(State.Z1.getStateVal()));
            orientation.setText(String.valueOf(State.HORIZONTAL));
            velocity.setText(String.valueOf(State.VELO1.getStateVal()));

            map.setOnClickListener(v -> {
                String labelLocation = "BIKE";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse
                        ("geo:<" +
                                latitude.getText().toString() + ">,<" +
                                longitude.getText().toString() + ">?q=<" +
                                latitude.getText().toString() + ">,<" +
                                longitude.getText().toString() + ">(" +
                                labelLocation + ")"));
                startActivity(intent);
            });
        } else {
            latitude.setText(String.valueOf(State.LAT2.getStateVal()));
            longitude.setText(String.valueOf(State.LONG2.getStateVal()));

            x.setText(String.valueOf(State.X2.getStateVal()));
            y.setText(String.valueOf(State.Y2.getStateVal()));
            z.setText(String.valueOf(State.Z2.getStateVal()));
            orientation.setText(String.valueOf(State.HORIZONTAL));
            velocity.setText(String.valueOf(State.VELO2.getStateVal()));

            map.setOnClickListener(v -> {
                String labelLocation = "BIKE";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse
                        ("geo:<" +
                                latitude.getText().toString() + ">,<" +
                                longitude.getText().toString() + ">?q=<" +
                                latitude.getText().toString() + ">,<" +
                                longitude.getText().toString() + ">(" +
                                labelLocation + ")"));
                startActivity(intent);
            });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Monitoring.this, MainActivity.class));
        finish();
    }
}