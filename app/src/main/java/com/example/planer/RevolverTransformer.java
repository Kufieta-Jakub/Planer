package com.example.planer;

import android.view.View;
import androidx.viewpager2.widget.ViewPager2;

public class RevolverTransformer implements ViewPager2.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        // Kąt obrotu w zależności od pozycji
        float rotation = position * -30f;
        page.setRotation(rotation); // Obrót elementu
        page.setAlpha(Math.max(0.3f, 1 - Math.abs(position))); // Stopniowe zanikanie
    }
}

