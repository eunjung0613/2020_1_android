package net.skhu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    ArrayList<String>arrayList;
    ArrayAdapter<String>adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        arrayList = new ArrayList<String>();
        arrayList.add("One");
        arrayList.add("Two");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        Button b = findViewById(R.id.btnAdd);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText e = findViewById(R.id.editText);
                CharSequence s = e.getText();
                e.setText("");
                arrayList.add(s.toString());
            }
        });
    }
}
