package ca.smartkids.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import java.util.List;

import ca.smartkids.R;
import ca.smartkids.data.GlobalData;
import ca.smartkids.model.User;
import ca.smartkids.viewmodel.KidViewModel;
import ca.smartkids.viewmodel.UserListViewModel;

public class AddKidFragment extends DialogFragment {

    AppCompatEditText etKidName;
    Button btnSubmit;
    KidViewModel kidViewModel;
    ViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_kid_dialog, container, false);
        viewModel = new ViewModelProvider(getParentFragment()).get(UserListViewModel.class);
        kidViewModel = new KidViewModel((UserListViewModel) viewModel);

        btnSubmit = v.findViewById(R.id.btnSubmit);
        etKidName = v.findViewById(R.id.etKidName);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String kidName = etKidName.getText().toString().trim();
                if (!kidName.equals("")) {
                    String parentId = GlobalData.getInstance().getUser().getUser_id();
                    kidViewModel.createKid(kidName, parentId);
                }
                Bundle result = new Bundle();
                result.putString("bundleKey", "Success");
               getParentFragmentManager().setFragmentResult("requestKey", result);

                dismiss();
            }
        });

        return v;
    }

    public static String TAG = "PurchaseConfirmationDialog";


}
