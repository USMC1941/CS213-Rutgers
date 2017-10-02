package com.rutgers.androidchess23;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * Main Activity
 *
 * @author William Chen
 * @author Chijun Sha
 */
public class MainActivity extends ActivityBase {
    Button buttonPlayChess;
    Button buttonListGames;
    Button buttonPlayback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        buttonPlayChess = (Button) findViewById(R.id.buttonPlayChess);;
        buttonListGames = (Button) findViewById(R.id.buttonListGames);;
        buttonPlayback  = (Button) findViewById(R.id.buttonPlayback);;
        //
        buttonPlayChess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this, ChessActivity.class);
                startActivity(intent);
            }
        });
        //
        buttonListGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this, ListgameActivity.class);
                startActivity(intent);
            }
        });
        //
        if (guiGame==null) {
            buttonPlayback.setVisibility(View.INVISIBLE);
        }
        else {
            buttonPlayback.setVisibility(View.VISIBLE);
        }
        buttonPlayback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this, PlaybackActivity.class);
                intent.putExtra(INTENT_DATA_KEY_FILENAME, INTENT_DATA_FILENAME_LAST);
                startActivity(intent);
            }
        });
    }
}
