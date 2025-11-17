package com.example.multifragmentapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsFragment extends Fragment {

    private EditText etUsername, etEmail, etPassword, etTheme;
    private Switch switchNotifications;
    private Button btnSave, btnReset;

    public static final String PREFS_NAME = "user_prefs";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_THEME = "theme";
    public static final String KEY_NOTIFICATION = "notifications";

    public SettingsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settingsfragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        etUsername = view.findViewById(R.id.et_username);
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        etTheme = view.findViewById(R.id.et_theme);
        switchNotifications = view.findViewById(R.id.switch_notifications);
        btnSave = view.findViewById(R.id.btn_save);
        btnReset = view.findViewById(R.id.btn_reset);

        loadPreferences();

        btnSave.setOnClickListener(v -> savePreferences());
        btnReset.setOnClickListener(v -> resetPreferences());
    }

    private void loadPreferences() {
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        etUsername.setText(prefs.getString(KEY_USERNAME, ""));
        etEmail.setText(prefs.getString(KEY_EMAIL, ""));
        etPassword.setText(prefs.getString(KEY_PASSWORD, ""));
        etTheme.setText(prefs.getString(KEY_THEME, ""));
        switchNotifications.setChecked(prefs.getBoolean(KEY_NOTIFICATION, false));
    }

    private void savePreferences() {
        String username = etUsername.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String theme = etTheme.getText().toString();
        boolean notifications = switchNotifications.isChecked();

        if (TextUtils.isEmpty(username)) {
            etUsername.setError("Required");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Required");
            return;
        }

        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_THEME, theme);
        editor.putBoolean(KEY_NOTIFICATION, notifications);

        editor.apply();

        Toast.makeText(getActivity(), "Preferences Saved", Toast.LENGTH_SHORT).show();
    }

    private void resetPreferences() {
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();

        etUsername.setText("");
        etEmail.setText("");
        etPassword.setText("");
        etTheme.setText("");
        switchNotifications.setChecked(false);

        Toast.makeText(getActivity(), "Preferences Reset", Toast.LENGTH_SHORT).show();
    }
}
