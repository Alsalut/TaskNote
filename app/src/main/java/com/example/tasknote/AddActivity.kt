package com.example.tasknote

// Добавляем новую задачу в ArrayList

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        // Нажали на кнопку "Сохранить"
        image_button_save_add.setOnClickListener {
            // Если ничего не ввели, то выходим без действий
            if (et_add_task.text.toString().isEmpty()) return@setOnClickListener

            // Получаем строку из sharedPreferences
            val textOld = sharedPreferences.getString(keyMemory, "")

            // Добавляем новую задачу в начало строки
            val textNew = statusActive + if (textOld == "") et_add_task.text.toString()
            else "${et_add_task.text}$split$textOld"

            // Заменяем строку в sharedPreferences
            editor.clear().putString(keyMemory, textNew).apply()

            finish() // Возвращаемся в MainActivity
        }

        // Нажали на кнопку "Удалить"
        image_button_delete_add.setOnClickListener {
            et_add_task.text.clear() // Очищаем поле ввода
        }
    }
}
