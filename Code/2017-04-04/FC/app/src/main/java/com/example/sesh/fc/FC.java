package com.example.sesh.fc;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class FC extends Activity {

    Button f2c, c2f;
    EditText fval, cval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fc);

        f2c = findViewById(R.id.f2c);
        c2f = findViewById(R.id.c2f);
        fval = findViewById(R.id.fval);
        cval = findViewById(R.id.cval);

        /*
        f2c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // put code here to handle f2c conversion
            }
        });
         */

        // use lambdas instead
        f2c.setOnClickListener(v -> {
            // code here to handle f2c
            float fv = Float.parseFloat(fval.getText().toString());
            float cv = (fv - 32) * 5 / 9;
            String cstr = String.format(Locale.ENGLISH, "%5.1f", cv);
            cval.setText(cstr);
        });

        c2f.setOnClickListener(v -> {
            // put code here to handle c2f conversion
            float cv = Float.parseFloat(cval.getText().toString());
            float fv = cv * 9 / 5 + 32;
            String fstr = String.format(Locale.ENGLISH, "%5.1f", fv);
            fval.setText(fstr);
        });
    }
}