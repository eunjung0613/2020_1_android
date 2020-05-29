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
        Class classobj = null;
        switch (view.getId()) {
            case R.id.button1:
                classobj = Firebase1Activity.class;
                break;
            case R.id.button2:
                classobj = MemoList1Activity.class;
                break;
            case R.id.button3:
                classobj = MemoList2Activity.class;
                break;
            case R.id.button4:
                classobj = MemoList3Activity.class;
                break;
        }
        Intent intent = new Intent(this,classobj);
        startActivity(intent);
    }
}

