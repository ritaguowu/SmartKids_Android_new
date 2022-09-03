package ca.smartkids.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import ca.smartkids.R;
import ca.smartkids.data.GlobalData;
import ca.smartkids.databinding.ActivityKidMainBinding;
import ca.smartkids.databinding.ActivityLoginBinding;


public class KidMainActivity extends AppCompatActivity {
    private ActivityKidMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_kid_main);


        int position;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                position= -1;
            } else {
                position= extras.getInt("index");
            }
        } else {
            position= Integer.parseInt((String)savedInstanceState.getSerializable("index"));
        }


        binding.tvTitle.setText(GlobalData.getInstance().getKids().get(position).toString());

    }
}