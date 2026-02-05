package com.techvipin130524.newsstack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techvipin130524.newsstack.auth.LoginActivity;
import com.techvipin130524.newsstack.ui.CategoryFragment;
import com.techvipin130524.newsstack.ui.FavoriteFragment;
import com.techvipin130524.newsstack.ui.HomeFragment;
import com.techvipin130524.newsstack.ui.ProfileFragment;
import com.techvipin130524.newsstack.utils.PreferenceManager;
import com.techvipin130524.newsstack.utils.ThemeManager;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;


//    TextView text;
//    private Button btn;
//
//    private FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference reference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        If you apply theme after UI loads
//        App opens in Light
//        Then switches to Dark (bad UX)
        // STEP 1: Apply theme BEFORE UI
        PreferenceManager preferenceManager = new PreferenceManager(this);
        ThemeManager.applyTheme(preferenceManager.isDarkModeEnabled());

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Default fragment
        loadFragment(new HomeFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {

            Fragment fragment = null;

            int id = item.getItemId();
            if (id == R.id.nav_home){
                fragment = new HomeFragment();
            } else if (id == R.id.nav_category) {
                fragment = new CategoryFragment();
            } else if (id == R.id.nav_favorite) {
                fragment = new FavoriteFragment();
            } else if (id == R.id.nav_profile) {
                fragment = new ProfileFragment();
            }

            if (fragment != null) {
                loadFragment(fragment);
                return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

//        text = findViewById(R.id.textView2);
//        btn = findViewById(R.id.button);
//        btn.setOnClickListener(v -> {
//            FirebaseAuth.getInstance().signOut();
//            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//        });
//
//
//        Log.d("Firebase", "onCreate:"+reference);
//        Log.d("Firebase", "onCreate:"+reference.child("Employee1").child("Name").setValue("Vipin Yadav"));
//        Log.d("Firebase", "onCreate:"+reference.child("Employee1").child("Job").setValue("Tester"));
//        Log.d("Firebase", "onCreate:"+reference.child("Employee1").child("Age").setValue(24));
//        Log.d("Firebase", "onCreate:"+reference.child("Employee1").child("Salary").setValue(500000));
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String name = snapshot.child("Employee1").child("Name").getValue().toString();
//                Log.d("Firebase", "onDataChange:"+name);
//                text.setText(name);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//
//
//    }
}