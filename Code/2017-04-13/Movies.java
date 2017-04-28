package com.example.sesh.movies;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.example.sesh.movies.R.layout.movie;

class Movie {
    String name;
    String year;
    String director;
    Movie(String name, String year) {
        this.name = name;
        this.year = year;
        this.director = "";
    }
    Movie(String name, String year, String director) {
        this(name, year);
        this.director = director;
    }
    public String toString() {   // used by ListView
        return name + "\n(" + year + ")";
    }
    public String getString() {
        return name + "|" + year + "|" + director;
    }
}

public class Movies extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Movie> movies;

    public static final int EDIT_MOVIE_CODE = 1;
    public static final int ADD_MOVIE_CODE = 2;

    int searchListStartPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_list);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        try {
            FileInputStream fis = openFileInput("movies.dat");
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(fis));
            String movieInfo = null;
            movies = new ArrayList<Movie>();
            while ((movieInfo = br.readLine()) != null) {
                String[] tokens = movieInfo.split("\\|");

                if (tokens.length == 3) {
                    movies.add(new Movie(tokens[0], tokens[1], tokens[2]));

                } else {
                    movies.add(new Movie(tokens[0], tokens[1]));
                }
            }
        } catch (IOException e) {
            // load from bootstrap list in string resources
            String[] moviesList = getResources().getStringArray(R.array.movies_array);
            movies = new ArrayList<Movie>(moviesList.length);
            for (int i = 0; i < moviesList.length; i++) {
                String[] tokens = moviesList[i].split("\\|");
                movies.add(new Movie(tokens[0], tokens[1]));
            }
        }

        listView = (ListView) findViewById(R.id.movies_list);
        handleIntent(getIntent());
    }

    private void showMovieList() {
        listView.setAdapter(new ArrayAdapter<Movie>(this, movie, movies));

        // show movie for possible edit when tapped
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showMovie(position);
            }
        });
    }

    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query =
                    intent.getStringExtra(SearchManager.QUERY);
            showSearchResults(query);
        } else {
            showMovieList();
        }
    }

    private void showMovie(int pos) {
        Bundle bundle = new Bundle();
        Movie movie = movies.get(pos);
        bundle.putInt(AddEditMovie.MOVIE_INDEX, pos);
        bundle.putString(AddEditMovie.MOVIE_NAME, movie.name);
        bundle.putString(AddEditMovie.MOVIE_YEAR, movie.year);
        bundle.putString(AddEditMovie.MOVIE_DIRECTOR, movie.director);
        Intent intent = new Intent(this, AddEditMovie.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, EDIT_MOVIE_CODE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:

                addMovie();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addMovie() {
        Intent intent = new Intent(this, AddEditMovie.class);
        startActivityForResult(intent, ADD_MOVIE_CODE);
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent intent) {

        if (resultCode != RESULT_OK) {
            return;
        }

        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }

        // gather all info passed back by launched activity
        String name = bundle.getString(AddEditMovie.MOVIE_NAME);
        String year = bundle.getString(AddEditMovie.MOVIE_YEAR);
        String director = bundle.getString(AddEditMovie.MOVIE_DIRECTOR);
        int index = bundle.getInt(AddEditMovie.MOVIE_INDEX);

        if (requestCode == EDIT_MOVIE_CODE) {
            Movie movie = movies.get(index);
            movie.name = name;
            movie.year = year;
            movie.director = director;
        } else if (requestCode == ADD_MOVIE_CODE){
            movies.add(new Movie(name, year, director));
        }

        // redo Adapter since source content has changed
        listView.setAdapter(new ArrayAdapter<Movie>(this, movie, movies));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_search_menu,menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager =
        (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager
              .getSearchableInfo(getComponentName()));
        // Do not iconify the widget; expand it by default
        searchView.setIconifiedByDefault(false);

        /*
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItemCompat.setOnActionExpandListener(searchItem,
                    new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                listView.setAdapter(new ArrayAdapter<Movie>(Movies.this, movie, movies));
                searchListStartPos=0;
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        });
*/

        return super.onCreateOptionsMenu(menu);
    }

    private int[] search(String query) {
        int lo=0, hi=movies.size()-1;
        String movie = query.toLowerCase();
        while (lo <= hi) {
            int mid=(lo+hi)/2;
            if (movies.get(mid).name.toLowerCase().startsWith(movie)) {
                // need to scan left and right until no more matches
                return getExtent(mid, movie);
            }
            // mid does not start with the given name, go left or right
            int c = query.compareToIgnoreCase(movies.get(mid).name);
            if (c < 0) {
                hi = mid-1;
            } else {
                lo = mid+1;
            }
        }
        return null;
    }

    private int[] getExtent(int mid, String movie) {
        int[] extent = new int[2];
        extent[0] = mid;
        // scan left
        while (extent[0] > 0) {
            if (movies.get(extent[0]-1).name.toLowerCase().startsWith(movie)) {
                extent[0]--;
            } else {
                break;
            }
        }
        // scan right
        extent[1] = mid;
        while (extent[1] < movies.size()-1) {
            if (movies.get(extent[1]+1).name.toLowerCase().startsWith(movie)) {
                extent[1]++;
            } else {
                break;
            }
        }
        return extent;
    }

    private void showSearchResults(String query) {
        int[] extent = search(query);
        if (extent == null || extent.length == 0) {
            // no matches
            String msg = getString(R.string.search_empty, new Object[]{query});
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        makeResultList(extent);
        searchListStartPos = extent[0];
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int masterIndex = Movies.this.searchListStartPos + position;
                        showMovie(masterIndex);
                    }
                });
    }

    private void makeResultList(int[] extent) {
        int count = extent[1] - extent[0] + 1;
        Movie[] matches = new Movie[count];
        for (int i=0; i < matches.length; i++) {
            matches[i] = movies.get(extent[0]+i);
        }
        listView.setAdapter(
                new ArrayAdapter<Movie>(this, R.layout.movie, matches));
    }

}