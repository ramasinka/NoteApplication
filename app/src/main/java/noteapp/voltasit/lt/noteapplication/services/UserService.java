package noteapp.voltasit.lt.noteapplication.services;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import noteapp.voltasit.lt.noteapplication.model.User;

/**
 * Created by CodeAcademy on 2017.12.18.
 */

public class UserService {

    public User saveUser (User user){

        ParseObject gameScore = new ParseObject("User");
        gameScore.put("email", 1337);
        gameScore.put("userName", "Sean Plott");
        gameScore.saveInBackground();
        gameScore.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Log.i("Parse", "Save Succeeded");
                } else {
                    Log.i("Parse", "Save Failed");
                    e.getMessage();
                }
            }
        });
        return user;
    }

    public boolean checkIfUserExist(String email, String password) {
        return false;
    }
}
