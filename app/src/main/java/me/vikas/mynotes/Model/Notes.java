package me.vikas.mynotes.Model;

import android.annotation.SuppressLint;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.vikas.mynotes.Config;

@Entity(tableName = Config.TABLE_NAME)
public class Notes {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = Config.COLUMN_TITLE)
    private String title;

    @ColumnInfo(name = Config.COLUMN_CONTENT)
    private String content;

    @ColumnInfo(name = Config.COLUMN_DATE_TIME)
    private String dateTime;

    @ColumnInfo(name=Config.COLUMN_COLOR_CODE)
    private int colorCode;

    @ColumnInfo(name = Config.COLUMN_NOTE_PINNED)
    private boolean isNotePinned;


    public Notes(String title, String content, String dateTime) {
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
        this.isNotePinned=false;
    }

    public static Notes copyNote(Notes notes) {
        @SuppressLint("SimpleDateFormat") String currentDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());
        return new Notes(notes.title, notes.content, currentDateTime);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isNotePinned() {
        return isNotePinned;
    }

    public void setNotePinned(boolean notePinned) {
        isNotePinned = notePinned;
    }

    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }
}
