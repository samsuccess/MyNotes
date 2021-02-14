package ru.example.mynotes;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class SourceImpl implements Source{

    private final List<Filling> dataSource;
    private final Resources resources; // ресурсы приложения

    public SourceImpl (Resources resources) {
        dataSource = new ArrayList<>( 20 );
        this .resources = resources;
    }

    public SourceImpl init () {
// строки заголовков из ресурсов
        String[] titles = resources.getStringArray(R.array.notes);
// строки дат из ресурсов
        String[] dates = resources.getStringArray(R.array.date);
// заполнение источника данных
        for ( int i = 0 ; i < titles.length; i++) {
            dataSource.add(new Filling(titles[i], dates[i]));
        }
        return this ;
    }

    @Override
    public Filling getFilling(int position) {
        return dataSource.get(position);
    }

    @Override
    public int size() {
        return dataSource.size();
    }
}
