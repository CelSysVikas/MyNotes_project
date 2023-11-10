package me.vikas.mynotes.Room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import me.vikas.mynotes.Model.Notes;

public class NoteRepository {
    private RoomDAO roomDAO;
    private RoomHelper helper;
    public LiveData<List<Notes>> listNotes;

    public NoteRepository(Application application) {
        helper = RoomHelper.getInstance(application);
        roomDAO = helper.getDao();
        listNotes = roomDAO.getNotes();
    }

    public LiveData<Notes> getNote(String id){
       return roomDAO.getNoteLive(id);
    }

    public void insertNotes(Notes notes){
        roomDAO.newNote(notes);
    }

    public void deleteNotes(Notes notes){
        roomDAO.deleteNote(notes);
    }

    public void updateNotes(Notes notes){
        updateNotes(notes);
    }

}
