package cl.exdev.ouranilist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLoginPressed(View view) {
        String userName = "mapacheverdugo3";
        String password = "Pollo123";

        String name = login(userName, password);

        if (name != null) {
            Toast.makeText(this, "¡Bienvenido " +  name + "!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
        }
    }

    public String login(String userName, String password) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "ouranilist", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT name, userName, password, email FROM users WHERE userName = '" + userName + "' OR password = '" + password + "'", null);

        boolean existFirstElement = cursor.moveToFirst();

        if (existFirstElement) {
            return cursor.getString(0);
        }

        return null;
    }

    public void onSingUpPressed(View view) {
        String userName = "mapacheverdugo3";
        String password = "Pollo123";
        String email = "jorgeverdugoch3@gmail.com";
        String name = "Jorge Verdugo";

        if (checkIfUserExist(userName, email)) {
            Toast.makeText(this, "El usuario " +  userName + " ya existe", Toast.LENGTH_LONG).show();
        } else {
            signUpUser(userName, password, email, name);
            Toast.makeText(this, "Usuario creado con éxito", Toast.LENGTH_LONG).show();
        }
    }

    public boolean checkIfUserExist(String userName, String email) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "ouranilist", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT name FROM users WHERE userName = '" + userName + "' OR email = '" + email + "'", null);

        boolean existFirstElement = cursor.moveToFirst();

        if (existFirstElement) {
            Log.d("MainActivitySQL", cursor.getString(0));
        }

        return existFirstElement;
    }

    public void signUpUser(String userName, String password, String email, String name) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "ouranilist", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues newUser = new ContentValues();
        newUser.put("userName", userName);
        newUser.put("password", password);
        newUser.put("email", email);
        newUser.put("name", name);

        db.insert("users", null, newUser);
    }
}