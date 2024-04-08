package com.midterm2023.quocanle;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;

import com.midterm2023.quocanle.databinding.ActivityDetailBinding;
import com.midterm2023.quocanle.model.Student;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        // binding layout
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // set toolbar
        setSupportActionBar(binding.toolbar);

        // get student from intent
        Intent intent = getIntent();
        int position = intent.getIntExtra("student", 0);
        Student student = MainActivity.getInstance().studentList.get(position);

        // set text for textview
        binding.tvFullName.setText(student.getFullName());
        binding.tvAge.setText(student.getAge());
        binding.tvAddress.setText(student.getAddress());
    }

    // set back button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back, menu);
        menu.findItem(R.id.app_bar_back).setOnMenuItemClickListener(item -> {
            onBackPressed();
            return true;
        });
        return true;
    }
}