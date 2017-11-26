package fr.iutmindfuck.qcmiutlyon1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AuthActivity extends AppCompatActivity {

    UserSQLServices userSQLServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        userSQLServices = new UserSQLServices(this);
    }

    public void onClickLoginButton(View view) {
        String username = ((TextView)findViewById(R.id.auth_username)).getText().toString();
        String password = ((TextView)findViewById(R.id.auth_password)).getText().toString();

        if (userSQLServices.isPasswordCorrectFor(username, password)) {
            if (userSQLServices.isTeacher(username)) {
                // TODO: Redirect Prof Panel & remove setText
                ((TextView)findViewById(R.id.auth_error)).setText("PROF");
            }
            else
            {
                // TODO: Redirect Prof User & remove setText
                ((TextView)findViewById(R.id.auth_error)).setText("USER");
            }
        }
        else
        {
            ((TextView)findViewById(R.id.auth_error))
                    .setText(getResources().getText(R.string.auth_error));
        }
    }
}
