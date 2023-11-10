package me.vikas.mynotes.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import me.vikas.mynotes.Config;
import me.vikas.mynotes.Model.Notes;

@Database(entities = Notes.class, exportSchema = false, version = 6)
public abstract class RoomHelper extends RoomDatabase {

    private static RoomHelper instance;

    public static RoomHelper getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, RoomHelper.class, Config.TABLE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract RoomDAO getDao();
}
