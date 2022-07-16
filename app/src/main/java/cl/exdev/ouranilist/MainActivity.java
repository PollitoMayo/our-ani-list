package cl.exdev.ouranilist;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void singUpUser(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "ouranilist", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();


    }
}