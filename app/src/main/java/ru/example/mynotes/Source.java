package ru.example.mynotes;

public interface Source {
    CardFilling getFilling(int position);
    int size();
    void deleteData ( int position);
    void updateData ( int position, CardFilling cardFilling);
    void addData (CardFilling cardFilling);
    void clearData ();
}
