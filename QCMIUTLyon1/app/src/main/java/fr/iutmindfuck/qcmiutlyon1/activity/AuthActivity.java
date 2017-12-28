package fr.iutmindfuck.qcmiutlyon1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import fr.iutmindfuck.qcmiutlyon1.R;
import fr.iutmindfuck.qcmiutlyon1.handlers.UserSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;


public class AuthActivity extends AppCompatActivity {

    UserSQLHandler userSQLHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auth);
        userSQLHandler = new UserSQLHandler(new SQLServices(this));
    }

    /**
     * While user click on the login button, check if his username & password is correct
     * and open his panel or display an error message.
     *
     * @param view Login Button (provided on click).
     */
    public void onClickLoginButton(View view) {
        String username = ((TextView)findViewById(R.id.auth_username)).getText().toString();
        String password = ((TextView)findViewById(R.id.auth_password)).getText().toString();

        if (userSQLHandler.isPasswordCorrectFor(username, password))
        {
            launchPanelFor(username);
        }
        else
        {
            ((TextView)findViewById(R.id.auth_error))
                    .setText(getResources().getText(R.string.auth_error));
        }
    }

    /**
     * Check if user is teacher or student and launch the matching panel.
     *
     * @param username username provided by the user.
     */
    private void launchPanelFor(String username) {
        if (userSQLHandler.isTeacher(username))
        {
            startActivity(new Intent(AuthActivity.this, TeacherPanelActivity.class));
        }
        else
        {
            startActivity(new Intent(AuthActivity.this, StudentPanelActivity.class));
        }
    }
}
