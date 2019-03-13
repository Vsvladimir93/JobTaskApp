package com.experience.jobtaskapp.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.experience.jobtaskapp.R;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInDialog extends Dialog {

    @BindView(R.id.name_edtx_in)
    EditText nameEdtxIn;
    @BindView(R.id.pass_edtx_in)
    EditText passEdtxIn;
    @BindView(R.id.sign_in_btn)
    Button signInBtn;

    public SignInDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_dialog);
        setTitle(R.string.sign_in);
        ButterKnife.bind(this);
    }

    public boolean validateFormSignIn() {

        boolean validate = true;

        String name = nameEdtxIn.getText().toString();
        if (TextUtils.isEmpty(name)) {
            nameEdtxIn.setError("Нужно заполнить");
            validate = false;
        } else if (name.length() > 30) {
            nameEdtxIn.setError("Допустимая длинна 30 символов");
            validate = false;
        }

        String password = passEdtxIn.getText().toString();
        if (password.length() < 6) {
            passEdtxIn.setError("Необходимо минимум 6 символов");
            validate = false;
        } else if (password.length() > 15) {
            passEdtxIn.setError("Допустимая длинна 15 символов");
            validate = false;
        }

        return validate;
    }

}
