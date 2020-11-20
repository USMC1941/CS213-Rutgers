package com.example.sub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
   }

   public void showSandwich(View view) {
      String output = "";
      CheckBox cb1 = (CheckBox) findViewById(R.id.top1);
      if (cb1.isChecked()) {
         output += getResources().getString(R.string.top1);
      }
      CheckBox cb2 = (CheckBox) findViewById(R.id.top2);
      if (cb2.isChecked()) {
         if (output.length() != 0) {
            output += ", ";
         }
         output += getResources().getString(R.string.top2);
      }
      if (output.length() == 0) {
         output = "No cheese";
      }
      output += "\n";

      RadioGroup cheeses = (RadioGroup) findViewById(R.id.cheese_group);
      int cheeseId = cheeses.getCheckedRadioButtonId();
      switch (cheeseId) {
         case R.id.cheese1:
            output += getResources().getString(R.string.cheese1);
            break;
         case R.id.cheese2:
            output += getResources().getString(R.string.cheese2);
            break;
         case R.id.cheese3:
            output += getResources().getString(R.string.cheese3);
            break;
      }
      TextView textView = (TextView) findViewById(R.id.output);
      textView.setText(output);
   }
}
