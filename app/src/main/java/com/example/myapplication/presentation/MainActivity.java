package com.example.myapplication.presentation;

import static androidx.core.content.ContentProviderCompat.requireContext;
import static com.example.myapplication.presentation.utils.constants.Utils.BACKGROUND_IMAGE;
import static com.example.myapplication.presentation.utils.constants.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.DRAWABLES;
import static com.example.myapplication.presentation.utils.constants.Utils.FINE_PERMISSION_CODE;
import static com.example.myapplication.presentation.utils.constants.Utils.PAGE_1;
import static com.example.myapplication.presentation.utils.constants.Utils.PAGE_KEY;
import static com.example.myapplication.presentation.utils.constants.Utils.QUEUE_ID;
import static com.example.myapplication.presentation.utils.constants.Utils.STATE;
import static com.example.myapplication.presentation.utils.constants.Restaurant.LOCATION_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.PATH;
import static com.example.myapplication.presentation.utils.constants.Restaurant.VISITOR;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.presentation.common.orderDetails.OrderDetailsActivity;
import com.example.myapplication.presentation.companyQueue.createQueue.CreateCompanyQueueActivity;
import com.example.myapplication.presentation.companyQueue.queueDetails.CompanyQueueDetailsActivity;
import com.example.myapplication.presentation.companyQueue.queueDetails.workerDetails.WorkerQueueDetailsActivity;
import com.example.myapplication.presentation.companyQueue.queueManager.QueueManagerActivity;
import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.WorkerManagerActivity;
import com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.CookActiveOrdersActivity;
import com.example.myapplication.presentation.employee.main.restaurantCook.availableOrders.AvailableCookOrdersActivity;
import com.example.myapplication.presentation.home.stories.StoriesActivity;
import com.example.myapplication.presentation.profile.createAccount.createCompanyAccountFragment.CreateCompanyActivity;
import com.example.myapplication.presentation.profile.createAccount.firstFragment.CreateAccountActivity;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.chooseCompany.ChooseCompanyActivity;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.editProfile.EditProfileActivity;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.historyProfile.HistoryActivity;
import com.example.myapplication.presentation.profile.loggedProfile.basicUser.userSettings.BasicSettingsActivity;
import com.example.myapplication.presentation.profile.loggedProfile.companyUser.editCompany.EditCompanyActivity;
import com.example.myapplication.presentation.basicQueue.createQueue.CreateQueueActivity;
import com.example.myapplication.presentation.basicQueue.queueDetails.QueueDetailsActivity;
import com.example.myapplication.presentation.restaurantLocation.addLocation.AddLocationActivity;
import com.example.myapplication.presentation.restaurantLocation.locationDetails.RestaurantLocationDetailsActivity;
import com.example.myapplication.presentation.restaurantMenu.RestaurantMenuActivity;
import com.example.myapplication.presentation.service.queue.QueueActivity;
import com.example.myapplication.presentation.restaurantLocation.LocationsActivity;
import com.example.myapplication.presentation.common.waitingInQueue.WaitingActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.myapplication.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, FINE_PERMISSION_CODE);
        }

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_queue, R.id.navigation_profile)
                .build();

        setSupportActionBar(binding.toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.navView, navController);
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        switch (newConfig.uiMode) {
            case Configuration.UI_MODE_NIGHT_YES:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
        }
        recreate();
    }

    public void openCreateAccountActivity(ActivityResultLauncher<Intent> launcher){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        launcher.launch(intent);
    }

    public void openOrderDetailsActivity(String path, ActivityResultLauncher<Intent> launcher){
        Intent intent = new Intent(this, OrderDetailsActivity.class);
        intent.putExtra(PATH, path);
        intent.putExtra(STATE, VISITOR);
        launcher.launch(intent);
    }

    public void openLocationDetailsActivity(String locationId, ActivityResultLauncher<Intent> launcher){
        Intent intent = new Intent(this, RestaurantLocationDetailsActivity.class);
        intent.putExtra(LOCATION_ID, locationId);
        launcher.launch(intent);
    }

    public void openAddLocationsActivity(ActivityResultLauncher<Intent> launcher){
        Intent intent = new Intent(this, AddLocationActivity.class);
        launcher.launch(intent);
    }

    public void openCookActiveOrdersActivity(String restaurantId, String locationId){
        Intent intent = new Intent(this, CookActiveOrdersActivity.class);
        intent.putExtra(COMPANY_ID, restaurantId);
        intent.putExtra(LOCATION_ID, locationId);
        startActivity(intent);
    }

    public void openAvailableOrdersActivity(String restaurantId, String locationId){
        Intent intent = new Intent(this, AvailableCookOrdersActivity.class);
        intent.putExtra(COMPANY_ID, restaurantId);
        intent.putExtra(LOCATION_ID, locationId);
        startActivity(intent);
    }

    public void openStoriesActivity(int[] drawables, int background){
        Intent intent = new Intent(this, StoriesActivity.class);
        intent.putExtra(DRAWABLES, drawables);
        intent.putExtra(BACKGROUND_IMAGE, background);
        startActivity(intent);
    }

    public void openRestaurantMenuActivity(){
        Intent intent = new Intent(this, RestaurantMenuActivity.class);
        startActivity(intent);
    }

    public void openRestaurantLocationsActivity(){
        Intent intent = new Intent(this, LocationsActivity.class);
        startActivity(intent);
    }

    public void openQueueWorkerDetailsActivity(String queueId, String companyId){
        Intent intent = new Intent(this, WorkerQueueDetailsActivity.class);
        intent.putExtra(QUEUE_ID, queueId);
        intent.putExtra(COMPANY_ID, companyId);
        startActivity(intent);
    }

    public void openWorkerManagerActivity(String companyId){
        Intent intent = new Intent(this, WorkerManagerActivity.class);
        intent.putExtra(COMPANY_ID, companyId);
        startActivity(intent);
    }

    public void openQueueActivity(){
        Intent intent = new Intent(this, QueueActivity.class);
        startActivity(intent);
    }

    public void openBasicSettingsActivity(ActivityResultLauncher<Intent> launcher){
        Intent intent = new Intent(this, BasicSettingsActivity.class);
        launcher.launch(intent);
    }

    public void openEditCompanyActivity(String companyId) {
        Intent intent = new Intent(this, EditCompanyActivity.class);
        intent.putExtra(COMPANY_ID, companyId);
        startActivity(intent);
    }

    public void openEditActivity() {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

    public void openHistoryActivity() {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    public void openQueueDetailsActivity(ActivityResultLauncher<Intent> launcher) {
        Intent intent = new Intent(this, QueueDetailsActivity.class);
        launcher.launch(intent);
    }

    public void launchQueueWaitingActivity(ActivityResultLauncher<Intent> launcher) {
        Intent intent = new Intent(this, WaitingActivity.class);
        launcher.launch(intent);
    }

    public void openCreateQueueActivity(ActivityResultLauncher<Intent> launcher){
        Intent intent = new Intent(this, CreateQueueActivity.class);
        intent.putExtra(PAGE_KEY, PAGE_1);
        launcher.launch(intent);
    }

    public void openQueueManagerActivity(String companyId){
        Intent intent = new Intent(this, QueueManagerActivity.class);
        intent.putExtra(COMPANY_ID, companyId);
        startActivity(intent);
    }

    public void openCreateCompanyQueueActivity(String companyId){
        Intent intent = new Intent(this, CreateCompanyQueueActivity.class);
        intent.putExtra(PAGE_KEY, PAGE_1);
        intent.putExtra(COMPANY_ID, companyId);
        startActivity(intent);
    }

    public void launchCreateCompanyQueueActivity(String companyId, ActivityResultLauncher<Intent> launcher){
        Intent intent = new Intent(this, CreateCompanyQueueActivity.class);
        intent.putExtra(PAGE_KEY, PAGE_1);
        intent.putExtra(COMPANY_ID, companyId);
        launcher.launch(intent);
    }

    public void openCompanyQueueDetailsActivity(String companyId, String queueId, ActivityResultLauncher<Intent> launcher){
        Intent intent = new Intent(this, CompanyQueueDetailsActivity.class);
        intent.putExtra(COMPANY_ID, companyId);
        intent.putExtra(QUEUE_ID, queueId);
        launcher.launch(intent);
    }

}