package me.vikas.mynotes.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import me.vikas.mynotes.Config;
import me.vikas.mynotes.Model.Notes;

@Dao
public interface RoomDAO {

    @Query("Select * from "+ Config.TABLE_NAME)
    LiveData<List<Notes>> getNotes();

    @Insert
    void newNote(Notes notes);

    @Update
    void updateNote(Notes notes);

    @Delete
    void deleteNote(Notes notes);

    @Query("Select * from "+Config.TABLE_NAME+ " where "+
            Config.COLUMN_ID+" = :id")
    Notes getNote(String id);

    @Query("Select * from "+Config.TABLE_NAME+ " where "+
            Config.COLUMN_ID+" = :id")
    LiveData<Notes> getNoteLive(String id);

    @Query("Select * from "+Config.TABLE_NAME+ " where "+
            Config.COLUMN_TITLE+" like :query "+
            " or " +
            Config.COLUMN_CONTENT+" like :query")
    List<Notes> searchData(String query);

    @Query("Delete from "+Config.TABLE_NAME)
    void deleteData();

}
