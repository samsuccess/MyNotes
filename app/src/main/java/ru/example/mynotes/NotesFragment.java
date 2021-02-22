package ru.example.mynotes;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
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
import android.widget.Toast;


public class NotesFragment extends Fragment {

    public static final String CURRENT_FILLING = "CurrentFilling";
    private CardFilling cardFilling;
    private Source data;
    MyAdapter myAdapter;
    RecyclerView recyclerView;
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
        setHasOptionsMenu(true);
        initList(view);
    }

    private void initList(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_lines);
        data = new SourceImpl(getResources()).init();
        myAdapter = new MyAdapter(data, this);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        myAdapter.SetOnItemClickListener((view1, position) -> {
            cardFilling = data.getFilling(position);
            showNotes(cardFilling);

        });

        if (getContext() != null) {
            DividerItemDecoration itemDecoration = new
                    DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
            itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
            recyclerView.addItemDecoration(itemDecoration);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_notes, menu);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        MenuItem search = menu.findItem(R.id.action_search); // поиск пункта меню поиска
        SearchView searchText = (SearchView) search.getActionView(); // строка поиска
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // реагирует на конец ввода поиска
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(), query,
                        Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                data.addData(new CardFilling("Запись" + (data.size() + 1), "Дата" + (data.size() + 1)));
                myAdapter.notifyItemInserted(data.size() - 1);
                recyclerView.scrollToPosition(data.size() - 1);
                myAdapter.notifyItemInserted(data.size() - 1);
                recyclerView.scrollToPosition(data.size() - 1);
                return true;
            case R.id.clear:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.popup, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = myAdapter.getMenuPosition();
        switch (item.getItemId()) {
            case R.id.change:
// Do some stuff
                return true;
            case R.id.delete:
                data.deleteData(position);
                myAdapter.notifyItemRemoved(position);
                return true;
            case R.id.clear:
                data.clearData();
                myAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_FILLING, cardFilling);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            cardFilling = savedInstanceState.getParcelable(CURRENT_FILLING);
        } else {
            cardFilling = new CardFilling(getResources().getStringArray(R.array.notes)[0],
                    getResources().getStringArray(R.array.date)[0]);
        }

        if (isLandscape) {
            showLandNotes(cardFilling);
        }
    }


    private void showNotes(CardFilling cardFilling) {
        if (isLandscape) {
            showLandNotes(cardFilling);
        } else {
            showPortNotes(cardFilling);
        }
    }

    private void showPortNotes(CardFilling cardFilling) {
        NoteFragment details = NoteFragment.newInstance(cardFilling);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.note_container, details)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    private void showLandNotes(CardFilling cardFilling) {
        NoteFragment detail = NoteFragment.newInstance(cardFilling);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.note_container_land, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commitAllowingStateLoss();
    }
}