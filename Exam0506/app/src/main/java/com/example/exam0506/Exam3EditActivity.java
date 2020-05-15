package com.example.exam0506;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class Exam3EditActivity extends AppCompatActivity {

    Exam3Adapter exam3Adapter;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam3_edit);

        final EditText editText = findViewById(R.id.editText1);
        Button b = findViewById(R.id.btn_save);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                editText.setText("");
                arrayList.add(s);
                exam3Adapter.notifyDataSetChanged();
            }
        });
    }
}
