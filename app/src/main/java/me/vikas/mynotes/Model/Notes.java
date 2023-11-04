package me.vikas.mynotes.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    public Notes(String title, String content, String dateTime) {
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
    }

    public static Notes copyNote(Notes notes) {
        return new Notes(notes.title, notes.content, notes.dateTime);
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
}
