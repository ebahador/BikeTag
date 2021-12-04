package concordia.assignment1.btag.components;

public class User {
    private String firstName, lastName, email;
    private String fullName;
    private static long UserID;
    private long ID;

    public User(String firstName, String lastName, String email, long ID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email; // Main Key
        User.UserID = ID;
        this.ID = ID;
        this.fullName = firstName + " " + lastName;
    }

    public User() {
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public static long getID() {
        return UserID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
