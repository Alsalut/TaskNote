package com.example.tasknote

// Добавляем новую задачу в ArrayList

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        // Нажали на кнопку "Сохранить"
        image_button_save_add.setOnClickListener {
            // если ничего не ввели, выходим без действий
            if (et_add_task.text.toString().isEmpty()) return@setOnClickListener

            // получаем строку из sharedPreferences
            val textOld = sharedPreferences.getString(keyMemory, "")

            // добавляем новую задачу в начало строки
            val textNew = if (textOld == "") et_add_task.text.toString()
            else "${et_add_task.text}$split$textOld"

            // заменяем строку в sharedPreferences
            editor.clear().putString(keyMemory, textNew).apply()

            finish() // возвращаемся в MainActivitz
        }

        // Нажали на кнопку "Удалить"
        image_button_delete_add.setOnClickListener {
            et_add_task.text.clear() // Очищаем поле ввода
        }
    }
}