package ca.smartkids;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ca.smartkids.databinding.FragmentKidsBinding;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class KidsFragment extends Fragment {

    public KidsFragment() {
        super(R.layout.fragment_kids);
    }

    private FragmentKidsBinding binding;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentKidsBinding.bind(view);

        Drawable myDrawable = getResources().getDrawable(R.drawable.default_user);
        binding.cvImage.setImageDrawable(myDrawable);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),
//                R.layout.list_kid, R.id.kid_name, DayOfWeek);
//        binding.mySpinner.setAdapter(adapter);

        binding.fabAddKid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String parentName = binding.tvUserName.getText().toString().trim();
                //Initialize fragment
                Fragment addFragment = new AddKidFragment();
                //Commit fragment
                getParentFragmentManager().beginTransaction().replace(R.id.kidsFragment, addFragment).commit();

            }
        });
    }
}