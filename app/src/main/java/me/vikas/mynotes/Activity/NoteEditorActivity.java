package me.vikas.mynotes.Activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.vikas.mynotes.Model.Notes;
import me.vikas.mynotes.R;
import me.vikas.mynotes.Room.RoomHelper;
import me.vikas.mynotes.databinding.ActivityNoteEditorBinding;

public class NoteEditorActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityNoteEditorBinding dataBinding;
    private RoomHelper helper;
    private int colorFromButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_note_editor);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        dataBinding.tbNavigationBar.setNavigationOnClickListener(v -> this.finish());
        helper = RoomHelper.getInstance(this);

        if (getIntent().getStringExtra("noteId") != null)
            initEditor(getIntent().getStringExtra("noteId"));
        else
            emptyNote();

    }

    private void initEditor(String noteID) {

        Notes note = helper.getDao().getNote(noteID);
        dataBinding.setNoteTitle(note.getTitle());
        dataBinding.setNoteContent(note.getContent());
        dataBinding.tbNavigationBar.setTitle("Edit Note");

        if (note.getColorCode() != 0)
            initColor(getResources().getColor(note.getColorCode()));

        upDateNote(getIntent().getStringExtra("noteId"));
    }

    private void initColor(int color) {
        if (color != 0) {
            dataBinding.parent.setBackgroundColor(color);
            dataBinding.tbNavigationBar.setBackgroundColor(color);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    void emptyNote() {

        String currentDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());

        dataBinding.tbNavigationBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.itemSave) {
                    if (!dataBinding.tvNoteTitle.getText().toString().trim().isEmpty() || !dataBinding.tvNoteContent.getText().toString().trim().isEmpty()) {
                        Notes newNote=new Notes(dataBinding.tvNoteTitle.getText().toString(), dataBinding.tvNoteContent.getText().toString(), currentDateTime);
                        if (colorFromButton!=0)
                            newNote.setColorCode(colorFromButton);
                        helper.getDao().newNote(newNote);
                        finish();
                    } else {
                        dataBinding.tvNoteTitle.setError("");
                        Toast.makeText(NoteEditorActivity.this, getString(R.string.emptyNotes), Toast.LENGTH_SHORT).show();
                    }
                }
                if (item.getItemId() == R.id.itemDelete) {
                    MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(NoteEditorActivity.this);
                    dialog.setCancelable(false)
                            .setTitle(getString(R.string.discard))
                            .setMessage(getString(R.string.confirmDiscard))
                            .setPositiveButton("Discard", (dialog1, which) -> {
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

    void upDateNote(String id) {
        dataBinding.tbNavigationBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.itemSave) {

                    Notes newNote = helper.getDao().getNote(id);
                    newNote.setTitle(dataBinding.tvNoteTitle.getText().toString());
                    newNote.setContent(dataBinding.tvNoteContent.getText().toString());
                    newNote.setColorCode(colorFromButton);

                    helper.getDao().updateNote(newNote);
                    finish();
                }
                if (item.getItemId() == R.id.itemDelete) {
                    MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(NoteEditorActivity.this);
                    dialog.setCancelable(false)
                            .setTitle(getString(R.string.discard))
                            .setMessage(getString(R.string.confirmDiscard))
                            .setPositiveButton("Discard", (dialog1, which) -> {
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

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnColor1){
            colorFromButton=R.color.color1;
            initColor(getResources().getColor(colorFromButton));
        } else if (v.getId() == R.id.btnColor2) {
            colorFromButton=R.color.color3;
            initColor(getResources().getColor(colorFromButton));
        }else if (v.getId() == R.id.btnColor3) {
            colorFromButton=R.color.color4;
            initColor( getResources().getColor(colorFromButton));
        }else if (v.getId() == R.id.btnColor4) {
            colorFromButton=R.color.color5;
            initColor(getResources().getColor(colorFromButton));
        }
    }
}