package com.experience.jobtaskapp.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import com.experience.jobtaskapp.R;
import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpDialog extends Dialog {

    @BindView(R.id.name_edtx_up)
    EditText nameEdtxUp;
    @BindView(R.id.email_edtx_up)
    EditText emailEdtxUp;
    @BindView(R.id.pass_edtx_up)
    EditText passEdtxUp;
    @BindView(R.id.sign_up_btn)
    Button signUpBtn;

    public SignUpDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_dialog);
        setTitle(R.string.sign_up);
        ButterKnife.bind(this);
    }

    public boolean validateFormSignUp() {

        boolean validate = true;

        String name = nameEdtxUp.getText().toString();
        if (TextUtils.isEmpty(name)) {
            nameEdtxUp.setError("Нужно заполнить");
            validate = false;
        }
        if (name.length() > 30) {
            nameEdtxUp.setError("Допустимая длинна 30 символов");
            validate = false;
        }

        String email = emailEdtxUp.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailEdtxUp.setError("Нужно заполнить");
            validate = false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEdtxUp.setError("Не соответствует форме E-mail");
            validate = false;
        }

        String password = passEdtxUp.getText().toString();
        if (password.length() < 6) {
            passEdtxUp.setError("Необходимо минимум 6 символов");
            validate = false;
        }
        if (password.length() > 15) {
            passEdtxUp.setError("Допустимая длинна 15 символов");
            validate = false;
        }

        return validate;
    }
}
