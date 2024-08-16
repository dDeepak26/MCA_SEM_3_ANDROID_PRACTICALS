package com.example.p2_feedback_form;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerFeedbackType;
    private AutoCompleteTextView autoCompleteTextViewSubject;
    private EditText editTextFeedbackMessage;
    private Button buttonSubmit;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        spinnerFeedbackType = findViewById(R.id.spinnerFeedbackType);
        autoCompleteTextViewSubject = findViewById(R.id.autoCompleteTextViewSubject);
        editTextFeedbackMessage = findViewById(R.id.editTextFeedbackMessage);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        textViewResult = findViewById(R.id.textViewResult);

        // Spinner setup
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.feedback_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFeedbackType.setAdapter(adapter);

        // AutoCompleteTextView setup (dummy data for demonstration)
        String[] subjects = {"App UI", "Performance", "Bug Report", "Other"};
        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, subjects);
        autoCompleteTextViewSubject.setAdapter(autoCompleteAdapter);

        // Submit button click listener
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });
    }

    private void submitFeedback() {
        // Retrieve input values
        String feedbackType = spinnerFeedbackType.getSelectedItem().toString();
        String subject = autoCompleteTextViewSubject.getText().toString().trim();
        String message = editTextFeedbackMessage.getText().toString().trim();

        // Display the submitted feedback
        StringBuilder feedbackText = new StringBuilder();
        feedbackText.append("Feedback Type: ").append(feedbackType).append("\n");
        feedbackText.append("Subject: ").append(subject).append("\n");
        feedbackText.append("Message: ").append(message).append("\n");

        // Display the feedback submission result
        textViewResult.setText(feedbackText.toString());
        textViewResult.setVisibility(View.VISIBLE); // Make the TextView visible

        // Optionally, you can perform actions such as sending the feedback to a server,
        // storing it locally, etc.

        // Toast message for feedback submission
        Toast.makeText(this, "Feedback submitted successfully!", Toast.LENGTH_SHORT).show();
    }
}
