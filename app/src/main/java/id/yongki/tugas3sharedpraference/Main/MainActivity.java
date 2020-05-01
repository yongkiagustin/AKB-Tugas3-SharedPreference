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


// 1 mei 2020 18.35 layouting main activity
// 1 mei 2020 19.05 layouting register activity
// 1 mei 2020 19.22 layouting register activity
// 1 mei 2020 19.55 layouting home activity
// 1 mei 2020 20.00 membuat fungsi di semua activity

public class MainActivity extends AppCompatActivity {
    private TextView txtMasuk;
    private TextView txtRegister;
    private EditText etUsername;
    private EditText etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        declareView();

        txtMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasiLogin();
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), RegisterActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Preferences.getLoggedInStatus(getBaseContext())) {
            startActivity(new Intent(getBaseContext(), HomeActivity.class));
            finish();
        }
    }


    private void declareView() {

        txtRegister = findViewById(R.id.txt_login_register);
        txtMasuk = findViewById(R.id.txt_login_masuk);
        etUsername = findViewById(R.id.et_login_username);
        etPassword = findViewById(R.id.et_login_password);

    }

    private void validasiLogin() {

        
        etUsername.setError(null);
        etPassword.setError(null);
        View fokus = null;
        boolean cancel = false;

     
        String userName = etUsername.getText().toString();
        String password = etPassword.getText().toString();


        if (TextUtils.isEmpty(userName)) {
            etUsername.setError("Harus diisi");
            fokus = etUsername;
            cancel = true;
        } else if (!cekUser(userName)) {
            etUsername.setError("Username Tidak Ditemukan");
            fokus = etUsername;
            cancel = true;
        }

        
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Harus Diisi");
            fokus = etPassword;
            cancel = true;
        } else if (!cekPassword(password)) {
            etPassword.setError("Data yang dimasukkan tidak sesuai");
            fokus = etPassword;
            cancel = true;
        }
        
        if (cancel) {
            fokus.requestFocus();
        } else {
         
            UserModel userModel = new UserModel();
            userModel.setUsername(userName);
            userModel.setPassword(password);
            
            Preferences.setUserPreferences(getBaseContext(), userModel);
            Preferences.setLoggedInStatus(getBaseContext(), true);
           
            startActivity(new Intent(getBaseContext(), HomeActivity.class));
            finish();
        }

    }

  
    private boolean cekUser(String user) {
        return user.equals(Preferences.getRegisteredUser(getBaseContext()));
    }

    
    private boolean cekPassword(String password) {
        return password.equals(Preferences.getRegisteredPassword(getBaseContext()));
    }

}
