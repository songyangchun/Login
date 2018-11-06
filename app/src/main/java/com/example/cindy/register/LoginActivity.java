package com.example.cindy.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.Guideline;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cindy.register.data.RegisterBean;
import com.example.cindy.register.utils.SharePreferenceUtil;
import com.example.cindy.register.utils.TextUtil;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

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
    @BindView(R.id.passwordeye)
    ImageView passwordeye;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private boolean               ispasswordshow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initview();


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

private  void initview(){

    passwordeye.setImageResource(R.drawable.eye_close_60);
    ispasswordshow=false;
}

    @OnClick({R.id.btn_login, R.id.btn_register,R.id.passwordeye})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if(checkNull()){return;}
                //在LoginInfoBeanEL表中查询用户名和用户类型
                List<RegisterBean> loginList = LitePal.where("phone = ? ", phoneedit.getText().toString()).find(RegisterBean.class);
                if(loginList.isEmpty()){
                    //先确认此用户名在数据库中存在
                    Toast.makeText(LoginActivity.this, "用户名错误", Toast.LENGTH_SHORT).show();
                    return;
                }else if( loginList.get(0).getPassword().equals(passwordedit.getText().toString())){
                    //再对比密码，list的大小只能为1，因为用户名不重复
                    //暂时缓存数据
                    SharePreferenceUtil.setStringSP("currentusername_el",phoneedit.getText().toString());
                    SharePreferenceUtil.setStringSP("currentpsaaword_el",passwordedit.getText().toString());
                    Intent i=new Intent(this, MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                    return;
                }



//                String account = phoneedit.getText().toString();
//                String password = passwordedit.getText().toString();
//                if (account.equals("song") && password.equals("123456")) {
//                    editor = pref.edit();
//                    if (rememberPass.isChecked()) {
//                        //登录成功之后检查复选框是否被选中，如果选中，表示记住密码，此时将remember_password键的值设置为true,并将account,password
//                        //对应的值存到SharedPreferences里并提交。
//                        editor.putBoolean("remember_password", Boolean.parseBoolean("true"));
//                        editor.putString("account", account);
//                        editor.putString("password", password);
//                    } else {
//                        //如果未选中，则清除SharedPreferences里的数据
//                        editor.clear();
//                    }
//                    editor.apply();  //提交数据
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }

                break;


            case R.id.btn_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();

                break;
            case R.id.passwordeye:
                if(ispasswordshow){
                    passwordedit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD) ;//输入类型为普通文本|不可见的密码
                    passwordeye.setImageResource(R.drawable.eye_close_60);
                    ispasswordshow=false;
                }else{
                    passwordedit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) ;//输入类型为用户可见的密码
                    passwordeye.setImageResource(R.drawable.eye_open_60);
                    ispasswordshow=true;
                }
                break;


        }
    }
    private boolean checkNull() {
        if(TextUtil.isEmpty(phoneedit.getText().toString())){
            Toast.makeText(LoginActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(TextUtil.isEmpty(passwordedit.getText().toString())){
            Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

}





