package ru.example.mynotes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class NoteFragment extends Fragment {

    private static final String ARG_FILLING = "filling";
    private Filling filling;

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
            filling =getArguments().getParcelable(ARG_FILLING);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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