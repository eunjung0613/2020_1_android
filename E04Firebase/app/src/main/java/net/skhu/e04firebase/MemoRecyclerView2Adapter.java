package net.skhu.e04firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MemoRecyclerView2Adapter  extends RecyclerView.Adapter<MemoRecyclerView2Adapter.ViewHolder> {
    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, View.OnLongClickListener {
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
            Memo memo = arrayList.get(getAdapterPosition());
            textView1.setText(memo.getTitle());
            textView2.setText(memo.getDateFormatted());
            checkBox.setChecked(memo.isChecked());
            checkBox.setVisibility( showCheckbox ? View.VISIBLE : View.GONE );
        }
        @Override
        public void onClick(View view) {
            selectedIndex = super.getAdapterPosition();
            onMemoClickListener.onMemoClicked(arrayList.get(selectedIndex));
        }
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Memo memo = arrayList.get(super.getAdapterPosition());
            memo.setChecked(isChecked);
            if (isChecked) ++checkedCount;
            else --checkedCount;
            onCheckCountChangeListener.onCheckCountChanged(checkedCount);
        }
        @Override
        public boolean onLongClick(View v) {
            arrayList.get(super.getAdapterPosition()).setChecked(true);
            showCheckbox = true;
            backPressedCallback.setEnabled(true);
            notifyDataSetChanged();
            return true;
        }
    }
    ValueEventListener firebaseListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            GenericTypeIndicator<List<Memo>> typeIndicator = new GenericTypeIndicator<List<Memo>>() {};
            List<Memo> temp = dataSnapshot.getValue(typeIndicator);
            if (temp != null) {
                arrayList = (ArrayList<Memo>) temp;
                notifyDataSetChanged();
            }
        }
        @Override
        public void onCancelled(DatabaseError error) {
        }
    };
    OnBackPressedCallback backPressedCallback = new OnBackPressedCallback(false) {
        @Override
        public void handleOnBackPressed() {
            this.setEnabled(false);
            showCheckbox = false;
            for (Memo memo : arrayList)
                memo.setChecked(false);
            notifyDataSetChanged();
        }
    };

    LayoutInflater layoutInflater;
    ArrayList<Memo> arrayList;
    int checkedCount = 0;
    int selectedIndex;
    OnMemoClickListener onMemoClickListener;
    OnCheckCountChangeListener onCheckCountChangeListener;
    DatabaseReference myData02;
    boolean showCheckbox = false;

    public MemoRecyclerView2Adapter(Context context, OnMemoClickListener onMemoClickListener,
                                    OnCheckCountChangeListener onCheckCountChangeListener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<Memo>();
        this.onMemoClickListener = onMemoClickListener;
        this.onCheckCountChangeListener = onCheckCountChangeListener;
        this.myData02 = FirebaseDatabase.getInstance().getReference("myData02");
        this.myData02.addValueEventListener(firebaseListener);
        ((AppCompatActivity)context).getOnBackPressedDispatcher().addCallback(backPressedCallback);
    }

    @Override
    public MemoRecyclerView2Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.memo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoRecyclerView2Adapter.ViewHolder holder, int position) {
        holder.setData();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public void add(Memo memo) {
        arrayList.add(memo);
        myData02.setValue(arrayList);
    }
    public void update(Memo memo) {
        arrayList.set(selectedIndex, memo);
        myData02.setValue(arrayList);
    }
    public void removeCheckedMemo() {
        ListIterator<Memo> iterator = arrayList.listIterator();
        while (iterator.hasNext())
            if (iterator.next().isChecked())
                iterator.remove();
        onCheckCountChangeListener.onCheckCountChanged(checkedCount = 0);
        showCheckbox = false;
        myData02.setValue(arrayList);
    }

}
