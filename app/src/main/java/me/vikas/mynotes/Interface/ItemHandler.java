package me.vikas.mynotes.Interface;

import java.util.List;

import me.vikas.mynotes.Model.Notes;

public interface ItemHandler {
    void cardClicked(int Position, int id);
    void contentClick(int position,int id);
    void onEditButtonClick(int position, int id);
    void onCopyButtonClick(int position, Notes notes);
    void onDeleteButtonClick(int position, Notes notes);

    void onCardPin(int position, Notes notes, List<Notes> list);
}
