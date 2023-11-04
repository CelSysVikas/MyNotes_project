package me.vikas.mynotes.Room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import me.vikas.mynotes.Model.Notes;

public class NoteRepository {
    private RoomDAO roomDAO;
    private RoomHelper helper;
    private LiveData<List<Notes>> listLiveData;

    public NoteRepository(Application application){
        helper=RoomHelper.getInstance(application);
        roomDAO= helper.getDao();
        listLiveData=roomDAO.getNotes();
    }
}
