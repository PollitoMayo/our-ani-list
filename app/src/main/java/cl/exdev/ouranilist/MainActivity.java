package cl.exdev.ouranilist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ListView _animesList;

    private String elements[] = {"uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _animesList = (ListView) findViewById(R.id.animes_list);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, elements);
        _animesList.setAdapter(adapter);
    }
}