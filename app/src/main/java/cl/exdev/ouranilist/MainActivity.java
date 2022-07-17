package cl.exdev.ouranilist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import cl.exdev.ouranilist.adapters.AnimesAdapter;

public class MainActivity extends AppCompatActivity {
    private ListView lvAnimes;

    private String elements[] = {"SPY×FAMILY", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa", "uwu", "awa"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("La lista de Pancito");

        lvAnimes = (ListView) findViewById(R.id.animes_list);

        ArrayAdapter<String> adapter = new AnimesAdapter(this, elements);
        lvAnimes.setAdapter(adapter);

        lvAnimes.setOnItemClickListener((parent, view, i, id) -> {
            String animeName = elements[i];
            Toast.makeText(MainActivity.this, animeName, Toast.LENGTH_LONG).show();
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logout) {
            Toast.makeText(this, "Cerrando sesión...", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}