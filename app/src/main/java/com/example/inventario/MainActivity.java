package com.example.inventario;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {


    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }



        replaceFragment(new HomeFragment());

        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.shorts:
                    replaceFragment(new ShortsFragment());
                    break;
                case R.id.subscriptions:
                    replaceFragment(new SubscriptionsFragment());
                    break;
                case R.id.library:
                    replaceFragment(new LibraryFragment());
                    break;
            }

            // Agrega animaciones Lottie a los elementos seleccionados
            switch (item.getItemId()) {
                case R.id.home:
                    setLottieAnimation(R.id.home, R.raw.home);
                    break;
                case R.id.shorts:
                    setLottieAnimation(R.id.shorts, R.raw.catalogue);
                    break;
                case R.id.subscriptions:
                    setLottieAnimation(R.id.subscriptions, R.raw.product);
                    break;
                case R.id.library:
                    setLottieAnimation(R.id.library, R.raw.sales);
                    break;
            }

            return true;
        });
        // Re
    }
    //Outside

    private  void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
    private void setLottieAnimation(int itemId, int animationResId) {
        MenuItem menuItem = bottomNavigationView.getMenu().findItem(itemId);
        View view = bottomNavigationView.findViewById(menuItem.getItemId());
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;

            LottieAnimationView animationView = new LottieAnimationView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;

            animationView.setLayoutParams(layoutParams);
            animationView.setAnimation(animationResId);
            animationView.setTag("lottie_animation");

            // Elimina cualquier vista de animación existente antes de agregar la nueva
            View oldAnimationView = viewGroup.findViewWithTag("lottie_animation");
            if (oldAnimationView != null) {
                viewGroup.removeView(oldAnimationView);
            }

            // Agrega la vista de animación al elemento del menú
            viewGroup.addView(animationView);
            animationView.playAnimation();
        }
    }



}
