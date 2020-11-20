package com.example.gridview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class ImageGridView extends AppCompatActivity {

   private ImageAdapter myImgAdapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.grid_view);

      myImgAdapter = new ImageAdapter(this);
      GridView gridview = (GridView) findViewById(R.id.gridview);
      gridview.setAdapter(myImgAdapter);

      gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.toast_layout,
                  (ViewGroup) findViewById(R.id.relativeLayout1));
            view.setBackgroundResource(myImgAdapter.getImgID(position));

            Toast toast = new Toast(parent.getContext());
            toast.setView(view);
            toast.show();
         }
      });
   }
}
