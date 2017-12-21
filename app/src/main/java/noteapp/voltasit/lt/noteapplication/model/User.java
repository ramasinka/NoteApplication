package noteapp.voltasit.lt.noteapplication.model;

/**
 * Created by CodeAcademy on 2017.12.18.
 */

public class User {
    private long id;
    private String userName;
    private String password;
    private boolean emailVerified;
    private String email;


    public User(String userName, String email, String password) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public User() {
    }

    public User(String name, String email) {
        this.userName = name;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
}
