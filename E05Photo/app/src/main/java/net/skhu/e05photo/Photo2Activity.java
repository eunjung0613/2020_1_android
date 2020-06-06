package net.skhu.e05photo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;

import java.io.File;

public class Photo2Activity extends AppCompatActivity {
    FileRecyclerView1Adapter fileRecyclerView1Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo2);

        File directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File[] files = ((File) directory).listFiles();

        fileRecyclerView1Adapter = new FileRecyclerView1Adapter(this, files);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(fileRecyclerView1Adapter);

    }
}
