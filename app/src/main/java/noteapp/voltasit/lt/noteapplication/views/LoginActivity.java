package noteapp.voltasit.lt.noteapplication.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

import noteapp.voltasit.lt.noteapplication.MainActivity;
import noteapp.voltasit.lt.noteapplication.R;
import noteapp.voltasit.lt.noteapplication.services.UserService;

/**
 * Created by Romas Noreika on 2017-12-17.
 */

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {


    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

    private SignInButton btnSignIn;
    private Button btnLogin, btnLinkToRegisterScreen;
    private EditText inputPassword, inputEmail;
    private TextView loginErrorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("NoteAppId")
                .clientKey("NoteAppClientKey")
                .server("https://voltas-parse-server.herokuapp.com/parse/")
                .build()
        );

//        Parse.enableLocalDatastore(this);
//        ParseUser currentUser = ParseUser.getCurrentUser();
//        if (currentUser != null) {
//            loadNotesView();
//        }

        setContentView(R.layout.activity_login);

        btnSignIn = findViewById(R.id.btn_sign_in);
        btnLogin = findViewById(R.id.btnLogin);
        btnLinkToRegisterScreen = findViewById(R.id.btnLinkToRegisterScreen);

        inputEmail = findViewById(R.id.loginEmail);
        inputPassword = findViewById(R.id.loginPassword);

        loginErrorMsg = (TextView) findViewById(R.id.login_error);

        btnSignIn.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnLinkToRegisterScreen.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Customizing G+ button
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());
    }

    private void loadNotesView() {
        Intent myIntent = new Intent(this, NotesView.class);
        startActivityForResult(myIntent, 0);
        finish();
    }


    private void signInWithGmail() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "Connected:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount user = result.getSignInAccount();
            saveUser(user.getDisplayName(), user.getEmail());
            loadNotesView();
        }
    }

    private void saveUser(String name, String email) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserParse");
        query.whereEqualTo("email", email);
        query.whereEqualTo("userName", name);

        try {
            List<ParseObject> parseObjects = query.find();
            if (parseObjects.isEmpty()) {
                ParseObject user = new ParseObject("UserParse");
                user.put("userName", name);
                user.put("email", email);
                user.saveInBackground(new SaveCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.i("User", "Save Succeeded");
                        } else {
                            Log.i("User", "Save Failed");
                            e.getMessage();
                        }
                    }
                });
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


//        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserParse");
//        try {
//            List<ParseObject> parseObjects = query.find();
//            if(!parseObjects.isEmpty()){
//                for (ParseObject parseObject : parseObjects) {
//                    if (!parseObject.get("email").equals(email) && !parseObject.get("userName").equals(name)) {
//
//                    }
//                }
//            }
//            for (ParseObject parseObject : parseObjects) {
//                if (parseObject.get("email").equals(email) && parseObject.get("userName").equals(name)) {
//                    return false;
//                } else {
//                    ParseObject user = new ParseObject("UserParse");
//                    user.put("userName", name);
//                    user.put("email", email);
////        user.saveInBackground();
//                    user.saveInBackground(new SaveCallback() {
//                        public void done(ParseException e) {
//                            if (e == null) {
//                                Log.i("User", "Save Succeeded");
//                            } else {
//                                Log.i("User", "Save Failed");
//                                e.getMessage();
//                            }
//                        }
//                    });
//                }
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return true;

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btn_sign_in:
                signInWithGmail();
                break;

            case R.id.btnLogin:
                sigIn();
                break;

            case R.id.btnLinkToRegisterScreen:
                register();
                break;
        }
    }

    private void register() {

    }

    private void sigIn() {
        if ((!inputEmail.getText().toString().equals(""))
                && (!inputPassword.getText().toString().equals(""))) {

            saveUser(inputEmail.getText().toString(), inputPassword.getText().toString());
            Intent myIntent = new Intent(this, NotesView.class);
            startActivityForResult(myIntent, 0);
            finish();

        } else if ((!inputEmail.getText().toString().equals(""))) {
            Toast.makeText(getApplicationContext(), "Password field empty", Toast.LENGTH_SHORT).show();
        } else if ((!inputPassword.getText().toString().equals(""))) {
            Toast.makeText(getApplicationContext(), "Email field empty", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Email and Password field are empty", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
//            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

//        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            btnSignIn.setVisibility(View.GONE);
//            btnSignOut.setVisibility(View.VISIBLE);
//            btnRevokeAccess.setVisibility(View.VISIBLE);
//            llProfileLayout.setVisibility(View.VISIBLE);
        } else {
            btnSignIn.setVisibility(View.VISIBLE);
//            btnSignOut.setVisibility(View.VISIBLE);
//            btnRevokeAccess.setVisibility(View.VISIBLE);
//            llProfileLayout.setVisibility(View.VISIBLE);
        }
    }

}