package fr.iutmindfuck.qcmiutlyon1.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import fr.iutmindfuck.qcmiutlyon1.R;
import fr.iutmindfuck.qcmiutlyon1.data.MCQ;
import fr.iutmindfuck.qcmiutlyon1.handlers.MCQSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;


public class MCQEditionActivity extends AppCompatActivity {

    private static final String INVALID_SUBMISSION = "Vous devez renseigner tout les champs.";
    private static final String SUCCESSFUL_CREATION = "Votre QCM à bien été ajouté.";
    private static final String SUCCESSFUL_MODIFICATION = "Votre QCM à bien été modifié.";

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
    public void setSupportActionBar(Toolbar toolbar) {
        super.setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MCQListActivity.class);
                intent.putExtra("isTeacher", true);
                startActivity(intent);
            }
        });
    }

    private void initFormField() {
        setMCQTitle(currentMCQ.getName());
        setMCQDescription(currentMCQ.getDescription());
        setMCQNegative(currentMCQ.isPointNegative());
        setMCQCoefficient(currentMCQ.getCoefficient());

        ((Button) findViewById(R.id.mcq_edition_submit)).setText(R.string.mcq_edition_edit);
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
            if (currentMCQ == null)
            {
                new MCQSQLHandler(new SQLServices(this))
                        .createOrReplaceMCQ(new MCQ(null, title,
                                description, isNegative, coefficient));
                displaySuccessToast(false);
            }
            else
            {
                new MCQSQLHandler(new SQLServices(this))
                        .createOrReplaceMCQ(new MCQ(currentMCQ.getId(), title,
                                description, isNegative, coefficient));
                displaySuccessToast(true);
            }
        }

        Intent intent = new Intent(MCQEditionActivity.this, MCQListActivity.class);
        intent.putExtra("isTeacher", true);
        startActivity(intent);
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

    @SuppressLint("ShowToast")
    public void displaySuccessToast(boolean isEdition) {
        Context context = this.getApplicationContext();

        Toast toast;
        if (isEdition) {
            toast = Toast.makeText(context, SUCCESSFUL_MODIFICATION, Toast.LENGTH_SHORT);
        }
        else {
            toast = Toast.makeText(context, SUCCESSFUL_CREATION, Toast.LENGTH_SHORT);
        }

        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.show();
    }
}
