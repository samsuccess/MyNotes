package ru.example.mynotes;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
        LinearLayout layoutView = (LinearLayout) view;
        String[] notes = getResources().getStringArray(R.array.notes);
        for (int i = 0; i < notes.length; i++) {
            String note = notes[i];
            TextView title = new TextView(getContext());
            title.setText(note);
            title.setTextSize(30);
            layoutView.addView(title);
            final int fi = i;
            title.setOnClickListener(v -> {
                filling = new Filling(getResources().getStringArray(R.array.notes)[fi],
                        getResources().getStringArray(R.array.date)[fi]);
                showNotes(filling);

            });
            title.setOnLongClickListener(v -> {
                Activity activity = requireActivity();
                PopupMenu popupMenu = new PopupMenu(activity, v);
                activity.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.show();
                return false;
            });
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