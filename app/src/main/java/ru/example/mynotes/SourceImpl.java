package ru.example.mynotes;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class SourceImpl implements Source {

    private final List<CardFilling> dataSource;
    private final Resources resources; // ресурсы приложения

    public SourceImpl(Resources resources) {
        dataSource = new ArrayList<>();
        this.resources = resources;
    }

    public SourceImpl init() {
// строки заголовков из ресурсов
        String[] titles = resources.getStringArray(R.array.notes);
// строки дат из ресурсов
        String[] dates = resources.getStringArray(R.array.date);
// заполнение источника данных
        for (int i = 0; i < titles.length; i++) {
            dataSource.add(new CardFilling(titles[i], dates[i]));
        }
        return this;
    }

    @Override
    public CardFilling getFilling(int position) {
        return dataSource.get(position);
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public void deleteData(int position) {
        dataSource.remove(position);
    }

    @Override
    public void updateData(int position, CardFilling cardFilling) {
        dataSource.set(position, cardFilling);
    }

    @Override
    public void addData(CardFilling cardFilling) {
        dataSource.add(cardFilling);
    }

    @Override
    public void clearData() {
        dataSource.clear();
    }
}
