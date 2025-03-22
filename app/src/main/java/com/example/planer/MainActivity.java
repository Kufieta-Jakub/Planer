package com.example.planer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> hoursList = new ArrayList<>(Arrays.asList(
            "01:00", "02:00", "03:00", "04:00", "05:00", "06:00",
            "07:00", "08:00", "09:00", "10:00", "11:00", "12:00",
            "13:00", "14:00", "15:00", "16:00", "17:00", "18:00",
            "19:00", "20:00", "21:00", "22:00", "23:00", "24:00"
    ));

    ActivityResultLauncher<Intent> resultLauncher;
    ArrayList<String> itemList;
    ArrayAdapter<String> adapter;
    ListView listView;
    String selectedItem;
    int selectedPosition;

    // SharedPreferences
    SharedPreferences sharedPreferences;

    // Adapter do ViewPager2
    private ViewPager2 viewPager;
    private CircleAdapter circleAdapter;
    private ArrayList<Integer> circleImages; // Lista obrazków dla kółek

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicjacja SharedPreferences
        sharedPreferences = getSharedPreferences("PlannerPrefs", MODE_PRIVATE);

        // Inicjacja ViewPager2
        viewPager = findViewById(R.id.viewPager);
        circleImages = new ArrayList<>();
        // Przykładowe obrazy dla kółek (możesz dodać więcej)
        circleImages.add(R.drawable.cloudy);
        circleImages.add(R.drawable.calendar);
        circleImages.add(R.drawable.cloudy);

        // Ustawienie adaptera dla ViewPager2
        circleAdapter = new CircleAdapter(circleImages);
        viewPager.setAdapter(circleAdapter);


        // ListView setup
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
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String resultFromOtherActivity = hoursList.get(selectedPosition)+ " " + result.getData().getStringExtra("result");
                        itemList.set(selectedPosition,resultFromOtherActivity);
                        adapter.notifyDataSetChanged();
                        saveItemList();
                    }
                }
        );
        // Ustawienie kliknięcia na item w liście
        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            selectedItem = itemList.get(position);
            Intent intent = new Intent(MainActivity.this, HourPlanActivity.class);
            intent.putExtra("hour", hoursList.get(selectedPosition));
            resultLauncher.launch(intent);
        });
    }
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
