package net.skhu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;

public class RecyclerView3Activity extends AppCompatActivity {
    public static final int REQUEST_CREATE = 0;
    public static final int REQUEST_EDIT = 1;

    int memoIndex;
    RecyclerView3Adapter recyclerView3Adapter;
    ArrayList<Memo> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view3);

        arrayList = new ArrayList<Memo>();
        arrayList.add(new Memo("one", new Date()));
        arrayList.add(new Memo("two", new Date()));
        recyclerView3Adapter = new RecyclerView3Adapter(this, arrayList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerView3Adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recycler_view3, menu);
        MenuItem menuItem = menu.findItem(R.id.action_remove);
        menuItem.setVisible(recyclerView3Adapter.checkedCount > 0);
        return true;
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_create) {
            Intent intent = new Intent(this, MemoActivity.class);
            startActivityForResult(intent, REQUEST_CREATE);
            return true;
        } else if (id == R.id.action_remove) {
            deleteItems();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            Memo memo = (Memo)intent.getSerializableExtra("MEMO");
            if (requestCode == REQUEST_CREATE)
                arrayList.add(memo);
            else if (requestCode == REQUEST_EDIT)
                arrayList.set(memoIndex, memo);
            recyclerView3Adapter.notifyDataSetChanged();
        }
    }
    private void deleteItems() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm);
        builder.setMessage(R.string.doYouWantToDelete);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int index) {
                ListIterator<Memo> iterator = arrayList.listIterator();
                while (iterator.hasNext())
                    if (iterator.next().isChecked())
                        iterator.remove();
                recyclerView3Adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(R.string.no, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
