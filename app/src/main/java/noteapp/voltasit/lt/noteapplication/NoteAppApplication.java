package noteapp.voltasit.lt.noteapplication;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by Romas Noreika on 2017-12-17.
 */

public class NoteAppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

//        Parse.initialize(this, "NoteAppId", "NoteAppKey");

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("NoteAppId")
                .server("http://localhost:1337/parse/")
                .build()
        );
        ParseObject testObject = new ParseObject("Role");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
    }
}
