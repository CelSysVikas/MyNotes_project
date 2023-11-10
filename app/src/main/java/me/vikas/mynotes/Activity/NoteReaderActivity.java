package me.vikas.mynotes.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.palette.graphics.Palette;

import me.vikas.mynotes.Model.Notes;
import me.vikas.mynotes.R;
import me.vikas.mynotes.Room.RoomHelper;
import me.vikas.mynotes.databinding.ActivityNoteReaderBinding;

public class NoteReaderActivity extends AppCompatActivity {

    private static final String TAG = "NoteReaderActivity";
    private ActivityNoteReaderBinding dataBinding;
    private String noteID;
    private Palette.Swatch lightVibrentSwatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_note_reader);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        dataBinding.navigationView.setNavigationOnClickListener(v -> finish());

        noteID = getIntent().getStringExtra("noteID");

        dataBinding.fabEditNote.setOnClickListener(v -> {
            Intent toEdit = new Intent(this, NoteEditorActivity.class);
            toEdit.putExtra("noteId", noteID);
            startActivity(toEdit);
        });

        initData();

    }

    private void initData() {
        RoomHelper helper = RoomHelper.getInstance(this);
        LiveData<Notes> note = helper.getDao().getNoteLive(noteID);

        note.observe(this, notes -> {
            dataBinding.setNoteContent(notes.getContent());
            dataBinding.setNoteTitle(notes.getTitle());
            dataBinding.setNoteDate("Created On: " + notes.getDateTime());
            if (notes.getColorCode() != 0)
                initColor(getResources().getColor(notes.getColorCode()));
        });
    }

    private void initColor(int color) {

        dataBinding.parent.setBackgroundColor(color);
        dataBinding.navigationView.setBackgroundColor(color);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(color);

    }

//    private Bitmap createBitmapFromColor(int color) {
//        int width = 100; // Set your desired width
//        int height = 100; // Set your desired height
//
//        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//
//        canvas.drawColor(color);
//
//        return bitmap;
//    }
}