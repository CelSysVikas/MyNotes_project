package me.vikas.mynotes.Adapter;

import static androidx.core.content.ContextCompat.getColor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

import me.vikas.mynotes.Interface.ItemHandler;
import me.vikas.mynotes.Model.Notes;
import me.vikas.mynotes.R;
import me.vikas.mynotes.databinding.ItemNoteBinding;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private Context context;
    private List<Notes> list;
    private ItemHandler itemClick;
    private int[] color = {R.color.color1,
                            R.color.color2,
                            R.color.color3,
                            R.color.color4,
                            R.color.color5};

    public NotesAdapter(Context context, List<Notes> list, ItemHandler itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.noteBinding.setNoteData(list.get(position));
        Random random=new Random();

        holder.noteBinding.setNoteTitle(list.get(position).getTitle());
        holder.noteBinding.setNoteContent(list.get(position).getContent());
        holder.noteBinding.setNoteDate(list.get(position).getDateTime());

        if (list.get(position).isNotePinned())
            holder.noteBinding.cvBackground.setBackgroundColor(context.getColor(R.color.cardPinned));
        else {
            int colorResId = color[random.nextInt(color.length)];
            holder.noteBinding.cvBackground.setBackgroundColor(getColor(context, colorResId));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ItemNoteBinding noteBinding;

        public ViewHolder(@NonNull ItemNoteBinding noteBinding) {
            super(noteBinding.getRoot());
            this.noteBinding = noteBinding;

            noteBinding.llNoteData.setOnClickListener(v -> itemClick.contentClick(getAdapterPosition(), list.get(getAdapterPosition()).getId()));
            noteBinding.ibCopy.setOnClickListener(v -> itemClick.onCopyButtonClick(getAdapterPosition(), list.get(getAdapterPosition())));
            noteBinding.ibDelete.setOnClickListener(v -> itemClick.onDeleteButtonClick(getAdapterPosition(), list.get(getAdapterPosition())));
            noteBinding.ibEdit.setOnClickListener(v -> itemClick.onEditButtonClick(getAdapterPosition(), list.get(getAdapterPosition()).getId()));
            noteBinding.ibPin.setOnClickListener(v-> itemClick.onCardPin(getAdapterPosition(), list.get(getAdapterPosition())));
        }
    }
}
