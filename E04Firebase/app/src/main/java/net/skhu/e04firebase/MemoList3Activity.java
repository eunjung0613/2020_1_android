package net.skhu.e04firebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MemoList3Activity extends AppCompatActivity {
    private static final int REQUEST_CREATE = 0;
    private static final int REQUEST_EDIT = 1;
    MemoRecyclerView3Adapter memoRecyclerView3Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_list2);
        memoRecyclerView3Adapter = new MemoRecyclerView3Adapter(this,
                (memo) -> startMemoActivityForResult(REQUEST_EDIT, memo),
                (count) -> { if (count <= 1) invalidateOptionsMenu(); });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(memoRecyclerView3Adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_memo_list1, menu);
        MenuItem menuItem = menu.findItem(R.id.action_remove);
        menuItem.setVisible(memoRecyclerView3Adapter.checkedCount > 0);
        return true;
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_create) {
            startMemoActivityForResult(REQUEST_CREATE, null);
            return true;
        } else if (id == R.id.action_remove) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.confirm);
            builder.setMessage(R.string.doYouWantToDelete);
            builder.setPositiveButton(R.string.yes,
                    (dialog, index) -> memoRecyclerView3Adapter.removeCheckedMemo());
            builder.setNegativeButton(R.string.no, null);
            AlertDialog dialog = builder.create();
            dialog.show();
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
                memoRecyclerView3Adapter.add(memo);
            else if (requestCode == REQUEST_EDIT)
                memoRecyclerView3Adapter.update(memo);
        }
    }
    private void startMemoActivityForResult(int requestCode, Memo memo) {
        Intent intent = new Intent(this, MemoEditActivity.class);
        intent.putExtra("MEMO", memo);
        startActivityForResult(intent, requestCode);
    }
}
