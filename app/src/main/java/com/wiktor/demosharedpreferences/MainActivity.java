package com.wiktor.demosharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText key, value;
    private Button buttonSave, buttonLoad;

    // создать SharedPreferences
    SharedPreferences mySharedPreferences;

    final String SAVED_TEXT = "saved_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLoad = findViewById(R.id.button_load);
        buttonSave = findViewById(R.id.button_save);
        key = findViewById(R.id.edit_text_1);
        value = findViewById(R.id.edit_text_2);

        buttonSave.setOnClickListener(this);
        buttonLoad.setOnClickListener(this);

       /* Давайте сделаем так, чтобы сохранение и загрузка происходили автоматически при закрытии и
        открытии приложения и не надо было жать кнопки.  Для этого метод loadText будем вызывать в onCreate.
а метод saveText - в onDestroy*/


        loadText();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_save:
                saveText();
                Toast.makeText(this, "Text Saved", Toast.LENGTH_LONG).show();
                break;
            case R.id.button_load:
                loadText();
                Toast.makeText(this, "Text Loaded", Toast.LENGTH_LONG).show();
                break;
        }
    }


    /*
        saveText – сохранение данных. Сначала с помощью метода getPreferences получаем объект sPref
        класса SharedPreferences, который позволяет работать с данными (читать и писать). Константа
        MODE_PRIVATE используется для настройки доступа и означает, что после сохранения, данные будут
        видны только этому приложению. Далее, чтобы редактировать данные, необходим объект Editor –
        получаем его из sPref. В метод putString указываем наименование переменной – это константа
        SAVED_TEXT, и значение – содержимое поля etText. Чтобы данные сохранились, необходимо выполнить
        commit. И для наглядности выводим сообщение, что данные сохранены.

                loadText – загрузка данных. Так же, как и saveText, с помощью метода getPreferences
                получаем объект sPref класса SharedPreferences. MODE_PRIVATE снова указывается, хотя и
                используется только при записи данных. Здесь Editor мы не используем, т.к. нас интересует
                только чтение данных. Читаем с помощью метода getString – в параметрах указываем константу
                 - это имя, и значение по умолчанию (пустая строка). Далее пишем значение в поле ввода
                  etText и выводим сообщение, что данные считаны.

- используете getPreferences, если работаете с данными для текущего Activity и не
                   хотите выдумывать имя файла.
 - используете getSharedPreferences, если сохраняете, например, данные - общие для нескольких
     Activity и сами выбираете имя файла для сохранения.


mySharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
То данные сохранились бы в файле MyPref.xml, а не в MainActivity.xml.
    */
    void saveText() {
        mySharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = mySharedPreferences.edit();
        ed.putString(SAVED_TEXT, key.getText().toString());
        ed.apply();
    }


    void loadText() {
        mySharedPreferences = getPreferences(MODE_PRIVATE);
        String savedText = mySharedPreferences.getString(SAVED_TEXT, "");
        value.setText(savedText);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveText();
    }
}
