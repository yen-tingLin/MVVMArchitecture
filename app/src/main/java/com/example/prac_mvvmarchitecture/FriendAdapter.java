package com.example.prac_mvvmarchitecture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Objects;

public class FriendAdapter extends ListAdapter<Friend, FriendAdapter.FriendHolder> {

    private onRVItemClickListener listener;

    public FriendAdapter() {
        super(DIFF_CALLBACK);
    }

    public static final DiffUtil.ItemCallback<Friend> DIFF_CALLBACK = new DiffUtil.ItemCallback<Friend>() {
        @Override
        public boolean areItemsTheSame(@NonNull Friend oldItem, @NonNull Friend newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Friend oldItem, @NonNull Friend newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getEmail().equals(newItem.getEmail()) &&
                    oldItem.getLocation().equals(newItem.getLocation());
        }
    };

    @NonNull
    @Override
    public FriendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_item, parent, false);
        return new FriendHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendHolder holder, int position) {
        Friend currentFriend = getItem(position);

        // bind name
        holder.name.setText(currentFriend.getName());
        // bind image
        //byte[] imageByte = currentFriend.getImage();
        //Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
        //holder.image.setImageBitmap(bitmap);

    }

    /*
    @Override
    public int getItemCount() {
        return friendList.size();
    }

     */
    /*
    public void setFriends(List<Friend> friend_list) {
        this.friendList = friend_list;
        notifyDataSetChanged();
    }

     */

    public Friend getFriendAt(int pos) {
        return getItem(pos);
    }

    public class FriendHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;

        public FriendHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.pic);
            name = itemView.findViewById(R.id.txv_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(listener != null && pos != RecyclerView.NO_POSITION) {
                        listener.onRVItemClick(getItem(pos));
                    }
                }
            });
        }
    }

    public interface onRVItemClickListener {
        void onRVItemClick(Friend friend);
    }

    public void setOnRVItemClickListener(onRVItemClickListener listener) {
        this.listener = listener;
    }
}
