package com.example.exam0506;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button button = (Button)findViewById(R.id.button);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void btn_clicked(View view){
        Intent intent = new Intent(this,Exam3Activity.class);
        startActivity(intent);
    }
}
