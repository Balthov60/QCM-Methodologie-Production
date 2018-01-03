package fr.iutmindfuck.qcmiutlyon1.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import fr.iutmindfuck.qcmiutlyon1.R;
import fr.iutmindfuck.qcmiutlyon1.data.MCQ;
import fr.iutmindfuck.qcmiutlyon1.handlers.MCQSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;


public class MCQEditionActivity extends AppCompatActivity {

    private MCQ currentMCQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getExtra();

        setContentView(R.layout.activity_mcq_edition);
        setSupportActionBar((Toolbar) findViewById(R.id.mcq_edition_toolbar));
    }
    private void getExtra() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            currentMCQ = (MCQ) extras.getSerializable("mcq");
        }
    }

    @Override
    public void setSupportActionBar(Toolbar toolbar) {
        super.setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MCQListActivity.class));
            }
        });
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        if (currentMCQ != null)
            initFormField();
    }

    /**
     * Init MCQ Edition Layout with currentMCQ Data.
     */
    private void initFormField() {
        setMCQTitle(currentMCQ.getName());
        setMCQDescription(currentMCQ.getDescription());
        setMCQNegative(currentMCQ.isPointNegative());
        setMCQCoefficient(currentMCQ.getCoefficient());

        ((Button) findViewById(R.id.mcq_edition_submit)).setText(R.string.mcq_edition_edit);
    }

    /* ******************/
    /* User Interaction */
    /********************/

    public void submitMCQ(View view) {
        String title = getMCQTitle();
        String description = getMCQDescription();
        boolean isNegative = isMCQNegative();
        Float coefficient = getMCQCoefficient();

        if (title.isEmpty() || description.isEmpty() || coefficient == null) {
            displayErrorToast();
            return;
        }

        MCQ mcq;
        if (currentMCQ == null)
        {
            mcq = new MCQ(null, title, description, isNegative, coefficient);
            displaySuccessToast(false);
        }
        else
        {
            mcq = new MCQ(currentMCQ.getId(), title, description, isNegative, coefficient);
            displaySuccessToast(true);
        }
        new MCQSQLHandler(new SQLServices(this)).createOrReplaceMCQ(mcq);

        startActivity(new Intent(MCQEditionActivity.this, MCQListActivity.class));
    }

    /* MCQ Edition Form getter */

    private String getMCQTitle() {
        return ((EditText) findViewById(R.id.mcq_edition_title_input)).getText().toString();
    }
    private String getMCQDescription() {
        return ((EditText) findViewById(R.id.mcq_edition_description_input)).getText().toString();
    }
    private boolean isMCQNegative() {
        return ((RadioButton) findViewById(R.id.mcq_edition_type_selector_negative)).isChecked();
    }
    private Float getMCQCoefficient() {
        String coefficient = ((EditText)findViewById(R.id.mcq_edition_coefficient_input))
                             .getText().toString();

        return ((coefficient.isEmpty()) ? null : Float.parseFloat(coefficient));
    }

    /* MCQ Edition Form setter */

    private void setMCQTitle(String title) {
        ((EditText) findViewById(R.id.mcq_edition_title_input)).setText(title);
    }
    private void setMCQDescription(String description) {
        ((EditText) findViewById(R.id.mcq_edition_description_input)).setText(description);
    }
    private void setMCQNegative(boolean isNegative) {
        if (isNegative) {
            ((RadioButton) findViewById(R.id.mcq_edition_type_selector_negative))
                    .setChecked(true);
        }
        else {
            ((RadioButton) findViewById(R.id.mcq_edition_type_selector_classic))
                    .setChecked(true);
        }

    }
    private void setMCQCoefficient(float coefficient) {
        ((EditText)findViewById(R.id.mcq_edition_coefficient_input))
                .setText(String.valueOf(coefficient));
    }

    /* Notifications handling */

    /**
     * Display a notification to inform user that filled are missing for mcq submission.
     */
    private void displayErrorToast() {
        Context context = this.getApplicationContext();

        Toast toast = Toast.makeText(context, getString(R.string.mcq_submission_failed),
                                              Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.show();
    }

    /**
     * Display a notification to inform user that mcq submission is successful.
     *
     * @param isEdition true if mcq already exist, false elsewhere.
     */
    private void displaySuccessToast(boolean isEdition) {
        Context context = this.getApplicationContext();
        String message = (isEdition) ? getString(R.string.mcq_successful_modification)
                                     : getString(R.string.mcq_successful_creation);

        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.show();
    }
}
