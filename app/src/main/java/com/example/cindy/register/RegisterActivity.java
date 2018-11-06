package com.example.cindy.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.cindy.register.data.RegisterBean;
import com.example.cindy.register.utils.SharePreferenceUtil;
import com.example.cindy.register.utils.TextUtil;

import org.litepal.LitePal;

import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;

import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import static com.example.cindy.register.utils.TextUtil.checkTel;
import static com.example.cindy.register.utils.TextUtil.matchAccount;
import static com.example.cindy.register.utils.TextUtil.verifyStoragePermissions;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.edit_code)
    EditText editCode;
    @BindView(R.id.btn_getcode)
    Button btnGetcode;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.edit_verifypassword)
    EditText editVerifypassword;
    private TimeCount mTimeCount;//计时器
    public EventHandler eh; //事件接收器
    private boolean flag;   // 操作是否成功


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        verifyStoragePermissions(this);
        init();


        mTimeCount = new TimeCount(60000, 1000);

    }


    /**
     * 计时器
     */
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            btnGetcode.setClickable(false);
            btnGetcode.setText(l / 1000 + "秒后重新获取");
        }

        @Override
        public void onFinish() {
            btnGetcode.setClickable(true);
            btnGetcode.setText("获取验证码");
        }
    }


//    /**
//     * 初始化事件接收器
//     */
//    private void init() {
//        eh = new EventHandler() {
//            @Override
//            public void afterEvent(int event, int result, Object data) {
//                //坑：进入mob平台官网，进入后台，点击SMSSDK，进入短信设置，关闭是否开启Mob云验证，否则同一个手机号只能发送一次短信。
//                if (result == SMSSDK.RESULT_COMPLETE) { //回调完成
//                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) { //提交验证码成功
//                      startActivity(new Intent(RegisterActivity.this, LoginActivity.class)); //页面跳转
//                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) { //获取验证码成功
//                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) { //返回支持发送验证码的国家列表
//                    }
//                } else {
//
//                   ((Throwable) data).printStackTrace();
//                }
//            }
//        };
//        SMSSDK.registerEventHandler(eh); //注册短信回调接口
//    }
    /**
     * 初始化事件接收器
     */
    private void init() {
        eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                //坑：进入mob平台官网，进入后台，点击SMSSDK，进入短信设置，关闭是否开启Mob云验证，否则同一个手机号只能发送一次短信。
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调接口
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (result == SMSSDK.RESULT_COMPLETE) {
                // 如果操作成功
                 if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) { // 校验验证码，返回校验的手机和国家代码
                     Toast.makeText(RegisterActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                     Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                     startActivity(intent);
                 } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) { // 获取验证码成功，true为智能验证，false为普通下发短信
                     Toast.makeText(RegisterActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                 } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) { // 返回支持发送验证码的国家列表
                     }
            } else { // 如果操作失败
                 if (flag) {
                     Toast.makeText(RegisterActivity.this, "验证码获取失败，请重新获取", Toast.LENGTH_SHORT).show();
                     editPhone.requestFocus();
                 } else {
                     ((Throwable) data).printStackTrace();
                     Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();

                 } } } };



    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);  //销毁回调接口
    }


    @OnClick({R.id.btn_register, R.id.btn_getcode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_register:

                if (TextUtil.isEmpty(editPhone.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!checkTel(editPhone.getText().toString().trim())) {
                    Toast.makeText(RegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtil.isEmpty(editPassword.getText().toString())) {
                    editCode.requestFocus();//把输入焦点放在该控件上
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (matchAccount(editPassword.getText().toString())) {
                   Toast.makeText(RegisterActivity.this, "请输入6-18位数字字母下划线密码", Toast.LENGTH_SHORT).show();
                   return;
                } //密码太多测试时太麻烦

                if (TextUtil.isEmpty(editVerifypassword.getText().toString())) {
                    editCode.requestFocus();//把输入焦点放在该控件上
                    Toast.makeText(RegisterActivity.this, "请确认密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!editVerifypassword.getText().toString().equals(editPassword.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (!TextUtil.isEmpty(editCode.getText().toString())) {
                    if (editCode.getText().length() == 4) {
                        SMSSDK.submitVerificationCode("+86", editPhone.getText().toString().trim(), editCode.getText().toString().trim());//提交验证
                        flag = false;
                    } else {
                        Toast.makeText(this, "请输入完整的验证码", Toast.LENGTH_SHORT).show();
                        editCode.requestFocus();
                        return;
                    } } else {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    editCode.requestFocus();
                    return;
                }
//
//                if (TextUtil.isEmpty(editCode.getText().toString())){
//                    Toast.makeText(RegisterActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (!TextUtil.isEmpty(editCode.getText().toString())){
//
//                    SMSSDK.submitVerificationCode("+86", editPhone.getText().toString().trim(), editCode.getText().toString().trim());//提交验证
////                return;  哇 要被这个return给坑死了 ，就说为什么数据库一直创建不了，为什么调式到这一步就不往下走了，底下给数据库增加数据的代码就没执行
////                能有数据才鬼了  ，都是因为return,气死了！！! 我真是个猪。  return了就是返回上一步？就不往下走了？
//           }
                List<RegisterBean> list = LitePal.findAll(RegisterBean.class);
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getPhone().equals(editPhone.getText().toString())) {
                        Toast.makeText(RegisterActivity.this, "手机号已经注册", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                RegisterBean bean = new RegisterBean();
                bean.setPhone(editPhone.getText().toString());
                bean.setPassword(editPassword.getText().toString());
                bean.save();  //创建RegisterBean数据表对象bean，将手机号和密码存储到数据表registerbean中
                SharePreferenceUtil.getStringSP("currentphone", editPhone.getText().toString());
                SharePreferenceUtil.getStringSP("currentpassword", editPassword.getText().toString());
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
//               finish();  //原来问题在这，
                break;
            case R.id.btn_getcode:
                if (!editPhone.getText().toString().trim().equals("")) {
                    if (checkTel(editPhone.getText().toString().trim())) {
                        SMSSDK.getVerificationCode("+86", editPhone.getText().toString());//获取验证码
                        editCode.requestFocus();//把输入焦点放在该控件上
                        mTimeCount.start();
                    } else {
                        Toast.makeText(RegisterActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                        editPhone.requestFocus();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                    editPhone.requestFocus();
                }
                break;
            default:
                break;
        }
    }
}

