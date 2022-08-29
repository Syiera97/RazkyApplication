package my.edu.utem.ftmk.msmd5113.razkyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.List;

import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.DonationDetailsDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.DonorDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.OrphanageDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.StockDetails;
import my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.DonateNow.DonateNowDetailsFragment;
import my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.DonateNow.OrphanagesDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.DonateNow.StockDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.Login.LoginActivity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Menu menuList;
    List<StockDetails> stockDataEntityList = new ArrayList<>();
    OrphanageDataEntity orphanageDataEntity;
    DonorDataEntity donorDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_my_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

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
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void navigateToDetailsFragment() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
        navController.navigate(R.id.navigation_donate_now);
    }

    public void navigateToDonationDetailsFragment() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
        navController.navigate(R.id.navigation_donate_detail);
    }

    public void navigateToDonationCompletedFragment() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
        navController.navigate(R.id.navigation_donate_completed);
    }

    public void navigateToDonateNearbyFragment() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
        navController.navigate(R.id.navigation_donate_nearby);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    public void setStocks(List<StockDetails>  stockDataEntity) {
        stockDataEntityList = stockDataEntity;
    }

    public List<StockDetails> getStockDetails(){
        return stockDataEntityList;
    }

    public void setOrphanageDetails(OrphanageDataEntity  orphanageDetails) {
        orphanageDataEntity = orphanageDetails;
    }

    public OrphanageDataEntity getOrphanageDetails(){
        return orphanageDataEntity;
    }

    public void setDonorDetails(DonorDataEntity donorDataEntity) {
        donorDetails = donorDataEntity;
    }

    public DonorDataEntity getDonorDataEntity(){
        return donorDetails;
    }
}