package id.yongki.tugas3sharedpraference.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import id.yongki.tugas3sharedpraference.Models.UserModel;
import id.yongki.tugas3sharedpraference.R;
import id.yongki.tugas3sharedpraference.Utils.Preferences;

public class RegisterActivity extends AppCompatActivity {
    private TextView txtMasuk;
    private EditText etUserName;
    private EditText etPassword;
    private EditText etRePassWord;
    private EditText etPhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        declareView();
        txtMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasiRegister();
            }
        });
    }

    private void declareView() {
        txtMasuk = findViewById(R.id.txt_reg_masuk);
        etUserName = findViewById(R.id.et_reg_username);
        etPassword = findViewById(R.id.et_reg_password);
        etRePassWord = findViewById(R.id.et_reg_password_confirm);
        etPhoneNumber = findViewById(R.id.et_reg_phone);
    }

    private void validasiRegister(){

        // Mereset semua Error dan fokus menjadi default
        etUserName.setError(null);
        etPassword.setError(null);
        etRePassWord.setError(null);
        View fokus = null;
        boolean cancel = false;

        //Set Input Value dari View
        String userName = etUserName.getText().toString();
        String password = etPassword.getText().toString();
        String rePassword = etRePassWord.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();


        // Jika form user kosong atau memenuhi kriteria di Method cekUser() maka, Set error di Form-
        // User dengan menset variable fokus dan error di Viewnya juga cancel menjadi true*/
        if (TextUtils.isEmpty(userName)){
            etUserName.setError("Harus diisi");
            fokus = etUserName;
            cancel = true;
        }else if(cekUser(userName)){
            etUserName.setError("Username sudah terdaftar");
            fokus = etUserName;
            cancel = true;
        }

        // Jika form password kosong dan memenuhi kriteria di Method cekPassword maka,
        // Reaksinya sama dengan percabangan User di atas. Bedanya untuk Password dan Repassword*/
        if (TextUtils.isEmpty(password)){
            etPassword.setError("Harus Diisi");
            fokus = etPassword;
            cancel = true;
        }else if (!cekPassword(password,rePassword)){
            etPassword.setError("Password yang dimasukkan tidak sesuai");
            fokus = etPassword;
            cancel = true;
        }

    
        if (cancel){
            fokus.requestFocus();
        }else{
            UserModel userModel = new UserModel();
            userModel.setUsername(userName);
            userModel.setPassword(password);
            userModel.setPhone(phoneNumber);
            Preferences.setUserPreferences(getBaseContext(),userModel);
            Preferences.setLoggedInStatus(getBaseContext(),true);
            startActivity(new Intent(getBaseContext(), HomeActivity.class));
            finish();
        }

    }
    
    private boolean cekUser(String user){
        return user.equals(Preferences.getRegisteredUser(getBaseContext()));
    }
    
    private boolean cekPassword(String password, String repassword){
        return password.equals(repassword);
    }
}
