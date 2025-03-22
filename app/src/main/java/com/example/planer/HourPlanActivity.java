package com.example.planer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HourPlanActivity extends AppCompatActivity {
    TextView textView;
    Button button;
    EditText editText;
    String receivedText;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hour_plan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //assingment of the objects
        textView = findViewById(R.id.textView3);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editTextText);;

        //Set up of which our will be planing
        receivedText = getIntent().getStringExtra("hour");
        textView.setText("Godz: "+receivedText);

        //Button set up
        button.setOnClickListener( v -> {
            Intent intentReturn = new Intent();
            String text = String.valueOf(editText.getText());
            intentReturn.putExtra("result", text);
            setResult(RESULT_OK, intentReturn);
            finish();
        });

    }
}