package com.example.whatsappclone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.whatsappclone.Models.User;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnPaidEventListener;
import com.google.android.gms.ads.ResponseInfo;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class SignIn extends AppCompatActivity {
    EditText email,password;
    TextView signUp;
    Button signIn,fbLogin,googleLogin;
    private GoogleSignInClient mGoogleSignInClient;
    private final int RC_SIGN_IN = 1;
    FirebaseAuth auth;
    DatabaseReference myRef;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));

        getSupportActionBar().hide();
        email = findViewById(R.id.EmailLogin);
        password = findViewById(R.id.PasswordLogin);
        signIn = findViewById(R.id.signinBtn);
        signUp = findViewById(R.id.signupRedirect);
        fbLogin = findViewById(R.id.fbBtnLogin);
        googleLogin= findViewById(R.id.googleBtnLogin);
        auth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();

        //Loading banner ad
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
                Toast.makeText(SignIn.this, "Ad was Clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                super.onAdFailedToLoad(adError);
            }

            @Override
            public void onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Toast.makeText(SignIn.this, "Ad is loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        });



        // Configure sign-in to request the user's ID, email address, and basic under details.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        //Email Log in
        signIn.setOnClickListener(v -> {
            if(TextUtils.isEmpty(email.getText().toString())){
                email.setError("Enter your Email");
            }
            if(TextUtils.isEmpty(password.getText().toString())){
                password.setError("Enter your Password");
            }

            auth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    startActivity(new Intent(SignIn.this,MainActivity.class));
                }
            }).addOnFailureListener(e -> Toast.makeText(SignIn.this, "Email or Password is incorrect", Toast.LENGTH_SHORT).show());
        });

        signUp.setOnClickListener(v -> {startActivity(new Intent(SignIn.this, SignUp.class));});

        //Google Log in
        googleLogin.setOnClickListener(v -> LogIn());
    }

    private void LogIn() {
            Intent intent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(intent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask)
    {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            FirebaseGoogleAuth(account);
        } catch (ApiException e) {
            Toast.makeText(SignIn.this,"Sign In Failed",Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }

    }

    private void FirebaseGoogleAuth(GoogleSignInAccount account)
    {
        //check if account is null
        if (account != null)
        {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            auth.signInWithCredential(authCredential).addOnCompleteListener(this, task -> {
                if (task.isSuccessful())
                {
                    Toast.makeText(SignIn.this, "Authentication Successful", Toast.LENGTH_SHORT).show();
                    User users = new User();
                    String id = auth.getUid();
                    users.setuName(account.getDisplayName());
                    users.setProfilePic(Objects.requireNonNull(account.getPhotoUrl()).toString());
                    users.setuId(id);
                    myRef.child("Users").child(id).setValue(users);
                    startActivity(new Intent(SignIn.this, MainActivity.class));
                    finish();

                } else {
                    Toast.makeText(SignIn.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(SignIn.this, "acc failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null){

            startActivity(new Intent(SignIn.this,MainActivity.class));
        }
    }
}