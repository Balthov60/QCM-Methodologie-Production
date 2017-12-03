package fr.iutmindfuck.qcmiutlyon1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.logging.Logger;

import fr.iutmindfuck.qcmiutlyon1.data.Answer;
import fr.iutmindfuck.qcmiutlyon1.data.MCQ;
import fr.iutmindfuck.qcmiutlyon1.data.Question;
import fr.iutmindfuck.qcmiutlyon1.handlers.MCQSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.handlers.QuestionSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.handlers.UserSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;

public class AuthActivity extends AppCompatActivity {

    private static final String UNIMPLEMENTED_STUDENT_MESSAGE
            = "Le panel etudiant n'a pas encore été implementé";

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
            launchPanelFor(username);
        }
        else {
            ((TextView)findViewById(R.id.auth_error))
                    .setText(getResources().getText(R.string.auth_error));
        }
    }
    private void launchPanelFor(String username) {
        if (userSQLHandler.isTeacher(username)) {
            startActivity(new Intent(AuthActivity.this,
                    TeacherPanelActivity.class));
        }
        else
        {
            Context context = getApplicationContext();

            Toast toast = Toast.makeText(context, UNIMPLEMENTED_STUDENT_MESSAGE, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM,0,50);
            toast.show();
        }
    }
}
