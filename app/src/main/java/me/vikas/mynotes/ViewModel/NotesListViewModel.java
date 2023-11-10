package me.vikas.mynotes.ViewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import me.vikas.mynotes.AppPreference;
import me.vikas.mynotes.Model.Notes;
import me.vikas.mynotes.Room.NoteRepository;
import me.vikas.mynotes.Room.RoomHelper;

public class NotesListViewModel extends AndroidViewModel {

    private NoteRepository repository;
    public LiveData<List<Notes>> list;

    public NotesListViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        list=repository.listNotes;
    }

    public void insertNotes(Notes notes){
        repository.insertNotes(notes);
    }

    public void updateNotes(Notes notes){
        repository.updateNotes(notes);
    }

    public void deleteNote(Notes notes){
        repository.deleteNotes(notes);
    }

    public LiveData<Notes> getNote(String id){
       return repository.getNote(id);
    }


}
