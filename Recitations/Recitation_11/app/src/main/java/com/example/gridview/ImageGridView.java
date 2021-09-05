package com.example.gridview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ImageGridView extends AppCompatActivity {

    private ImageAdapter myImgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view);

        myImgAdapter = new ImageAdapter(this);
        GridView gridview = findViewById(R.id.gridview);
        gridview.setAdapter(myImgAdapter);

        gridview.setOnItemClickListener((parent, v, position, id) -> {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.toast_layout,
                    findViewById(R.id.relativeLayout1));
            view.setBackgroundResource(myImgAdapter.getImgID(position));

            Toast toast = new Toast(parent.getContext());
            toast.setView(view);
            toast.show();
        });
    }
}
