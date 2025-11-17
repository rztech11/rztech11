package com.example.multifragmentapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

    private TextView tvGreeting, tvProfileEmail, tvProfileTheme,
            tvProfileNotifications, tvInfo;

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profilefragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        tvGreeting = view.findViewById(R.id.tvGreeting);
        tvProfileEmail = view.findViewById(R.id.tvProfileEmail);
        tvProfileTheme = view.findViewById(R.id.tvProfileTheme);
        tvProfileNotifications = view.findViewById(R.id.tvProfileNotifications);
        tvInfo = view.findViewById(R.id.tvInfo);

        loadProfile();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadProfile();
    }

    private void loadProfile() {

        SharedPreferences prefs = requireActivity()
                .getSharedPreferences(SettingsFragment.PREFS_NAME, Context.MODE_PRIVATE);

        String username = prefs.getString(SettingsFragment.KEY_USERNAME, "");
        String email = prefs.getString(SettingsFragment.KEY_EMAIL, "");
        String theme = prefs.getString(SettingsFragment.KEY_THEME, "");
        boolean notifications = prefs.getBoolean(SettingsFragment.KEY_NOTIFICATION, false);

        if (!username.isEmpty()) {
            tvGreeting.setText("Hi, " + username);
            tvProfileEmail.setText("Email: " + email);
            tvProfileTheme.setText("Theme: " + theme);
            tvProfileNotifications.setText("Notifications: " + (notifications ? "Enabled" : "Disabled"));
            tvInfo.setText("Preferences loaded successfully.");
        } else {
            tvGreeting.setText("Hi, Guest");
            tvProfileEmail.setText("Email: Not set");
            tvProfileTheme.setText("Theme: Not set");
            tvProfileNotifications.setText("Notifications: Disabled");
            tvInfo.setText("No saved preferences.\nGo to Settings to add your profile.");
        }
    }
}
