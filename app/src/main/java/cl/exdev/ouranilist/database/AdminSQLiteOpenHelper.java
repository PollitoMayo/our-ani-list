package cl.exdev.ouranilist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    final static String DB_NAME = "ouranilistdb";
    final static int DB_VERSION = 2;

    public AdminSQLiteOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE users(username TEXT UNIQUE PRIMARY KEY, name TEXT, email TEXT, password TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE favorites(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, animeId TEXT, liked INTEGER DEFAULT 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
