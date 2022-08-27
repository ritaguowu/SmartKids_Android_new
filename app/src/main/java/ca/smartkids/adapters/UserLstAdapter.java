package ca.smartkids.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.smartkids.R;
import ca.smartkids.model.User;

public class UserLstAdapter extends RecyclerView.Adapter<UserLstAdapter.UserViewHolder> {

    private ArrayList<User> kidsList;

    public UserLstAdapter(ArrayList<User> kidsList) {
        this.kidsList = kidsList;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);

        // Create an object type DogViewHolder and pass inflated view for each cell to its constructor
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        // Create Reference to UI elements
        ImageView image = holder.itemView.findViewById(R.id.cv_item_image);
        TextView name = holder.itemView.findViewById(R.id.tvUserName);

        // Set value for UI elements
        name.setText(kidsList.get(position).getUser_name());
    }

    @Override
    public int getItemCount() {
        return kidsList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
       // Reference to each cell view. Each cell view will pass to constructor
        // when we want to create an object from this class
        public View itemView;
        //public ItemDogBinding itemView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}
