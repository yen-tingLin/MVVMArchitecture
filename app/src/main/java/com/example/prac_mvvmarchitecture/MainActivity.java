package com.example.prac_mvvmarchitecture;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FriendViewModel friendViewModel;
    private FriendAdapter adp;

    public static final int ADD_FRIEND_REQUEST = 2;
    public static final int EDIT_FRIEND_REQUEST = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViewModel();
        setView();
    }

    public void setViewModel() {
        // solve the conflict between AndroidViewModel and ViewModelProvider() by
        // passing the second argument ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()
        // https://blog.csdn.net/xiaojinlai123/article/details/106092108
        friendViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(FriendViewModel.class);
        friendViewModel.getAllFriends().observe(this, new Observer<List<Friend>>() {
            @Override
            public void onChanged(List<Friend> friends) {
                adp.submitList(friends);
            }
        });
    }

    public void setView() {
        // add
        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditFriendActivity.class);
                startActivityForResult(intent, ADD_FRIEND_REQUEST);
            }
        });

        // recyclerView
        RecyclerView recyclerView = findViewById(R.id.rv);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);

        // add divider for recyclerview item :
        DividerItemDecoration di = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(di);

        adp = new FriendAdapter();
        recyclerView.setAdapter(adp);

        // remove
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Friend friend = adp.getFriendAt(viewHolder.getAdapterPosition());
                friendViewModel.delete(friend);
                Toast.makeText(MainActivity.this, "Removed", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adp.setOnRVItemClickListener(new FriendAdapter.onRVItemClickListener() {
            @Override
            public void onRVItemClick(Friend friend) {
                Intent intent = new Intent(MainActivity.this, AddEditFriendActivity.class);
                intent.putExtra(AddEditFriendActivity.EXTRA_ID, friend.getId());
                intent.putExtra(AddEditFriendActivity.EXTRA_NAME, friend.getName());
                intent.putExtra(AddEditFriendActivity.EXTRA_EMAIL, friend.getEmail());
                intent.putExtra(AddEditFriendActivity.EXTRA_LOCATION, friend.getLocation());

                startActivityForResult(intent, EDIT_FRIEND_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_FRIEND_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddEditFriendActivity.EXTRA_NAME);
            String email = data.getStringExtra(AddEditFriendActivity.EXTRA_EMAIL);
            String location = data.getStringExtra(AddEditFriendActivity.EXTRA_LOCATION);

            Friend friend = new Friend(name, email, location);
            friendViewModel.insert(friend);

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        } else if(requestCode == EDIT_FRIEND_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditFriendActivity.EXTRA_ID, -1);

            if(id == -1) {
                Toast.makeText(this, "Friend cannot be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(AddEditFriendActivity.EXTRA_NAME);
            String email = data.getStringExtra(AddEditFriendActivity.EXTRA_EMAIL);
            String location = data.getStringExtra(AddEditFriendActivity.EXTRA_LOCATION);

            Friend friend = new Friend(name, email, location);
            friend.setId(id);
            friendViewModel.update(friend);

            Toast.makeText(this, "Friend updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.delete_all :
                friendViewModel.deleteAllFriends();
                Toast.makeText(this, "All deleted", Toast.LENGTH_SHORT).show();
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }
}
