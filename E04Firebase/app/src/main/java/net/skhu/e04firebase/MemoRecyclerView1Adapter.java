package net.skhu.e04firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.ListIterator;

public class MemoRecyclerView1Adapter extends RecyclerView.Adapter<MemoRecyclerView1Adapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        TextView textView1, textView2;
        CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);
            textView1 = view.findViewById(R.id.textView1);
            textView2 = view.findViewById(R.id.textView2);
            checkBox = view.findViewById(R.id.checkBox);
            view.setOnClickListener(this);
            checkBox.setOnCheckedChangeListener(this);
        }

        public void setData() {
            Memo memo = arrayList.get(getAdapterPosition());
            textView1.setText(memo.getTitle());
            textView2.setText(memo.getDateFormatted());
            checkBox.setChecked(memo.isChecked());
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
    }

    LayoutInflater layoutInflater;
    ArrayList<Memo> arrayList;
    int checkedCount = 0;
    int selectedIndex;
    OnMemoClickListener onMemoClickListener;
    OnCheckCountChangeListener onCheckCountChangeListener;

    public MemoRecyclerView1Adapter(Context context, OnMemoClickListener onMemoClickListener,
                                    OnCheckCountChangeListener onCheckCountChangeListener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<Memo>();
        this.onMemoClickListener = onMemoClickListener;
        this.onCheckCountChangeListener = onCheckCountChangeListener;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
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
        arrayList.add(memo);
        notifyItemInserted(arrayList.size() - 1);
    }

    public void update(Memo memo) {
        arrayList.set(selectedIndex, memo);
        notifyItemChanged(selectedIndex);
    }

    public void removeCheckedMemo() {
        ListIterator<Memo> iterator = arrayList.listIterator();
        while (iterator.hasNext())
            if (iterator.next().isChecked())
                iterator.remove();
        onCheckCountChangeListener.onCheckCountChanged(checkedCount = 0);
        notifyDataSetChanged();
    }
}
