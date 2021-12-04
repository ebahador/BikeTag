package concordia.assignment1.btag.components;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class Bike implements Observer {

    private String bikeModel;
    private String ownerEmail;
    private int size;
    private long userID;
    User userEmail = new User();

    public Bike(String bikeModel, int size) {
        this.bikeModel = bikeModel;
        this.size = size;
        this.userID = User.getID(); // Primary Key
        this.ownerEmail = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
        // Foreign Key
    }

    public Bike() {
    }
    
    public String getBikeModel() {
        return bikeModel;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public int getSize() {
        return size;
    }

    public long getUserID() {
        return userID;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}