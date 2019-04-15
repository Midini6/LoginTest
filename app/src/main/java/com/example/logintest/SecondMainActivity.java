package com.example.logintest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SecondMainActivity extends AppCompatActivity {

    // TAG is for show some tag logs in LOG screen.
    public static final String TAG = "SecondMainActivity";
    // Request sing in code. Could be anything as you required.
    public static final int RequestSignInCode = 7;
    // Firebase Auth Object.
    public FirebaseAuth firebaseAuth;
    // Google API Client object.
    public GoogleApiClient googleApiClient;

    // Sing out button.
    Button SignOutButton;

    Button GoogleLogoutButton;

    // Google Sign In button .
    com.google.android.gms.common.SignInButton signInButton;

    // TextView to Show Login User Email and Name.
    TextView LoginUserName, LoginUserEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        SignOutButton = (Button) findViewById(R.id.sign_out);
        GoogleLogoutButton = (Button) findViewById(R.id.google_logout);
        LoginUserName = (TextView) findViewById(R.id.textViewName);
        LoginUserEmail = (TextView) findViewById(R.id.textViewEmail);

        // Getting Firebase Auth Instance into firebaseAuth object.
        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();


        //유저가 있다면, null이 아니면 계속 진행
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //textViewUserEmail의 내용을 변경해 준다.
        LoginUserName.setText("반갑습니다." + user.getDisplayName() + "님\n");
        LoginUserEmail.setText(user.getEmail() + "으로 로그인 하였습니다.");


        // Adding Click Listener to User Sign Out button.
        SignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //firebase 계정 삭제
                //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //user.delete();

                FirebaseAuth.getInstance().signOut();

                //유저가 로그인 하지 않은 상태라면 null 상태이고 이 액티비티를 종료하고 로그인 액티비티를 연다.
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    Toast.makeText(SecondMainActivity.this, "로그아웃!", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(SecondMainActivity.this, MainActivity.class));
                }
            }
        });

        GoogleLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                // ...
                                Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),SecondMainActivity.class);
                                startActivity(intent);
                            }
                        });
            }
        });
    }
}