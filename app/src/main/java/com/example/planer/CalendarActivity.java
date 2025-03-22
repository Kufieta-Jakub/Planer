package com.example.planer;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calender);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Dodanie ViewPager2 z common_view_pager.xml
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        ArrayList<Integer> circleImages = new ArrayList<>();
        circleImages.add(R.drawable.cloudy);
        circleImages.add(R.drawable.calendar);
        circleImages.add(R.drawable.cloudy);

        // Tworzymy adapter i ustawiamy dla ViewPager2
        CircleAdapter circleAdapter = new CircleAdapter(circleImages);
        viewPager.setAdapter(circleAdapter);
    }
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}