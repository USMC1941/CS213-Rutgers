package com.rutgers.androidchess23;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Activity for listing saved games
 *
 * @author William Chen
 * @author Chijun Sha
 */
public class ListgameActivity extends ActivityBase implements View.OnClickListener {
    public static final String TAG_TITLE        = "--TITLE--";
    public static final String TAG_DATE         = "--DATE--";
    public static final String TAG_NOACTION     = "--NO_ACTION--";
    public static final String TAG_DELETE       = "--DELETE--";
    public static final String TAG_PLAYBACK     = "--PLAYBACK--";

    private static final int WIDTH_TITLE        = 250;
    private static final int WIDTH_DATE         = 200;
    private static final int WIDTH_DELETE       = 40;
    private static final int HEIGHT_ALL         = 20;

    Comparator<String> title_norm = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return getTitleFromFileName(o1).toUpperCase().compareTo(getTitleFromFileName(o2).toUpperCase());
        }
    };
    Comparator<String> date_norm =  new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return getTMSFromFileName(o1).compareTo(getTMSFromFileName(o2));
        };
    };
    Comparator<String> title_rev = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return -getTitleFromFileName(o1).toUpperCase().compareTo(getTitleFromFileName(o2).toUpperCase());
        };
    };
    Comparator<String> date_rev =  new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return -getTMSFromFileName(o1).compareTo(getTMSFromFileName(o2));
        };
    };

    boolean title_sort_norm = true;
    boolean date_sort_norm = true;



    TableLayout table;
    List<String> filenameList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listgame);
        //
        table = (TableLayout) findViewById(R.id.table);
        //
        filenameList = getListFilesName(getFilesDir());
        //
        insertHeaderRow();
        insertRows();
    }


    private void insertHeaderRow() {
        TableRow tbrow = new TableRow(this);
        //
        {   TextView tv = new TextView(this);
            tv.setText("Title");
            tv.setTextColor(Color.WHITE);
            tv.setBackgroundColor(Color.BLUE);
            tv.setGravity(Gravity.LEFT);
            tv.setTextSize(15);
            tv.setTag(TAG_TITLE);
            tv.setWidth(WIDTH_TITLE);
            tv.setOnClickListener(ListgameActivity.this);
            tbrow.addView(tv);
        }
        //
        {   TextView tv = new TextView(this);
            tv.setText("Date");
            tv.setTextColor(Color.WHITE);
            tv.setBackgroundColor(Color.BLUE);
            tv.setGravity(Gravity.LEFT);
            tv.setTextSize(15);
            tv.setTag(TAG_DATE);
            tv.setWidth(WIDTH_DATE);
            tv.setOnClickListener(ListgameActivity.this);
            tbrow.addView(tv);
        }
        //
        {   TextView tv = new TextView(this);
            tv.setText(" ");
            tv.setTextColor(Color.WHITE);
            tv.setBackgroundColor(Color.BLUE);
            tv.setGravity(Gravity.LEFT);
            tv.setTextSize(15);
            tv.setWidth(WIDTH_DELETE);
            tv.setTag(TAG_NOACTION);
            tbrow.addView(tv);
        }
        //
        table.addView(tbrow);
    }
    private void insertRows() {
        for (int i = 0; i < filenameList.size(); i++) {
            TableRow tbrow = new TableRow(this);
            String fileName = filenameList.get(i);
            //
            {   TextView tv = new TextView(this);
                tv.setText(getTitleFromFileName(fileName));
                tv.setTextColor(Color.WHITE);
                tv.setGravity(Gravity.LEFT);
                tv.setWidth(WIDTH_TITLE);
                tv.setTag(TAG_PLAYBACK+fileName);
                tv.setOnClickListener(ListgameActivity.this);
                tbrow.addView(tv);
            }
            //
            {   TextView tv = new TextView(this);
                tv.setText(getTMSFromFileName(fileName));
                tv.setTextColor(Color.WHITE);
                tv.setGravity(Gravity.LEFT);
                //tv.setWidth(WIDTH_DATE);
                tv.setTag(TAG_PLAYBACK+fileName);
                tv.setOnClickListener(ListgameActivity.this);
                tbrow.addView(tv);
            }
            //
            {   Button bt = new Button(this);
                bt.setText("Delete");
                bt.setTextColor(Color.WHITE);
                bt.setGravity(Gravity.LEFT);
                bt.setHeight(HEIGHT_ALL);
                bt.setWidth(WIDTH_DELETE);
                bt.setTag(TAG_DATE);
                bt.setTag(TAG_DELETE+fileName);
                bt.setOnClickListener(ListgameActivity.this);
                tbrow.addView(bt);
            }
            //
            table.addView(tbrow);
        }
    }

    @Override
    public void onClick(View v) {
        String tag = v.getTag().toString();
        //
        if (tag.equalsIgnoreCase(TAG_TITLE)) {
            Comparator<String> comp = title_sort_norm ? title_norm : title_rev;
            title_sort_norm = !title_sort_norm;
            //
            Collections.sort(filenameList, comp);
            //
            int childCount = table.getChildCount();
            // Remove all rows except the first one
            if (childCount > 1) {
                table.removeViews(1, childCount - 1);
            }
            //
            insertRows();
        }
        else if (tag.equalsIgnoreCase(TAG_DATE)) {
            Comparator<String> comp = date_sort_norm ? date_norm : date_rev;
            date_sort_norm = !date_sort_norm;
            //
            Collections.sort(filenameList, comp);
            //
            int childCount = table.getChildCount();
            // Remove all rows except the first one
            if (childCount > 1) {
                table.removeViews(1, childCount - 1);
            }
            //
            insertRows();
        }
        else if (tag.startsWith(TAG_DELETE)) {
            for (int i = 0; i < filenameList.size(); i++) {
                String fileName = filenameList.get(i);
                //
                if ((TAG_DELETE + fileName).equals(tag)) {
                    boolean ret = deleteFile(getFilesDir(), fileName);
                    table.removeView(table.getChildAt(i + 1));
                    filenameList.remove(i);
                }
            }
        }
        else if (tag.startsWith(TAG_PLAYBACK)) {
            String fileName = tag.substring(TAG_PLAYBACK.length());
            //
            Intent intent = new Intent(ListgameActivity.this, PlaybackActivity.class);
            intent.putExtra(INTENT_DATA_KEY_FILENAME, fileName);
            startActivity(intent);
        }
    }
}
