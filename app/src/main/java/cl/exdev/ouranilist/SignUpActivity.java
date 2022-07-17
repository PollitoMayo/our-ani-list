package cl.exdev.ouranilist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cl.exdev.ouranilist.database.AdminSQLiteOpenHelper;
import cl.exdev.ouranilist.models.User;

public class SignUpActivity extends AppCompatActivity {

    private EditText _username;
    private EditText _fullname;
    private EditText _email;
    private EditText _password;
    private EditText _retryPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        _username = (EditText)findViewById(R.id.username_text);
        _fullname = (EditText)findViewById(R.id.full_name_text);
        _email = (EditText)findViewById(R.id.email_text);
        _password = (EditText)findViewById(R.id.password_text);
        _retryPassword = (EditText)findViewById(R.id.retry_password_text);

    }

    public void onSingUpPressed(View view) {
        String username = _username.getText().toString();
        String name = _fullname.getText().toString();
        String email = _email.getText().toString();
        String password = _password.getText().toString();
        String retryPassword = _retryPassword.getText().toString();

        if(password.compareTo(retryPassword) != 0) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
        } else {
            if (checkIfUserExist(username, email)) {
                Toast.makeText(this, "El usuario " + username + " ya existe", Toast.LENGTH_LONG).show();
            } else {
                User user = signUpUser(username, password, email, name);
                Toast.makeText(this, "Usuario creado con éxito", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
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

    public User signUpUser(String username, String password, String email, String name) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "ouranilist", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues newUser = new ContentValues();
        newUser.put("username", username);
        newUser.put("password", password);
        newUser.put("email", email);
        newUser.put("name", name);

        db.insert("users", null, newUser);
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setName(name);
        return user;
    }

}