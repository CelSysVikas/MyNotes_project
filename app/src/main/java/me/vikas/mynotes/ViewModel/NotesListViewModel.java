package me.vikas.mynotes.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import me.vikas.mynotes.Model.Notes;
import me.vikas.mynotes.Room.RoomHelper;

public class NotesListViewModel extends ViewModel {

    //    private RoomHelper helper=RoomHelper.getInstance();
    private LiveData<List<Notes>> list;

//    public NotesListViewModel(@NonNull Context application) {
//        helper=RoomHelper.getInstance(application);
//    }

//    public void insertNotes(Notes notes){
//        helper.getDao().newNote(notes);
//    }
//
//    public void updateNotes(Notes notes){
//        helper.getDao().updateNote(notes);
//    }
//
//    public void deleteNote(Notes notes){
//        helper.getDao().deleteNote(notes);
//    }
//
//    public void get(Notes notes){
//        helper.getDao().newNote(notes);
//    }

    public LiveData<List<Notes>> getNotes(Context context) {
        return RoomHelper.getInstance(context).getDao().getNotes();
    }


}
