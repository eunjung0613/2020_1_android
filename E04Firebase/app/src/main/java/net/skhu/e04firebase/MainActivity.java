package net.skhu.e04firebase;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.skhu.e04firebase.Firebase1Activity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void button_clicked(View view) {
        Class classObj = null;
        switch (view.getId()) {
            case R.id.button1:
                classObj = Firebase1Activity.class;
                break;
            case R.id.button2:
                classObj = MemoList1Activity.class;
                break;
            case R.id.button3:
                classObj = MemoList2Activity.class;
                break;
        }
        Intent intent = new Intent(this, classObj);
        startActivity(intent);
    }
}
