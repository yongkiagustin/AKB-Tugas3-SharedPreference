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
    private EditText edtUserName;
    private EditText edtPassWord;
    private EditText edtRePassWord;
    private EditText edtPhoneNumber;
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
        edtUserName = findViewById(R.id.edt_reg_username);
        edtPassWord = findViewById(R.id.edt_reg_password);
        edtRePassWord = findViewById(R.id.edt_reg_password_confirm);
        edtPhoneNumber = findViewById(R.id.edt_reg_phone);
    }

    private void validasiRegister(){

        // Mereset semua Error dan fokus menjadi default
        edtUserName.setError(null);
        edtPassWord.setError(null);
        edtRePassWord.setError(null);
        View fokus = null;
        boolean cancel = false;

        //Set Input Value dari View
        String userName = edtUserName.getText().toString();
        String password = edtPassWord.getText().toString();
        String rePassword = edtRePassWord.getText().toString();
        String phoneNumber = edtPhoneNumber.getText().toString();


        // Jika form user kosong atau memenuhi kriteria di Method cekUser() maka, Set error di Form-
        // User dengan menset variable fokus dan error di Viewnya juga cancel menjadi true*/
        if (TextUtils.isEmpty(userName)){
            edtUserName.setError("Harus diisi");
            fokus = edtUserName;
            cancel = true;
        }else if(cekUser(userName)){
            edtUserName.setError("Username sudah terdaftar");
            fokus = edtUserName;
            cancel = true;
        }

        // Jika form password kosong dan memenuhi kriteria di Method cekPassword maka,
        // Reaksinya sama dengan percabangan User di atas. Bedanya untuk Password dan Repassword*/
        if (TextUtils.isEmpty(password)){
            edtPassWord.setError("Harus Diisi");
            fokus = edtPassWord;
            cancel = true;
        }else if (!cekPassword(password,rePassword)){
            edtPassWord.setError("Password yang dimasukkan tidak sesuai");
            fokus = edtPassWord;
            cancel = true;
        }

        // Jika cancel true, variable fokus mendapatkan fokus. Jika false, maka
        // Kembali ke LoginActivity dan Set User dan Password untuk data yang terdaftar */
        if (cancel){
            fokus.requestFocus();
        }else{
// Deklarasi Model
            UserModel userModel = new UserModel();
            userModel.setUsername(userName);
            userModel.setPassword(password);
            userModel.setPhone(phoneNumber);
            // Simpan data ke shared preferences
            Preferences.setUserPreferences(getBaseContext(),userModel);
            Preferences.setLoggedInStatus(getBaseContext(),true);
            //Pindah Halaman ke home
            startActivity(new Intent(getBaseContext(), HomeActivity.class));
            finish();
        }

    }


    // True jika parameter user sama dengan data user yang terdaftar dari Preferences */
    private boolean cekUser(String user){
        return user.equals(Preferences.getRegisteredUser(getBaseContext()));
    }

    // True jika parameter password sama dengan parameter repassword */
    private boolean cekPassword(String password, String repassword){
        return password.equals(repassword);
    }
}
