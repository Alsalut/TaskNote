package com.example.tasknote

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.View
import android.widget.ListView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_modify.*
import kotlinx.android.synthetic.main.activity_modify.tv_app_name

class ModifyActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        // Заполняем EditText
        et_modify_task.setText(taskList[index][1])

        // Нажали на кнопку "Сохранить"
        image_button_save_modify.setOnClickListener { // Считываем текст из et_modify_task
            val textModified = et_modify_task.text.toString()

            // Если ничего не ввели, то удаляем задачу
            // Иначе заменяем задачу в taskList
            if (textModified.isBlank()) taskList.removeAt(index)
            else taskList[index][1] = textModified

            // Заменяем строку в sharedPreferences
            fillMemoryFromTaskList()
        }

        // Нажали на кнопку "Подтвердить"
        image_button_ok_modify.setOnClickListener { // Считываем текст из et_modify_task
            val textModified = et_modify_task.text.toString()

            // Если ничего не ввели, то удаляем задачу
            // Иначе заменяем задачу в taskList
            // и переносим выполненную задачу в конец списка
            if (textModified.isBlank()) taskList.removeAt(index)
            else
            {
                taskList[index][0] = statusDone
                taskList[index][1] = textModified

                if (taskList.size > 1)
                {
                    val textElement = ArrayList(taskList[index])
                    taskList.removeAt(index)
                    taskList.add(textElement)
                }
            }

            // Заменяем строку в sharedPreferences
            fillMemoryFromTaskList()
        }

        // Нажали на кнопку "Удалить"
        image_button_delete_modify.setOnClickListener { // Удаляем задачу
            taskList.removeAt(index)

            // Заменяем строку в sharedPreferences
            fillMemoryFromTaskList()
        }
    }

    // Заменяем строку в sharedPreferences
    private fun fillMemoryFromTaskList()
    { // Задаём начальное значение stringModified
        var stringModified = if (taskList.isEmpty()) statusActive else "${taskList[0][0]}${taskList[0][1]}"
        var flag = true

        // Компонуем строку stringModified
        for (element in taskList)
        {
            if (flag)
            {
                flag = false
                continue
            }

            stringModified = "$stringModified$split${element[0]}${element[1]}"
        }

        // Сохраняем строку в память
        editor.clear().putString(keyMemory, stringModified).apply()

        // Возвращаемся в MainActivity
        finish()
    }
}
