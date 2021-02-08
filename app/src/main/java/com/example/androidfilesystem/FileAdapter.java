package com.example.androidfilesystem;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {
    private List<FileSystem> list;
    private Context context;

    public FileAdapter(List<FileSystem> list, Context context) {
        this.list = list;
        this.context =context;
    }

    public void setList(List<FileSystem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View contactView = inflater.inflate(R.layout.file_row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FileAdapter.ViewHolder holder, int position) {
        FileSystem file = list.get(position);

        // Set item views based on your views and data model
        // fileNameTextView = holder.fileNameTextView;
        TextView filePathTextView = holder.filePathTextView;
        //fileNameTextView.setText(file.getFileName());
        filePathTextView.setText(file.getFilePath());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView fileNameTextView;
        public TextView filePathTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            //fileNameTextView = (TextView) itemView.findViewById(R.id.fileNameId);
            filePathTextView = (TextView) itemView.findViewById(R.id.filePathId);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =  new Intent(context, FileSystemDetails.class);
                    intent.putExtra("item", list.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }

    public void sort(SortingDialog.SortingType sortingType) {
        switch (sortingType) {
            case ALPHABETICAL:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    list.sort(Comparator.comparing(FileSystem::getFilePath));
                }
                break;
            case EXTENSION:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    list.sort(Comparator.comparing(FileSystem::getExtension,Comparator.nullsLast(Comparator.naturalOrder())));
                }
                break;
        }
        notifyDataSetChanged();
    }
}
