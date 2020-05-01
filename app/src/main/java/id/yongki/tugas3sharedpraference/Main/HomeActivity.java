package id.yongki.tugas3sharedpraference.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import id.yongki.tugas3sharedpraference.R;
import id.yongki.tugas3sharedpraference.Utils.Preferences;



public class HomeActivity extends AppCompatActivity {
    private TextView txtKeluar;
    private TextView txtName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        declareView();
        txtKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Preferences.setLogout(getBaseContext());
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            }
        });
    }

    private void declareView() {
        txtKeluar = findViewById(R.id.txt_logout);
        txtName = findViewById(R.id.txtName);

        txtName.setText(Preferences.getRegisteredUser(getBaseContext()));
    }
}
