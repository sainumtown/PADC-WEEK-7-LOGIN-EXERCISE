package xyz.aungpyaephyo.padc.myanmarattractions.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.aungpyaephyo.padc.myanmarattractions.R;
import xyz.aungpyaephyo.padc.myanmarattractions.controllers.ControllerAccountControl;
import xyz.aungpyaephyo.padc.myanmarattractions.controllers.UserController;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.MyanmarAttractionsConstants;
import xyz.aungpyaephyo.padc.myanmarattractions.views.PasswordVisibilityListener;

/**
 * Created by aung on 7/15/16.
 */
public class LoginFragment extends Fragment  {

    @BindView(R.id.lbl_login_title)
    TextView tvLoginTitle;

    @BindView(R.id.et_email_login)
    EditText etEmail;

    @BindView(R.id.et_password_login)
    EditText etPassword;

    @BindView(R.id.btn_login)
    Button btnLogin;

    @BindView(R.id.tv_forgot_password)
    TextView tvForgotPasswword;

    @BindView(R.id.tv_wanna_register)
    TextView tvWannaRegister;

    private ControllerAccountControl mControllerAccountControl;
    private UserController mController;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mControllerAccountControl = (ControllerAccountControl) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);

        tvLoginTitle.setText(Html.fromHtml(getString(R.string.lbl_login_title)));
        tvForgotPasswword.setText(Html.fromHtml(getString(R.string.lbl_forgot_password)));
        tvWannaRegister.setText(Html.fromHtml(getString(R.string.lbl_wanna_register)));
        etPassword.setOnTouchListener(new PasswordVisibilityListener());
        return rootView;
    }

    @OnClick(R.id.tv_wanna_register)
    public void onTapWannaRegister(TextView tvWannaRegister) {
        mControllerAccountControl.onTapRegister();
    }

    @OnClick(R.id.btn_login)
    public void onTapLogin(Button btnLogin) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

            if (TextUtils.isEmpty(email)) {
                etEmail.setError(getString(R.string.error_missing_email));
            }

            if (TextUtils.isEmpty(password)) {
                etPassword.setError(getString(R.string.error_missing_password));
            }
        } else if (!isEmailValid(email)) {
            //Email address is not in the right format.
            etEmail.setError(getString(R.string.error_email_is_not_valid));
        } else {
            //Checking on client side is done here.
            mControllerAccountControl.onLogin(email, password);
        }
    }

    public boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(MyanmarAttractionsConstants.EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

}
