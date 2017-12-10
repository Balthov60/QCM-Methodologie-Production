package fr.iutmindfuck.qcmiutlyon1.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import fr.iutmindfuck.qcmiutlyon1.R;
import fr.iutmindfuck.qcmiutlyon1.data.MCQ;
import fr.iutmindfuck.qcmiutlyon1.handlers.MCQSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;


public class MCQEditionActivity extends AppCompatActivity {

    private static final String INVALID_SUBMISSION = "Vous devez renseigner tout les champs.";
    private static final String SUCCESSFUL_SUBMISSION = "Votre QCM à bien été ajouté.";

    private MCQ currentMCQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mcq_edition);

        setSupportActionBar((Toolbar) findViewById(R.id.mcq_edition_toolbar));

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            currentMCQ = (MCQ) extras.getSerializable("mcq");
            initFormField();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basic_menu, menu);
        return true;
    }

    private void initFormField() {
        setMCQTitle(currentMCQ.getName());
        setMCQDescription(currentMCQ.getDescription());
        setMCQNegative(currentMCQ.isPointNegative());
        setMCQCoefficient(currentMCQ.getCoefficient());
    }


    public void submitMCQ(View view) {
        String title = getMCQTitle();
        String description = getMCQDescription();
        boolean isNegative = isMCQNegative();
        Float coefficient = getMCQCoefficient();

        if (title.isEmpty() || description.isEmpty() || coefficient.isNaN()) {
            displayErrorToast();
        }
        else {
            new MCQSQLHandler(new SQLServices(this))
                    .createOrReplaceMCQ(new MCQ(0, title, description, isNegative, coefficient));
            displaySuccessToast();
        }

        startActivity(new Intent(MCQEditionActivity.this, MCQListActivity.class));
    }

    /* Get Value from MCQ Edition Form */
    public String getMCQTitle() {
        return ((EditText) findViewById(R.id.mcq_edition_title_input)).getText().toString();
    }
    public String getMCQDescription() {
        return ((EditText) findViewById(R.id.mcq_edition_description_input)).getText().toString();
    }
    public boolean isMCQNegative() {
        return ((RadioButton) findViewById(R.id.mcq_edition_type_selector_negative)).isChecked();
    }
    public Float getMCQCoefficient() {
        String coefficient = ((EditText)findViewById(R.id.mcq_edition_coefficient_input))
                             .getText().toString();

        if (coefficient.isEmpty())
            return null;

        return Float.parseFloat(coefficient);
    }

    /* Set Value from MCQ Edition Form */
    public void setMCQTitle(String title) {
        ((EditText) findViewById(R.id.mcq_edition_title_input)).setText(title);
    }
    public void setMCQDescription(String description) {
        ((EditText) findViewById(R.id.mcq_edition_description_input)).setText(description);
    }
    public void setMCQNegative(boolean isNegative) {
        if (isNegative) {
            ((RadioButton) findViewById(R.id.mcq_edition_type_selector_negative))
                    .setChecked(true);
        }
        else {
            ((RadioButton) findViewById(R.id.mcq_edition_type_selector_classic))
                    .setChecked(true);
        }

    }
    public void setMCQCoefficient(float coefficient) {
        ((EditText)findViewById(R.id.mcq_edition_coefficient_input))
                .setText(String.valueOf(coefficient));
    }

    /* Toast */
    public void displayErrorToast() {
        Context context = this.getApplicationContext();

        Toast toast = Toast.makeText(context, INVALID_SUBMISSION, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.show();
    }
    public void displaySuccessToast() {
        Context context = this.getApplicationContext();

        Toast toast = Toast.makeText(context, SUCCESSFUL_SUBMISSION, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.show();
    }
}
