package me.vikas.mynotes.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import me.vikas.mynotes.Adapter.NotesAdapter;
import me.vikas.mynotes.Interface.ItemHandler;
import me.vikas.mynotes.Model.Notes;
import me.vikas.mynotes.R;
import me.vikas.mynotes.Room.RoomHelper;
import me.vikas.mynotes.ViewModel.NotesListViewModel;
import me.vikas.mynotes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements ItemHandler {
    private ActivityMainBinding dataBinding;
    private RoomHelper helper;
    private NotesListViewModel listViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        dataBinding.fabAddNote.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, NoteEditorActivity.class)));

        initNavbar();
        initRecyclerView();

    }

    private void initRecyclerView() {
        dataBinding.rvNotes.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        helper = RoomHelper.getInstance(this);
        helper.getDao().getNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                if (!notes.isEmpty()) {
                    dataBinding.tvNoData.setVisibility(View.GONE);
                    dataBinding.rvNotes.setVisibility(View.VISIBLE);
                    dataBinding.rvNotes.setAdapter(new NotesAdapter(MainActivity.this, notes, MainActivity.this));
                }
            }
        });
    }

    private void initNavbar() {
        dataBinding.navigationBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.navSettings) {
                startActivity(new Intent(MainActivity.this, AppSettingsActivity.class));
                return true;
            }
            if (item.getItemId() == R.id.navSearch) {
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        List<Notes> list = helper.getDao().searchData("%"+query+"%");
                        dataBinding.rvNotes.setAdapter(new NotesAdapter(MainActivity.this,list,MainActivity.this));
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
            }
            return false;
        });
    }

    @Override
    public void cardClicked(int Position, int id) {

    }

    @Override
    public void contentClick(int position, int id) {
        Intent toReader = new Intent(this, NoteReaderActivity.class);
        toReader.putExtra("noteID", String.valueOf(id));
        startActivity(toReader);
    }

    @Override
    public void onEditButtonClick(int position, int id) {
        Intent toEditor = new Intent(MainActivity.this, NoteEditorActivity.class);
        toEditor.putExtra("noteId", String.valueOf(id));
        startActivity(toEditor);
    }

    @Override
    public void onCopyButtonClick(int position, Notes notes) {
        helper.getDao().newNote(Notes.copyNote(notes));
    }

    @Override
    public void onDeleteButtonClick(int position, Notes notes) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this);
        builder.setCancelable(false)
                .setTitle(getString(R.string.warn))
                .setMessage(getString(R.string.conformWarn))
                .setPositiveButton("Delete", (dialog, which) -> {
                    helper.getDao().deleteNote(notes);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void onCardPin(int position, Notes notes) {
        if (!notes.isNotePinned())
            notes.setNotePinned(true);
        else
            notes.setNotePinned(false);

        helper.getDao().updateNote(notes);
        dataBinding.rvNotes.getAdapter().notifyItemChanged(position);
    }
}