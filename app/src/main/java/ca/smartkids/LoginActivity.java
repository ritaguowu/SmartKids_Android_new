package ca.smartkids;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import ca.smartkids.data.DataStoreManager;
import ca.smartkids.databinding.ActivityLoginBinding;
import ca.smartkids.viewmodel.LoginViewModel;


public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);


        DataStoreManager.instance.init(this);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);


        //Make app use full screen
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().getInsetsController().hide(WindowInsets.Type.statusBars());
        } else {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }
        setupActionBar();

        //Click the login button
        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.etEmail.getText().toString().trim();
                String password = binding.etPassword.getText().toString().trim();
                loginViewModel.login(email, password);
            }
        });

        //Create the observer which updates the UI.
        loginViewModel.getLoginResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvLoginResult.setText(s);
                if (s.equals("Load Kids Success")) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        });

    }

    private void setupActionBar(){
        setSupportActionBar(binding.toolbarLoginActivity);
        binding.toolbarLoginActivity.setNavigationIcon(R.drawable.ic_black_back_24dp);
        binding.toolbarLoginActivity.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}