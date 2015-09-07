package com.dijon.graffiti.android.activities;

import android.accounts.Account;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.dijon.graffiti.BuildConfig;
import com.dijon.graffiti.R;
import com.dijon.graffiti.android.fragments.LoginFragment;
import com.dijon.graffiti.network.GraffitiClient;
import com.dijon.graffiti.network.models.User;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.io.IOException;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = LoginActivity.class.getSimpleName();

    /* A reference to the Firebase */
    private Firebase mFirebaseRef;

    /* Data from the authenticated user */
    private AuthData mAuthData;

    /* Listener for Firebase session changes */
    private Firebase.AuthStateListener mAuthStateListener;

    // Request code used to invoke sign in user interactions.
    private static final int RC_SIGN_IN = 9001;
    // Client used to interact with Google APIs.
    private GoogleApiClient mGoogleApiClient;
    // Is there a ConnectionResult resolution in progress?
    private boolean mIsResolving = false;
    // Should we automatically resolve ConnectionResults when possible?
    private boolean mShouldResolve = false;

    // <editor-fold desc="Lifecycle">
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, LoginFragment.newInstance())
                    .commit();
        }
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);
        if (requestCode == RC_SIGN_IN) {
            // If the error resolution was not successful we should not resolve further.
            if (resultCode != RESULT_OK) {
                mShouldResolve = false;
            }
            mIsResolving = false;
            mGoogleApiClient.connect();
        }
    }
    // </editor-fold>

    // <editor-fold desc="LoginFragment.OnFragmentInteractionListener">
    @Override
    public void onGoogleButtonClicked() {
        // User clicked the sign-in button, so begin the sign-in process and automatically
        // attempt to resolve any errors that occur.
        mShouldResolve = true;
        mGoogleApiClient.connect();
        // TODO: Show a message to the user that we are signing in.
    }
    // </editor-fold>

    // <editor-fold desc="Helpers">
    private void init() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

        /* Create the Firebase ref that is used for all authentication with Firebase */
        mFirebaseRef = new Firebase(BuildConfig.API_URL);

        mAuthStateListener = new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {

            }
        };
        /* Check if the user is authenticated with Firebase already. If this is the case we can set the authenticated
         * user and hide hide any login buttons */
        mFirebaseRef.addAuthStateListener(mAuthStateListener);
    }

    private User createUserFromAuth(AuthData authData) {
        User user = new User();
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            // user.setBirthYear(currentPerson.getBirthday());
            switch (currentPerson.getGender()) {
                case 0:
                    user.setGender("male");
                    break;
                case 1:
                    user.setGender("female");
                    break;
                case 2:
                    user.setGender("other");
                    break;
            }
            String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
            user.setEmail(email);
        }
        Map<String, Object> providerData = authData.getProviderData();
        String displayName = (String) providerData.get("displayName");
        user.setFirstName(displayName.split(" ")[0]);
        user.setLastName(displayName.split(" ")[1]);
        user.setProfileImage((String) providerData.get("profileImageURL"));
        user.setPhoneManufacturer(Build.MANUFACTURER);
        user.setPhoneModel(Build.MODEL);
        user.setPhoneProduct(Build.PRODUCT);
        user.setPhoneVersion(Build.VERSION.RELEASE);
        user.setProvider(authData.getProvider());
        return user;
    }

    /**
     * Show errors to users
     */
    private void showErrorDialog(ConnectionResult connectionResult) {
        int errorCode = connectionResult.getErrorCode();

        if (GooglePlayServicesUtil.isUserRecoverableError(errorCode)) {
            // Show the default Google Play services error dialog which may still start an intent
            // on our behalf if the user can resolve the issue.
            GooglePlayServicesUtil.getErrorDialog(errorCode, this, RC_SIGN_IN,
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            mShouldResolve = false;
                            updateUI(false);
                        }
                    }).show();
        } else {
            // No default Google Play Services error, display a message to the user.
            String errorString = getString(R.string.play_services_error_fmt, errorCode);
            Toast.makeText(this, errorString, Toast.LENGTH_SHORT).show();

            mShouldResolve = false;
            updateUI(false);
        }
    }

    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            // Show signed-in user's name
            String name = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient).getDisplayName();
            Snackbar.make(findViewById(android.R.id.content), "Signed In: " + name, Snackbar.LENGTH_INDEFINITE)
                    .show();
        } else {
            // Show signed-out message
            Snackbar.make(findViewById(android.R.id.content), "Signed Out...", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }
    // </editor-fold>

    // <editor-fold desc="Google">

    // <editor-fold desc="GoogleApiClient.ConnectionCallbacks">
    @Override
    public void onConnected(Bundle bundle) {
        // onConnected indicates that an account was selected on the device, that the selected
        // account has granted any requested permissions to our app and that we were able to
        // establish a service connection to Google Play services.
        Log.d(TAG, "onConnected:" + bundle);
        mShouldResolve = false;

        // Show the signed-in UI
        updateUI(true);
        // Get the Oauth token from our user and post to Firebase
        new GetIdTokenTask().execute();
    }

    @Override
    public void onConnectionSuspended(int i) {
        // The connection to Google Play services was lost. The GoogleApiClient will automatically
        // attempt to re-connect. Any UI elements that depend on connection to Google APIs should
        // be hidden or disabled until onConnected is called again.
        Log.w(TAG, "onConnectionSuspended:" + i);
    }
    // </editor-fold>

    // <editor-fold desc="GoogleApiClient.ConnectionCallbacks">
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Could not connect to Google Play Services.  The user needs to select an account,
        // grant permissions or resolve an error in order to sign in. Refer to the javadoc for
        // ConnectionResult to see possible error codes.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);

        if (!mIsResolving && mShouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    Log.e(TAG, "Could not resolve ConnectionResult.", e);
                    mIsResolving = false;
                    mGoogleApiClient.connect();
                }
            } else {
                // Could not resolve the connection result, show the user an
                // error dialog.
                showErrorDialog(connectionResult);
            }
        } else {
            // Show the signed-out UI
            updateUI(false);
        }
    }
    // </editor-fold>
    // </editor-fold>

    /**
     * Utility class for authentication results
     */
    private class AuthResultHandler implements Firebase.AuthResultHandler {

        private final String provider;

        public AuthResultHandler(String provider) {
            this.provider = provider;
        }

        @Override
        public void onAuthenticated(AuthData authData) {
            User user = createUserFromAuth(authData);
            GraffitiClient.createOrUpdateUser(authData.getUid(), user, new Firebase.CompletionListener() {
                @Override
                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                    if (firebaseError != null) {
                        Log.i(TAG, "Data could not be saved. " + firebaseError.getMessage());
                    } else {
                        Log.i(TAG, "Data saved successfully.");
                    }
                }
            });
            Log.i(TAG, provider + " auth successful");
        }

        @Override
        public void onAuthenticationError(FirebaseError firebaseError) {
            Log.e(TAG, provider + " auth failure");
        }
    }

    private class GetIdTokenTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String accountName = Plus.AccountApi.getAccountName(mGoogleApiClient);
            Account account = new Account(accountName, GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
            // String scopes = "audience:server:client_id:" + SERVER_CLIENT_ID; // Not the app's client ID.
            String scopes = String.format("oauth2:%s", Scopes.PLUS_LOGIN);
            try {
                return GoogleAuthUtil.getToken(getApplicationContext(), account, scopes);
            } catch (IOException e) {
                Log.e(TAG, "Error retrieving ID token.", e);
                return null;
            } catch (GoogleAuthException e) {
                Log.e(TAG, "Error retrieving ID token.", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, "ID token: " + result);
            if (result != null) {
                // Successfully retrieved OAuth token, now login with Google
                mFirebaseRef.authWithOAuthToken("google", result, new AuthResultHandler("google"));
            } else {
                // There was some error getting the ID Token
                Log.e(TAG, "Error retrieving ID token.");
            }
        }

    }
}
