package ca.smartkids;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import ca.smartkids.data.DataStoreManager;
import ca.smartkids.databinding.ActivityWelcomeBinding;
import ca.smartkids.viewmodel.LoginViewModel;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityWelcomeBinding binding;

    private Button btnSignupIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataStoreManager dataStoreManager = DataStoreManager.getInstance();
        dataStoreManager.init(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);

        //Use font downloaded from below website:
        // https://www.1001fonts.com/free-for-commercial-use-fonts.html
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "sacramento.ttf");
        binding.tvAppName.setTypeface(typeFace);


        Typeface typeFaceLogo = Typeface.createFromAsset(getAssets(), "BLKCHCRY.TTF");
        binding.tvLogo.setTypeface(typeFaceLogo);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().getInsetsController().hide(WindowInsets.Type.statusBars());
        } else {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }
        binding.tvSignIn.setOnClickListener(this);
        binding.btnSignup.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tvSignIn) {
            startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        } else if (id == R.id.btnSignup) {
            startActivity(new Intent(WelcomeActivity.this, SignUpActivity.class));
        }
    }

}
