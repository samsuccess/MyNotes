package ru.example.mynotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    //    private final String[] dataSource;
    private final Source dataSource;
    private final Fragment fragment;
    private MyClickListener myClickListener;
    private MyLongClickListener myLongClickListener;
    private int menuPosition;

    public int getMenuPosition () {
        return menuPosition;
    }

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

    public MyAdapter(Source dataSource, Fragment fragment) {
        this.dataSource = dataSource;
        this.fragment = fragment;
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
        private final TextView date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            date = itemView.findViewById(R.id.date);
            registerContextMenu(itemView);
            itemView.setOnClickListener(v -> {
                if (myClickListener != null) {
                    myClickListener.onItemClick(v, getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(v -> {
                menuPosition = getLayoutPosition();
                itemView.showContextMenu( 10 , 10 );
                return true ;
            });

        }

        public void onBind(CardFilling cardFilling) {
            textView.setText(cardFilling.getTitle());
            date.setText(cardFilling.getDate());
        }

        private void registerContextMenu(@NonNull View itemView) {
            if (fragment != null) {
                itemView.setOnLongClickListener(v -> {
                    menuPosition = getLayoutPosition();
                    return false ;
                });
                fragment.registerForContextMenu(itemView);
            }
        }
    }
}
