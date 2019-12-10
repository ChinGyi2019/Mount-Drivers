package mounts.com.driver.activity.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import mounts.com.driver.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.back){
            finish();
        }

    }
}
