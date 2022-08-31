package ca.smartkids.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ca.smartkids.R;
import ca.smartkids.databinding.ActivityMainBinding;
import ca.smartkids.viewmodel.MyProfileViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MyProfileViewModel myProfileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        myProfileViewModel = new ViewModelProvider(this).get(MyProfileViewModel.class);

        //Create navigation controller
        BottomNavigationView bottomNavigationView = binding.bottomNavigationView;

        final NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        final NavController navController = navHostFragment.getNavController();


        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}