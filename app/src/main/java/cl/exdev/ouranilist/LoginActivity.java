package cl.exdev.ouranilist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.username_text);
        password = (EditText)findViewById(R.id.password_text);
    }

    public void login(View view) {
        String _username = username.getText().toString();
        String _password = password.getText().toString();

        if(_username == null || _username.compareTo("") == 0 || _password == null || _password.compareTo("") == 0) {
            Toast.makeText(this, "Faltan datos", Toast.LENGTH_SHORT).show();
        }

        if(_username.compareTo("Pollito") == 0 && _password.compareTo("awa") == 0) {
            Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show();
        }
    }
}