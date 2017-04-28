package com.example.sesh.fc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FC extends Activity {

    Button f2c,c2f;
    EditText fval, cval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fc);

        f2c = (Button)findViewById(R.id.f2c);
        c2f = (Button)findViewById(R.id.c2f);
        fval = (EditText)findViewById(R.id.fval);
        cval = (EditText)findViewById(R.id.cval);

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
            String cstr = String.format("%5.1f", cv);
            cval.setText(cstr);
        });

        c2f.setOnClickListener(v -> {
            // put code here to handle c2f conversion
            float cv = Float.parseFloat(cval.getText().toString());
            float fv = cv * 9 / 5 + 32;
            String fstr = String.format("%5.1f", fv);
            fval.setText(fstr);
        });
    }
}
