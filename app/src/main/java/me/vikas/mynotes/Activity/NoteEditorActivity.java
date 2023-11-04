package me.vikas.mynotes.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.vikas.mynotes.Model.Notes;
import me.vikas.mynotes.R;
import me.vikas.mynotes.Room.RoomHelper;
import me.vikas.mynotes.databinding.ActivityNoteEditorBinding;

public class NoteEditorActivity extends AppCompatActivity {

    private ActivityNoteEditorBinding dataBinding;
    private RoomHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding= DataBindingUtil.setContentView(this,R.layout.activity_note_editor);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        dataBinding.tbNavigationBar.setNavigationOnClickListener(v -> this.finish());

        helper = RoomHelper.getInstance(this);

        if (getIntent().getStringExtra("noteId")!=null){
            Notes note=helper.getDao().getNote(getIntent().getStringExtra("noteId"));
            dataBinding.setNoteTitle(note.getTitle());
            dataBinding.setNoteContent(note.getContent());
            dataBinding.tbNavigationBar.setTitle("Edit Note");

            upDateNote(getIntent().getStringExtra("noteId"));

        }else{
            emptyNote();
        }


    }

    void emptyNote(){
        String currentDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());

        dataBinding.tbNavigationBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.itemSave) {
                    helper.getDao().newNote(new Notes(dataBinding.tvNoteTitle.getText().toString(), dataBinding.tvNoteContent.getText().toString(), currentDateTime));
                    finish();
                }
                if (item.getItemId() == R.id.itemDelete) {
                    MaterialAlertDialogBuilder dialog=new MaterialAlertDialogBuilder(NoteEditorActivity.this);
                    dialog.setCancelable(false)
                            .setTitle(getString(R.string.discard))
                            .setMessage(getString(R.string.confirmDiscard))
                            .setPositiveButton("Diacrd", (dialog1, which) -> {
                                finish();
                            })
                            .setNegativeButton("Cancel", (dialog1, which) -> {
                                dialog1.dismiss();
                            })
                            .show();
                }

                return true;
            }
        });

    }

    void upDateNote(String id){
        dataBinding.tbNavigationBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.itemSave) {

                    Notes newNote = helper.getDao().getNote(id);
                    newNote.setTitle(dataBinding.tvNoteTitle.getText().toString());
                    newNote.setContent(dataBinding.tvNoteContent.getText().toString());

                    helper.getDao().updateNote(newNote);
                    finish();
                }
                if (item.getItemId() == R.id.itemDelete) {
                    MaterialAlertDialogBuilder dialog=new MaterialAlertDialogBuilder(NoteEditorActivity.this);
                    dialog.setCancelable(false)
                            .setTitle("Discard changes")
                            .setMessage("Your all changes cannot be saved")
                            .setPositiveButton("Diacrd", (dialog1, which) -> {
                                finish();
                            })
                            .setNegativeButton("Cancel", (dialog1, which) -> {

                            })
                            .show();
                }

                return true;
            }
        });

    }
}