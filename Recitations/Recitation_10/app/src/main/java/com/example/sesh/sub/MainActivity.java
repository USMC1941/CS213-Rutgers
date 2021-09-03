package com.example.sesh.sub;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showSandwich(View view) {
        String output = "";
        CheckBox cb1 = findViewById(R.id.top1);
        if (cb1.isChecked()) {
            output += getResources().getString(R.string.top1);
        }
        CheckBox cb2 = findViewById(R.id.top2);
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

        RadioGroup cheeses = findViewById(R.id.cheese_group);
        int cheeseId = cheeses.getCheckedRadioButtonId();
        if (cheeseId == R.id.cheese1) {
            output += getResources().getString(R.string.cheese1);
        } else if (cheeseId == R.id.cheese2) {
            output += getResources().getString(R.string.cheese2);
        } else if (cheeseId == R.id.cheese3) {
            output += getResources().getString(R.string.cheese3);
        }
        TextView textView = findViewById(R.id.output);
        textView.setText(output);
    }
}
