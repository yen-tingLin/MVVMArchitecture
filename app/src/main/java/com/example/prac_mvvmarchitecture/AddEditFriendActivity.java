package com.example.prac_mvvmarchitecture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddEditFriendActivity extends AppCompatActivity {
    EditText inputName, inputEmail, inputLocation;
    ImageView inputImg;

    public static final String EXTRA_ID =
            "com.example.prac_mvvmarchitecture.EXTRA_ID";
    public static final String EXTRA_NAME =
            "com.example.prac_mvvmarchitecture.EXTRA_NAME";
    public static final String EXTRA_EMAIL =
            "com.example.prac_mvvmarchitecture.EXTRA_EMAIL";
    public static final String EXTRA_LOCATION =
            "com.example.prac_mvvmarchitecture.EXTRA_LOCATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        setView();
        getData();
    }

    public void setView() {
        inputName = findViewById(R.id.input_name);
        inputEmail = findViewById(R.id.input_email);
        inputLocation = findViewById(R.id.input_location);
        inputImg = findViewById(R.id.input_img);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
    }

    private void saveData() {
        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String location = inputLocation.getText().toString();

        if(name.trim().isEmpty()) {
            Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if(email.trim().isEmpty() && location.trim().isEmpty()) {
            Toast.makeText(this, "Email or location is required", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_EMAIL, email);
        intent.putExtra(EXTRA_LOCATION, location);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1) {
            intent.putExtra(EXTRA_ID, id) ;
        }

        setResult(RESULT_OK, intent);
        finish();
    }

    public void getData() {
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Friend");

            String name = intent.getStringExtra(EXTRA_NAME);
            String email = intent.getStringExtra(EXTRA_EMAIL);
            String location = intent.getStringExtra(EXTRA_LOCATION);

            inputName.setText(name);
            inputEmail.setText(email);
            inputLocation.setText(location);
        } else {
            setTitle("Add Friend");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_friend_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id. icon_save :
                saveData();
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }

    }
}
