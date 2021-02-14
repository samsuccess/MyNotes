package ru.example.mynotes;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;


public class NotesFragment extends Fragment {

    public static final String CURRENT_FILLING = "CurrentFilling";
    private Filling filling;

    //    private int currentPosition = 0;
    private boolean isLandscape;

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }


    private void initList(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);
        Source data = new SourceImpl(getResources()).init();
        MyAdapter myAdapter = new MyAdapter(data);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        myAdapter.SetOnItemClickListener((view1, position) -> {

            filling = new Filling(getResources().getStringArray(R.array.notes)[position],
                    getResources().getStringArray(R.array.date)[position]);
            showNotes(filling);

        });
        myAdapter.setMyLongClickListener((view12, position) -> {
            Activity activity = requireActivity();
            PopupMenu popupMenu = new PopupMenu(activity, view12);
            activity.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
            popupMenu.show();
        });

        if (getContext() != null) {
            DividerItemDecoration itemDecoration = new
                    DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
            itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator,
                    null));
            recyclerView.addItemDecoration(itemDecoration);
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_FILLING, filling);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            filling = savedInstanceState.getParcelable(CURRENT_FILLING);
        } else {
            filling = new Filling(getResources().getStringArray(R.array.notes)[0],
                    getResources().getStringArray(R.array.date)[0]);
        }

        if (isLandscape) {
            showLandNotes(filling);
        }
    }


    private void showNotes(Filling filling) {
        if (isLandscape) {
            showLandNotes(filling);
        } else {
            showPortNotes(filling);
        }
    }

    private void showPortNotes(Filling filling) {
        NoteFragment details = NoteFragment.newInstance(filling);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.note_container, details)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    private void showLandNotes(Filling filling) {
        NoteFragment detail = NoteFragment.newInstance(filling);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.popBackStack();
        fragmentTransaction.replace(R.id.note_container_land, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commitAllowingStateLoss();
    }
}