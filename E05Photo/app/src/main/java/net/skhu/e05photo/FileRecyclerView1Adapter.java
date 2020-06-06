package net.skhu.e05photo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.SimpleDateFormat;

public class FileRecyclerView1Adapter extends RecyclerView.Adapter<FileRecyclerView1Adapter.ViewHolder> {
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2;
        public ViewHolder(View view) {
            super(view);
            textView1 = view.findViewById(R.id.textView1);
            textView2 = view.findViewById(R.id.textView2);
        }
        public void setData() {
            File file = files[getAdapterPosition()];
            textView1.setText(file.getName());
            String s = String.format("%s %,d bytes",
                    dateFormat.format(file.lastModified()),
                    file.length());
            textView2.setText(s);
        }
    }
    LayoutInflater layoutInflater;
    File[] files;
    public FileRecyclerView1Adapter(Context context, File[] files) {
        this.layoutInflater = LayoutInflater.from(context);
        this.files = files;
    }
    @Override
    public FileRecyclerView1Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.file, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileRecyclerView1Adapter.ViewHolder holder, int position) {
        holder.setData();
    }

    @Override
    public int getItemCount() {
        return files.length;
    }
}
