package ca.smartkids.adapters;

import android.database.Observable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ca.smartkids.R;
import ca.smartkids.model.User;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    private ArrayList<User> kidsList;

    //How to start activity from RecyclerView adapter in fragment
    //https://androiderrors.com/how-to-start-activity-from-recyclerview-adapter-in-fragment/

    private OnActionListener mListener;

    public UserListAdapter(ArrayList<User> kidsList, OnActionListener listener) {
        this.kidsList = kidsList;
        this.mListener = listener;
    }

    public void updateKidsList(List<User> newKidsList) {
        kidsList.clear();
        kidsList.addAll(newKidsList);

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.startActivity( holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return kidsList.size();
    }

    public User getKidAt(int postion){
        return kidsList.get(postion);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
       // Reference to each cell view. Each cell view will pass to constructor
        // when we want to create an object from this class
        public View itemView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

    }

    public interface OnActionListener {
        public void startActivity(int position);
    }

}
