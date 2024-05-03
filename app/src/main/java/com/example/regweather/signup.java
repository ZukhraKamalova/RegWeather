package com.example.regweather;

import android.content.ContentValues;
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

public class signup extends Fragment {
    EditText loginEditText, passwordEditText, RepasswordEditText;
    Button registerButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        loginEditText = view.findViewById(R.id.loginField);
        passwordEditText = view.findViewById(R.id.passwordField);
        RepasswordEditText = view.findViewById(R.id.RepasswordField);
        registerButton = view.findViewById(R.id.regButton);

        registerButton.setOnClickListener(v -> {
            String login = loginEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String Repassword = RepasswordEditText.getText().toString();
            if (login.isEmpty() || password.isEmpty() || Repassword.isEmpty()) {
                Toast.makeText(getActivity(), "Пожалуйста, заполните все поля!", Toast.LENGTH_SHORT).show();
                return;
            } else if(!password.equals(Repassword)) {
                Toast.makeText(getActivity(), "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                return;
            }

            DataBaseHelper dbHelper = new DataBaseHelper(getActivity());
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            Cursor cursor = db.query(DataBaseHelper.TABLE_NAME,
                    null, DataBaseHelper.COLUMN_LOGIN + " = ?", new String[]{login},
                    null, null, null);

            if (cursor.moveToFirst()) {
                Toast.makeText(getActivity(), "Пользователь с таким логином уже существует", Toast.LENGTH_SHORT).show();
            } else {
                ContentValues values = new ContentValues();
                values.put(DataBaseHelper.COLUMN_LOGIN, login);
                values.put(DataBaseHelper.COLUMN_PASSWORD, password);
                db.insert(DataBaseHelper.TABLE_NAME, null, values);
                Toast.makeText(getActivity(), "Успешная регистрация", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), WeatherActivity.class);
                intent.putExtra("login", login);
                startActivity(intent);

            }
            cursor.close();
            db.close();
        });
        return view;
    }
}