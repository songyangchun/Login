package com.example.cindy.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.Guideline;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;



import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.textView2)
    TextView textView2;


    @BindView(R.id.guideline3)
    Guideline guideline3;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.password)
    EditText passwordedit;
    @BindView(R.id.remember_pass)
    CheckBox rememberPass;
    @BindView(R.id.phone)
    EditText phoneedit;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        pref = PreferenceManager.getDefaultSharedPreferences(this);//获取SharedPreferences对象pref
        boolean isRemember = pref.getBoolean("remember_password", Boolean.parseBoolean("false"));
        if (isRemember) {
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            phoneedit.setText(account);
            passwordedit.setText(password);
            rememberPass.setChecked(true);
        }

    }



    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String account = phoneedit.getText().toString();
                String password = passwordedit.getText().toString();
                if (account.equals("song") && password.equals("123456")) {
                    editor = pref.edit();
                    if (rememberPass.isChecked()) {
                        //登录成功之后检查复选框是否被选中，如果选中，表示记住密码，此时将remember_password键的值设置为true,并将account,password
                        //对应的值存到SharedPreferences里并提交。
                        editor.putBoolean("remember_password", Boolean.parseBoolean("true"));
                        editor.putString("account", account);
                        editor.putString("password", password);
                    } else {
                        //如果未选中，则清除SharedPreferences里的数据
                        editor.clear();
                    }
                    editor.apply();  //提交数据
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.btn_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();

                break;
        }
    }
}





