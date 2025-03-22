package com.example.planer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> resultLauncher;
    ArrayList<String> itemList;
    ArrayAdapter<String> adapter;
    ListView listView;
    String selectedItem;
    int selectedPosition;

    // SharedPreferences
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicjacja SharedPreferences
        sharedPreferences = getSharedPreferences("PlannerPrefs", MODE_PRIVATE);

        // Inicjacja resultLauncher
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {  // Callback, when activity return result
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String returnedText = result.getData().getStringExtra("result");
                        if (returnedText != null) {
                            String updatedText = itemList.get(selectedPosition) + " " + returnedText;
                            itemList.set(selectedPosition, updatedText);
                            adapter.notifyDataSetChanged();

                            // Zapisz zaktualizowaną listę w SharedPreferences
                            saveItemList();
                        }
                    }
                }
        );

        // listview setup
        listView = findViewById(R.id.listview1);
        itemList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
        listView.setAdapter(adapter);

        // Odczytaj dane z SharedPreferences i załaduj je do itemList
        loadItemList();

        // Jeżeli lista jest pusta, dodaj domyślne wartości
        if (itemList.isEmpty()) {
            for (int i = 1; i <= 24; i++) {
                itemList.add(i < 10 ? "0" + i + ":00" : i + ":00");
            }
            // Zapisz domyślną listę
            saveItemList();
        }

        // Ustawienie kliknięcia na item w liście
        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            selectedItem = itemList.get(position);
            Intent intent = new Intent(MainActivity.this, HourPlanActivity.class);
            intent.putExtra("hour", selectedItem);
            resultLauncher.launch(intent);
        });
    }

    // Metoda do zapisywania listy do SharedPreferences
    private void saveItemList() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Konwertowanie listy na String
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : itemList) {
            stringBuilder.append(item).append(",");
        }
        editor.putString("itemList", stringBuilder.toString());
        editor.apply();
    }

    // Metoda do odczytywania listy z SharedPreferences
    private void loadItemList() {
        String savedList = sharedPreferences.getString("itemList", "");
        if (!savedList.isEmpty()) {
            String[] items = savedList.split(",");
            itemList.clear();
            for (String item : items) {
                if (!item.isEmpty()) {
                    itemList.add(item);
                }
            }
        }
    }
}
