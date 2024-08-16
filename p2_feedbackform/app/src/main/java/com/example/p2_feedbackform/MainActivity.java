package com.example.p2_feedbackform;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private EditText editName, editEmail, editComments;
    private RatingBar ratingBar;
    private Button btnSubmit;
    private TextView textViewDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        editName = findViewById(R.id.edit_name);
        editEmail = findViewById(R.id.edit_email);
        editComments = findViewById(R.id.edit_comments);
        ratingBar = findViewById(R.id.rating_bar);
        btnSubmit = findViewById(R.id.btn_submit);
        textViewDisplay = findViewById(R.id.textViewDisplay);

        // Handle submit button click
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve input values
                String name = editName.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String comments = editComments.getText().toString().trim();
                float rating = ratingBar.getRating();

                // TODO: Handle submission (e.g., send feedback to server or store locally)
                // For example, show a toast message
                Toast.makeText(MainActivity.this, "Feedback submitted!", Toast.LENGTH_SHORT).show();
                responseDetails();
            }
        });
    }
    private void responseDetails() {
        // Retrieve input values
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String comments = editComments.getText().toString().trim();
        float rating = ratingBar.getRating();

        // Display the entered data
        StringBuilder displayText = new StringBuilder();
        displayText.append("Name: ").append(name).append("\n");
        displayText.append("Email: ").append(email).append("\n");
        displayText.append("Rating: ").append(rating).append("\n");
        displayText.append("Comments: ").append(comments).append("\n");

        // Display the data in a TextView
        textViewDisplay.setText(displayText.toString());

        // Make the TextView visible
        textViewDisplay.setVisibility(View.VISIBLE);

        // Optionally, you can toast a message or perform other actions upon submission
        Toast.makeText(this, "Feedback submitted!", Toast.LENGTH_SHORT).show();
    }

}
