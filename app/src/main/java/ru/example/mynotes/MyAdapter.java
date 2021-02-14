package ru.example.mynotes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

//    private final String[] dataSource;
    private final Source dataSource;
    private MyClickListener myClickListener;
    private MyLongClickListener myLongClickListener;

    public void SetOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public void setMyLongClickListener(MyLongClickListener myLongClickListener) {
        this.myLongClickListener = myLongClickListener;
    }

    public interface MyClickListener {
        void onItemClick(View view, int position);
    }

    public interface MyLongClickListener {
        void onLongItemClick(View view, int position);
    }

    public MyAdapter(Source dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.onBind(dataSource.getFilling(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            textView.setOnClickListener(v -> {
                if (myClickListener != null) {
                    myClickListener.onItemClick(v, getAdapterPosition());
                }
            });
            textView.setOnLongClickListener(v -> {
                if (myLongClickListener != null) {
                    myLongClickListener.onLongItemClick(v, getAdapterPosition());
                }

                return false;
            });
        }

        public void onBind(Filling filling) {
            textView.setText(filling.getTitle());
        }
    }
}
