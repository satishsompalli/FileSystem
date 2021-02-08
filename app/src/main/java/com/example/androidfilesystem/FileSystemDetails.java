package com.example.androidfilesystem;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.load.model.GlideUrl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;
import java.util.Date;

public class FileSystemDetails extends AppCompatActivity {
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_system_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        FileSystem fs = (FileSystem) intent.getSerializableExtra("item");
        Toast.makeText(this, fs.getFilePath(), Toast.LENGTH_SHORT);
        file = new File(fs.getFilePath());
        Date createdDate = new Date(file.lastModified());
        // Date lastModDate = new Date(file.);
        TextView fileSizeId = findViewById(R.id.fileSizeId);
        TextView createdDateId = findViewById(R.id.createdDateId);
        TextView modifiedDateId = findViewById(R.id.modifiedDateId);
        fileSizeId.setText(fs.getFilePath());
        modifiedDateId.setText(createdDate.toString());
        ImageView imageView = findViewById(R.id.imageView);
        if(isValidImage.accept(file)){
            Picasso.get().load(file).into(imageView);
        }else{
            imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        Date createdDate = new Date(file.lastModified());
        Toast.makeText(this, createdDate.toString(), Toast.LENGTH_SHORT);
    }

    /** The is valid image. */
    public static FileFilter isValidImage = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            final String name = pathname.getName();
            String ext = null;
            int i = name.lastIndexOf('.');


            if (i > 0 && i < name.length() - 1) {
                ext = name.substring(i + 1).toLowerCase();
            }
            if (ext == null)
                return false;
            else if (!ext.equals("jpg") && !ext.equals("jpeg") && !ext.equals("png") && !ext.equals("gif"))
                return false;
            else
                return true;
        }
    };

}