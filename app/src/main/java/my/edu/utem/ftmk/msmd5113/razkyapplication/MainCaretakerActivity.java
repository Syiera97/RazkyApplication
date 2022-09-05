package my.edu.utem.ftmk.msmd5113.razkyapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.DonationDetailsDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.Login.LoginActivity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.Login.LoginCaretakerActivity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.databinding.ActivityMainCaretakerBinding;

public class MainCaretakerActivity extends AppCompatActivity {

    private ActivityMainCaretakerBinding binding;
    private DonationDetailsDataEntity dataEntity;
    private Menu menuList;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menuList = menu;
        menu.findItem(R.id.edit).setVisible(false);
        menu.findItem(R.id.add).setVisible(false);
        menu.findItem(R.id.save).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.logOut){
            Intent intent = new Intent(MainCaretakerActivity.this, LoginCaretakerActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainCaretakerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view_caretaker);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_my_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main_caretaker);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navViewCaretaker, navController);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#5DAE8C"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setDisplayHomeAsUpEnabled(false);

    }

    public void navigateToDonationCompletedFragment() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main_caretaker);
        navController.navigateUp();
        navController.navigate(R.id.navigation_donate_completed);
    }


    public void navigateToAddProductFragment() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main_caretaker);
        navController.navigateUp();
        navController.navigate(R.id.navigation_add_product);
    }

    public void setDonationDetails(DonationDetailsDataEntity donationDetailsDataEntity) {
        dataEntity = donationDetailsDataEntity;
    }

    public DonationDetailsDataEntity getDonationDetails(){
        return dataEntity;
    }
}
