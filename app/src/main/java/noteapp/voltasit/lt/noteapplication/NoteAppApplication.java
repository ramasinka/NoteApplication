package noteapp.voltasit.lt.noteapplication;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by Romas Noreika on 2017-12-17.
 */

public class NoteAppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

//        Parse.enableLocalDatastore(this);
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("NoteAppId")
                .clientKey("NoteAppClientKey")
                .server("https://voltas-parse-server.herokuapp.com/parse/")
                .build()
        );


//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("Test", "test");
//        testObject.saveInBackground();
//        testObject.saveInBackground(new SaveCallback() {
//            public void done(ParseException e) {
//                if (e == null) {
//                    Log.i("Parse", "Save Succeeded");
//                } else {
//                    Log.i("Parse", "Save Failed");
//                    e.getMessage();
//                }
//            }
//        });

//        ParseUser.enableAutomaticUser();
//        ParseACL defaultACL = new ParseACL();
//        defaultACL.setPublicReadAccess(true);
//        defaultACL.setPublicWriteAccess(true);
//        ParseACL.setDefaultACL(defaultACL, true);


    }
}
