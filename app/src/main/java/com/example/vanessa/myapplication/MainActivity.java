package com.example.vanessa.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.vanessa.myapplication.Fragments.AppointmentDetailFragment;
import com.example.vanessa.myapplication.Fragments.AppointmentFragment;
import com.example.vanessa.myapplication.Fragments.AppointmentsCalendarFragment;
import com.example.vanessa.myapplication.Fragments.EventDetailFragment;
import com.example.vanessa.myapplication.Fragments.EventFragment;
import com.example.vanessa.myapplication.Fragments.EventsCalendarFragment;
import com.example.vanessa.myapplication.Fragments.MainFragment;
import com.example.vanessa.myapplication.Fragments.ReminderFragment;
import com.example.vanessa.myapplication.Interfaces.FragmentComunication;
import com.example.vanessa.myapplication.Models.Appointment;
import com.example.vanessa.myapplication.Models.Event;
import com.example.vanessa.myapplication.Models.Reminder;

public class MainActivity extends AppCompatActivity implements FragmentComunication{

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    AppointmentFragment appointmentFragmentList;
    AppointmentDetailFragment appointmentDetailFragment;
    EventFragment eventFragment;
    EventDetailFragment eventDetailFragment;
    ContactActivity contactActivity;
    MainFragment mainFragment;
    MenuItem menuItemEvents;
    MenuItem menuItemAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        //menuItemEvents = drawerLayout.findViewById(R.id.item_navigation_drawer_events);

        initializeFragmentsActivities();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }

        setupNavigationDrawerContent(navigationView);

        Intent intent = getIntent();

        setFirstFragment(intent);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFirstFragment(Intent intent) {
        Bundle extras = intent.getExtras();

        int pos=0;
        if (extras != null) {
            pos= intent.getIntExtra("main_pos", 0);
        }
        setFragment(pos);

    }


    private void initializeFragmentsActivities () {
        appointmentFragmentList = new AppointmentFragment();
        appointmentDetailFragment = new AppointmentDetailFragment();
        eventFragment = new EventFragment();
        eventDetailFragment = new EventDetailFragment();
        contactActivity = new ContactActivity();
        mainFragment = new MainFragment();
    }

    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_navigation_drawer_home:
                                menuItem.setChecked(true);
                                setFragment(0);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_appointments:
                                menuItem.setChecked(true);
                                setFragment(1);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_events:
                                menuItem.setChecked(true);
                                setFragment(2);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_contacts:
                                menuItem.setChecked(true);
                                openActivity(3);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_share:
                                menuItem.setChecked(true);
                                openActivity(4);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_settings:
                                menuItem.setChecked(true);
                                openActivity(5);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_reminders:
                                menuItem.setChecked(true);
                                setFragment(6);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                        }
                        return true;
                    }
                });
    }

    public void setFragment(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (position) {
            case 0:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                MainFragment mainFragment = new MainFragment();
                fragmentTransaction.replace(R.id.fragment, mainFragment);
                fragmentTransaction.detach(mainFragment);
                fragmentTransaction.attach(mainFragment);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                //AppointmentFragment appointmentFragment = new AppointmentFragment();
                AppointmentsCalendarFragment appointmentCalFragment = new AppointmentsCalendarFragment();
                fragmentTransaction.replace(R.id.fragment, appointmentCalFragment);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                //EventFragment eventFragment = new EventFragment();
                EventsCalendarFragment eventCalFragment = new EventsCalendarFragment();
                fragmentTransaction.replace(R.id.fragment, eventCalFragment);
                fragmentTransaction.commit();
                break;
            case 6:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                //EventFragment eventFragment = new EventFragment();
                ReminderFragment reminderFragment = new ReminderFragment();
                fragmentTransaction.replace(R.id.fragment, reminderFragment);
                fragmentTransaction.commit();
                break;
            case 7:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                EventFragment eventFragment = new EventFragment();
                fragmentTransaction.replace(R.id.fragment, eventFragment);
                fragmentTransaction.commit();
                break;
            case 8:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                AppointmentFragment appointmentFragment = new AppointmentFragment();
                fragmentTransaction.replace(R.id.fragment, appointmentFragment);
                fragmentTransaction.commit();
                break;
        }
    }

    private void openActivity (int position){
        Intent intent;
        switch (position) {
            case 3:
                intent = new Intent(this, ContactActivity.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(this, ShareFacebook.class);
                startActivity(intent);
                break;
            case 5:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void sendAppointment(Appointment appointment) {
        appointmentDetailFragment= (AppointmentDetailFragment) this.getSupportFragmentManager().
                findFragmentById(R.id.fragAppDetail);

        if(appointmentDetailFragment!=null && findViewById(R.id.fragment)==null){
            appointmentDetailFragment.setInfo(appointment);
        }else{
            appointmentDetailFragment=new AppointmentDetailFragment();
            Bundle bundleSend=new Bundle();
            bundleSend.putSerializable("app_object",appointment);
            appointmentDetailFragment.setArguments(bundleSend);

            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment,appointmentDetailFragment).
                    addToBackStack(null).commit();
        }

    }

    @Override
    public void sendEvent(Event event) {
        eventDetailFragment= (EventDetailFragment) this.getSupportFragmentManager().
                findFragmentById(R.id.fragEventDetail);

        if(eventDetailFragment!=null && findViewById(R.id.fragment)==null){
            eventDetailFragment.setInfo(event);
        }else{
            eventDetailFragment=new EventDetailFragment();
            Bundle bundleSend=new Bundle();
            bundleSend.putSerializable("event_object",event);
            eventDetailFragment.setArguments(bundleSend);

            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment,eventDetailFragment).
                    addToBackStack(null).commit();
        }

    }
}

