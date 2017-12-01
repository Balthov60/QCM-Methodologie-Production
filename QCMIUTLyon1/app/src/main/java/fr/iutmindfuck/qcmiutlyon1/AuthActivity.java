package fr.iutmindfuck.qcmiutlyon1;

import android.annotation.SuppressLint;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.logging.Logger;

import fr.iutmindfuck.qcmiutlyon1.handlers.UserSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;

public class AuthActivity extends AppCompatActivity {

    UserSQLHandler userSQLHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_auth);

        userSQLHandler = new UserSQLHandler(new SQLServices(this));
    }

    public void onClickLoginButton(View view) {
        String username = ((TextView)findViewById(R.id.auth_username)).getText().toString();
        String password = ((TextView)findViewById(R.id.auth_password)).getText().toString();

        if (userSQLHandler.isPasswordCorrectFor(username, password)) {
            if (userSQLHandler.isTeacher(username)) {
                // TODO: Redirect Prof Panel & remove setText
                ((TextView)findViewById(R.id.auth_error)).setText(getResources().getText(R.string.set_prof));
            }
            else
            {
                // TODO: Redirect Prof User & remove setText
                ((TextView)findViewById(R.id.auth_error)).setText(getResources().getText(R.string.set_user));
            }
        }
        else
        {
            ((TextView)findViewById(R.id.auth_error))
                    .setText(getResources().getText(R.string.auth_error));
        }
    }
}
