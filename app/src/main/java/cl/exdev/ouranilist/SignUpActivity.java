package cl.exdev.ouranilist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    /*private EditText _username;
    private EditText _password;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        /*_username = (EditText)findViewById(R.id.username_text);
        _password = (EditText)findViewById(R.id.password_text);*/
    }

    public void onSingUpPressed(View view) {
        String username = "Pollo";
        String password = "Pollo123";
        String email = "jorgeverdugoch3@gmail.com";
        String name = "Jorge Verdugo";

        if (checkIfUserExist(username, email)) {
            Toast.makeText(this, "El usuario " +  username + " ya existe", Toast.LENGTH_LONG).show();
        } else {
            signUpUser(username, password, email, name);
            Toast.makeText(this, "Usuario creado con éxito", Toast.LENGTH_LONG).show();
        }
    }

    public boolean checkIfUserExist(String username, String email) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "ouranilist", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT name FROM users WHERE username = '" + username + "' OR email = '" + email + "'", null);

        boolean existFirstElement = cursor.moveToFirst();

        if (existFirstElement) {
            Log.d("MainActivitySQL", cursor.getString(0));
        }

        return existFirstElement;
    }

    public void signUpUser(String username, String password, String email, String name) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "ouranilist", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues newUser = new ContentValues();
        newUser.put("username", username);
        newUser.put("password", password);
        newUser.put("email", email);
        newUser.put("name", name);

        db.insert("users", null, newUser);
    }

}