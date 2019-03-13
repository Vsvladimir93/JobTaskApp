package com.experience.jobtaskapp.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.experience.jobtaskapp.Net;
import com.experience.jobtaskapp.OnResponseListener;
import com.experience.jobtaskapp.R;
import com.experience.jobtaskapp.User;
import org.json.JSONObject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthActivity extends AppCompatActivity {

    private final static String LOG_TAG = AuthActivity.class.getSimpleName();

    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_email)
    TextView userEmail;
    @BindView(R.id.user_activity)
    TextView userActivity;
    @BindView(R.id.user_age)
    TextView userAge;
    @BindView(R.id.user_image)
    ImageView userImage;

    private SignInDialog signInDialog;
    private SignUpDialog signUpDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.dial_in_btn, R.id.dial_up_btn})
    public void onClick(Button button) {
        switch (button.getId()) {
            case R.id.dial_in_btn:
                signInDialog = new SignInDialog(this);
                signInDialog.show();
                signInDialog.signInBtn.setOnClickListener(v -> signIn());
                break;
            case R.id.dial_up_btn:
                signUpDialog = new SignUpDialog(this);
                signUpDialog.show();
                signUpDialog.signUpBtn.setOnClickListener(v -> signUp());
                break;
        }
    }

    private void signIn() {
        if (signInDialog.validateFormSignIn()) {
            Net.signInRequest(this,
                    signInDialog.nameEdtxIn.getText().toString(),
                    signInDialog.passEdtxIn.getText().toString(),
                    response -> {
                        handleSignInResponse(response);
                    });
            signInDialog.hide();
        }
    }

    private void signUp() {
        if (signUpDialog.validateFormSignUp()) {
            Net.signUpRequest(AuthActivity.this,
                    signUpDialog.nameEdtxUp.getText().toString(),
                    signUpDialog.emailEdtxUp.getText().toString(),
                    signUpDialog.passEdtxUp.getText().toString(),
                    response -> handleSignUpResponse(response)
            );
            signUpDialog.hide();
        }
    }

    private void handleSignInResponse(JSONObject jsonObject) {
        try {
            if (jsonObject.getString("error").equals("")) {
                Toast.makeText(this, jsonObject.getString("success"), Toast.LENGTH_LONG).show();
                Net.requestUserData(this, new OnResponseListener<User>() {
                    @Override
                    public void onResponse(User user) {
                        fillUserProfile(user);
                    }
                });
            } else {
                Toast.makeText(this, jsonObject.getString("error"), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.d(LOG_TAG, e.getMessage());
        }
    }

    private void handleSignUpResponse(String state) {
        if (state.equals("200")) {
            Toast.makeText(this, "Регистрация прошла успешно", Toast.LENGTH_LONG).show();
            Net.requestUserData(this, new OnResponseListener<User>() {
                @Override
                public void onResponse(User response) {
                    fillUserProfile(response);
                }
            });
        } else {
            Toast.makeText(this, "Ошибка регистрации", Toast.LENGTH_LONG).show();
        }
    }

    private void fillUserProfile(User user) {
        Glide.with(userImage).load(user.getImageUrl()).into(userImage);
        userName.setText(user.getName());
        userEmail.setText(user.getEmail());
        userActivity.setText(user.getActivity());
        userAge.setText(String.format("%s %s", "Возраст", user.getAge()));
    }

}
