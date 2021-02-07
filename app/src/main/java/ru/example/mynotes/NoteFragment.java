package ru.example.mynotes;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class NoteFragment extends Fragment {

    public static final String ARG_FILLING = "filling";
    private Filling filling;
    private boolean isLandscape;

    public NoteFragment() {
        // Required empty public constructor
    }

    public static NoteFragment newInstance(Filling filling) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_FILLING, filling);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            filling = getArguments().getParcelable(ARG_FILLING);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textViewTitle = view.findViewById(R.id.title_note);
        TextView textViewDate = view.findViewById(R.id.date);
        textViewTitle.setText(filling.getTitle());
        textViewDate.setText(filling.getDate());
    }
}
