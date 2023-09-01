package com.msidorenko.speech_to_text_microphone

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

private const val CODE = 10

class MainActivity : AppCompatActivity() {
    private lateinit var btn: Button            // переменная для кнопки
    private lateinit var textField: TextView    // переменная для текстового поля

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.btn)            // находим кнопку
        textField = findViewById(R.id.text)     // находим текстовое поле

        //----------------------------------------------------------
        // подготовим запуск распознавания речи
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            // добавим текстовую модель
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)

            // добавим язык, который будет распознаваться (в нашем случае будет тот, что в устройстве)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        }

        //----------------------------------------------------------
        // установим обработчик нажатий на кнопку
        btn.setOnClickListener{
            // запускаем активити с распознаванием речи
            startActivityForResult(intent, CODE)
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // проверяем, что результат соответствует нашему коду запроса и он не пустой
        if (requestCode == CODE && resultCode == RESULT_OK && data != null) {

            // получаем список результатов (их всегда несколько)
            val recognitionResultList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

            //  будем работать с первым результатом
            //  проверяем, что в нем есть какие-то данные
            if (recognitionResultList.first().isNotEmpty() && recognitionResultList.first().isNotBlank()) {
                // устанавливаем результат в текстовое поле
                textField.text = recognitionResultList.first()

                // пишем в лог
                Log.i("TAG", "Recognition result: " + recognitionResultList.first())
            } else {
                // если в первом элементе нет данных, то выводим ошибку в текстовое поле
                textField.text = "ОШИБКА! \n Нажмите на кнопку и повторите фразу"

                // пишем ошибку в лог
                Log.e("TAG", "Error! Recognition result is blank or empty!")
            }
        } else {
            // выводим ошибку в текстовое поле, если что-то пошло не так
            textField.text = "ОШИБКА! \n Нажмите на кнопку и повторите фразу"

            // пишем ошибку в лог
            Log.e("TAG","Error!")
        }
    }

}

