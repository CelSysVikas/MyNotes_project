package me.vikas.mynotes.Interface;

import me.vikas.mynotes.Model.Notes;

public interface ItemHandler {
    void cardClicked(int Position, int id);
    void contentClick(int position,int id);
    void onEditButtonClick(int position, int id);
    void onCopyButtonClick(int position, Notes notes);
    void onDeleteButtonClick(int position, Notes notes);
}
