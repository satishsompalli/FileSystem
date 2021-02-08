package com.example.androidfilesystem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity implements SortingDialog.OnSortingSelectedListener {
    private List<File> files = new ArrayList<>();
    private List<FileSystem> list = new ArrayList<>();
    private SortingDialog.SortingType sortingType = SortingDialog.SortingType.ALPHABETICAL;

    public static final String PERMISSION_READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";

    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 2;
    private File path = null;
    private FileAdapter adapter;

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private EditText searchView;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinkedHashMap<String, String> storages = Utils.getExternalStoragePaths(this);
        if (storages.size() > 0) {
            path = new File((String) storages.keySet().toArray()[0]);
        }
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchView = findViewById(R.id.searchEditText);
        recyclerView = (RecyclerView) findViewById(R.id.rvId);
        adapter = new FileAdapter(list,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (ContextCompat.checkSelfPermission(this, PERMISSION_READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            readDirectory(path);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{PERMISSION_READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
        }
    }

    private void readDirectory(File directory) {
        File[] files = directory.listFiles();
        recursiveFileFind(files);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_READ_EXTERNAL_STORAGE:
                readDirectory(path);
                break;
            case REQUEST_CODE_WRITE_EXTERNAL_STORAGE:
                saveToFile();
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onSortingSelected(SortingDialog.SortingType sortingType) {
        this.sortingType = sortingType;
        adapter.sort(sortingType);
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.sort) {
            SortingDialog dialog = new SortingDialog(this);
            dialog.setOnSortingSelectedListener(this);
            dialog.show();
        } else if (itemId == R.id.save) {
            if (ContextCompat.checkSelfPermission(this, PERMISSION_WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                saveToFile();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{PERMISSION_WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    public void recursiveFileFind(File[] file1) {
        int i = 0;
        String filePath = "";
        String fileName = "";
        String fileExt = "";
        if (file1 != null) {
            while (i != file1.length) {
                filePath = file1[i].getAbsolutePath();
                files.add(file1[i]);
                if (file1[i].isDirectory()) {
                    File file[] = file1[i].listFiles();
                    recursiveFileFind(file);
                } else {
                    Uri uri = Uri.fromFile(new File(filePath));
                    fileExt = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
                    if (fileExt != null && fileExt.trim().length() > 0) {
                        fileName = file1[i].getName();
                        FileSystem fileSystem = new FileSystem();
                        fileSystem.setFileName(fileName);
                        fileSystem.setFilePath(filePath);
                        fileSystem.setExtension(fileExt);
                        list.add(fileSystem);
                    }
                }
                Log.d(i + "", filePath + " - " + fileName + " " + fileExt);
                i++;
            }
        }
        adapter.setList(list);
    }

    private void saveToFile() {
        File dir = new File(getFilesDir(), "mydir");
        if (!dir.exists()) {
            dir.mkdir();
        }
        try {
            File gpxfile = new File(dir, "taskResults");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(list.toString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Stored to " + dir.getAbsolutePath(), Toast.LENGTH_LONG).show();
    }
}