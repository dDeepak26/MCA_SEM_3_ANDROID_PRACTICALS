package com.example.p5_curd_sqllite;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTask;
    private Button buttonAdd;
    private ListView listViewTasks;
    private DatabaseHelper databaseHelper;
    private ArrayAdapter<String> adapter;
    private List<String> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTask = findViewById(R.id.editTextTask);
        buttonAdd = findViewById(R.id.buttonAdd);
        listViewTasks = findViewById(R.id.listViewTasks);

        databaseHelper = new DatabaseHelper(this);
        taskList = databaseHelper.getAllTasks();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        listViewTasks.setAdapter(adapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = editTextTask.getText().toString();
                if (!task.isEmpty()) {
                    databaseHelper.addTask(task);
                    refreshTaskList();
                    editTextTask.setText("");
                    Toast.makeText(MainActivity.this, "Task added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String task = taskList.get(position);
                showUpdateDeleteDialog(task);
            }
        });
    }

    private void refreshTaskList() {
        taskList.clear();
        taskList.addAll(databaseHelper.getAllTasks());
        adapter.notifyDataSetChanged();
    }

    private void showUpdateDeleteDialog(final String task) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextUpdate = dialogView.findViewById(R.id.editTextUpdate);
        final Button buttonUpdate = dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonDelete = dialogView.findViewById(R.id.buttonDelete);

        editTextUpdate.setText(task);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedTask = editTextUpdate.getText().toString();
                if (!updatedTask.isEmpty()) {
                    databaseHelper.updateTask(taskList.indexOf(task) + 1, updatedTask);
                    refreshTaskList();
                    alertDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Task updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteTask(task);
                refreshTaskList();
                alertDialog.dismiss();
                Toast.makeText(MainActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
}