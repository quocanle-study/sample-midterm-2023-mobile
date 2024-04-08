package com.midterm2023.quocanle;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.midterm2023.quocanle.DAO.StudentDAO;
import com.midterm2023.quocanle.databaseUtil.AppDatabase;
import com.midterm2023.quocanle.databinding.ActivityMainBinding;
import com.midterm2023.quocanle.model.Student;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static MainActivity instance;
    private ActivityMainBinding binding;
    private AppDatabase appDatabase;
    protected ArrayList<Student> studentList;
    private StudentDAO studentDao;
    private ArrayAdapter<Student> adapter;

    // Constructor
    public MainActivity() {
        instance = this;
    }

    // Hàm này trả về instance của MainActivity
    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        // binding layout
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // set toolbar
        setSupportActionBar(binding.toolbar);

        // get instance của database
        appDatabase = AppDatabase.getInstance(getApplicationContext());
        studentDao = appDatabase.studentDao();

        // set adapter cho listview
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                studentList = (ArrayList<Student>) studentDao.getAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new ArrayAdapter<Student>(MainActivity.this, android.R.layout.simple_list_item_1, studentList) {
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                TextView textView = (TextView) super.getView(position, convertView, parent);
                                Student student = studentList.get(position);
                                textView.setText("Full Name: " + student.getFullName() + "\nAge: " + student.getAge() + "\nAddress: " + student.getAddress());
                                return textView;
                            }
                        };
                        binding.lvStudent.setAdapter(adapter);
                    }
                });
            }
        });

        binding.lvStudent.setAdapter(adapter);

        // set click cho button add
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check input
                if (binding.etFullName.getText().toString().isEmpty() || binding.etAge.getText().toString().isEmpty() || binding.etAddress.getText().toString().isEmpty()) {
                    CharSequence text = "Please fill all fields";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(MainActivity.this, text, duration);
                    toast.show();
                    return;
                }
                // check age
                else if (!binding.etAge.getText().toString().matches("\\d+")) {
                    CharSequence text = "Age must be a number";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(MainActivity.this, text, duration);
                    toast.show();
                    return;
                }
                int agecheck = Integer.parseInt(binding.etAge.getText().toString());
                if (agecheck < 0 || agecheck > 99) {
                    CharSequence text = "Age must be between 0 and 99";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(MainActivity.this, text, duration);
                    toast.show();
                    return;
                }
                // add student
                String fullName = binding.etFullName.getText().toString();
                String age = binding.etAge.getText().toString();
                String address = binding.etAddress.getText().toString();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        studentDao.insertAll(new Student(fullName, age, address));
                        ArrayList<Student> students = new ArrayList<>(studentDao.getAll());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                CharSequence text = "Student added successfully";
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(MainActivity.this, text, duration);
                                toast.show();
                                studentList.clear();
                                studentList.addAll(students);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        });

        // set click cho listview
        binding.lvStudent.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("student", position);
            startActivity(intent);
        });
    }
}