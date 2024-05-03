package com.example.regweather;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class login extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EditText loginEditText, passwordEditText;
        Button loginButton;
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        loginEditText = view.findViewById(R.id.loginField);
        passwordEditText = view.findViewById(R.id.passwordField);
        loginButton = view.findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            String login = loginEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(getActivity(), "Пожалуйста, заполните все поля!", Toast.LENGTH_SHORT).show();
                return;
            }

            DataBaseHelper databaseHelper = new DataBaseHelper(getActivity());
            SQLiteDatabase db = databaseHelper.getReadableDatabase();

            Cursor cursor = db.query(DataBaseHelper.TABLE_NAME,
                    null, DataBaseHelper.COLUMN_LOGIN + " = ? AND " + DataBaseHelper.COLUMN_PASSWORD + " = ?",
                    new String[]{login, password},
                    null, null, null);

            if (cursor.moveToFirst()) {
                Toast.makeText(getActivity(), "Успешная авторизация", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), WeatherActivity.class);
                intent.putExtra("login", login);

                startActivity(intent);
                getActivity().finish();
            } else {
                Toast.makeText(getActivity(), "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
            db.close();
        });
        return view;
    }
}