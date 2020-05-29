package net.skhu.e04firebase;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeMap;
public class MemoRecyclerView3Adapter extends RecyclerView.Adapter<MemoRecyclerView3Adapter.ViewHolder>
{
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            CompoundButton.OnCheckedChangeListener, View.OnLongClickListener
    {
        TextView textView1, textView2;
        CheckBox checkBox;
        public ViewHolder(View view) {
            super(view);
            textView1 = view.findViewById(R.id.textView1);
            textView2 = view.findViewById(R.id.textView2);
            checkBox = view.findViewById(R.id.checkBox);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            checkBox.setOnCheckedChangeListener(this);
        }
        public void setData() {
            Memo memo = memoList.get(getAdapterPosition());
            textView1.setText(memo.getTitle());
            textView2.setText(memo.getDateFormatted());
            checkBox.setChecked(memo.isChecked());
            checkBox.setVisibility( showCheckbox ? View.VISIBLE : View.GONE );
        }
        @Override
        public void onClick(View view) {
            selectedIndex = super.getAdapterPosition();
            onMemoClickListener.onMemoClicked(memoList.get(selectedIndex));
        }
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            memoList.get(super.getAdapterPosition()).setChecked(isChecked);
            if (isChecked) ++checkedCount; else --checkedCount;
            onCheckCountChangeListener.onCheckCountChanged(checkedCount);
        }
        @Override
        public boolean onLongClick(View v) {
            memoList.get(super.getAdapterPosition()).setChecked(true);
            showCheckbox = true;
            backPressedCallback.setEnabled(true);
            notifyDataSetChanged();
            return true;
        }
    }
    ChildEventListener firebaseListener = new ChildEventListener() {
        private int findIndex(String key) {
            return Collections.binarySearch(keyList, key);
        }
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
            String key = dataSnapshot.getKey();
            Memo memo = dataSnapshot.getValue(Memo.class);
            keyList.add(key);
            memoList.add(memo);
            notifyItemInserted(keyList.size() - 1);
        }
        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            String key = dataSnapshot.getKey();
            Memo memo = dataSnapshot.getValue(Memo.class);
            int index = findIndex(key);
            memoList.set(index, memo);
            notifyItemChanged(index);
        }
        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String key = dataSnapshot.getKey();
            int index = findIndex(key);
            keyList.remove(index);
            memoList.remove(index);
            notifyItemRemoved(index);
        }
        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };
    OnBackPressedCallback backPressedCallback = new OnBackPressedCallback(false) {
        @Override
        public void handleOnBackPressed() {
            this.setEnabled(false);
            showCheckbox = false;
            for (Memo memo : memoList)
                memo.setChecked(false);
            notifyDataSetChanged();
        }
    };
    LayoutInflater layoutInflater;
    ArrayList<String> keyList = new ArrayList<>();
    ArrayList<Memo> memoList = new ArrayList<>();
    int checkedCount = 0;
    int selectedIndex;
    OnMemoClickListener onMemoClickListener;
    OnCheckCountChangeListener onCheckCountChangeListener;
    DatabaseReference myData03;
    boolean showCheckbox = false;
    public MemoRecyclerView3Adapter(Context context, OnMemoClickListener onMemoClickListener,
                                    OnCheckCountChangeListener onCheckCountChangeListener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.onMemoClickListener = onMemoClickListener;
        this.onCheckCountChangeListener = onCheckCountChangeListener;
        this.myData03 = FirebaseDatabase.getInstance().getReference("myData03");
        this.myData03.addChildEventListener(firebaseListener);
        ((AppCompatActivity)context).getOnBackPressedDispatcher().addCallback(backPressedCallback);
    }
    @Override
    public int getItemCount() {
        return keyList.size();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.memo, viewGroup, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int index) {
        viewHolder.setData();
    }
    public void add(Memo memo) {
        String key = myData03.push().getKey();
        myData03.child(key).setValue(memo);
    }
    public void update(Memo memo) {
        String key = keyList.get(selectedIndex);
        myData03.child(key).setValue(memo);
    }
    public void removeCheckedMemo() {
        for (int i = 0; i < memoList.size(); ++i)
            if (memoList.get(i).isChecked())
                myData03.child(keyList.get(i)).removeValue();
        onCheckCountChangeListener.onCheckCountChanged(checkedCount = 0);
        showCheckbox = false;
    }
}
