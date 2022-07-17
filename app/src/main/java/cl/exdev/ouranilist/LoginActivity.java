package cl.exdev.ouranilist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cl.exdev.ouranilist.database.AdminSQLiteOpenHelper;
import cl.exdev.ouranilist.models.User;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "";
    private EditText _username;
    private EditText _password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _username = (EditText)findViewById(R.id.username_text);
        _password = (EditText)findViewById(R.id.password_text);
    }

    public void onLoginPressed(View view) {
        String username = _username.getText().toString();
        String password = _password.getText().toString();

        if(username.compareTo("") == 0 || password.compareTo("") == 0) {
            Toast.makeText(this, "Faltan datos", Toast.LENGTH_SHORT).show();
        }

        User user = login(username, password);

        if (user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            Toast.makeText(this, "¡Bienvenido " +  user.getName() + "!", Toast.LENGTH_LONG).show();
            intent.putExtra("user", user);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
        }
    }

    public User login(String username, String password) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "ouranilist", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT name, username, password, email FROM users WHERE username = '" + username + "' AND password = '" + password + "'", null);

        boolean existFirstElement = cursor.moveToFirst();

        if (existFirstElement) {
            User user = new User();
            user.setName(cursor.getString(0));
            user.setUsername(cursor.getString(1));
            user.setEmail(cursor.getString(3));
            cursor.close();
            return user;
        }
        cursor.close();
        return null;
    }

    public void goToSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}