package com.example.tasknote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_modify.*
import kotlinx.android.synthetic.main.activity_modify.tv_app_name

class ModifyActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        // заполняем EditText
        et_modify_task.setText(taskList[index])

        // Нажали на кнопку "Сохранить"
        image_button_save_modify.setOnClickListener { // считываем текст из et_modify_task
            val textModified = et_modify_task.text.toString()

            // если ничего не ввели, удаляем задачу
            // иначе заменяем задачу в taskList
            if (textModified.isBlank()) taskList.removeAt(index)
            else taskList[index] = textModified

            // заменяем строку в sharedPreferences
            fillMemoryFromTaskList()
        }

        // Нажали на кнопку "Подтвердить"
        image_button_ok_modify.setOnClickListener { // считываем текст из et_modify_task
            val textModified = et_modify_task.text.toString()

            // если ничего не ввели, удаляем задачу
            // иначе заменяем задачу в taskList
            // и переносим выполненную задачу в конец списка
            if (textModified.isBlank()) taskList.removeAt(index)
            else
            {
                taskList[index] = textModified

                if (taskList.size > 1)
                {
                    val textElement = taskList[index]
                    taskList.removeAt(index)
                    taskList.add(textElement)
                }
            }

            // заменяем строку в sharedPreferences
            fillMemoryFromTaskList()
        }

        // Нажали на кнопку "Удалить"
        image_button_delete_modify.setOnClickListener { // удаляем задачу
            taskList.removeAt(index)

            // заменяем строку в sharedPreferences
            fillMemoryFromTaskList()
        }
    }

    // заменяем строку в sharedPreferences
    private fun fillMemoryFromTaskList()
    { // задаём начальное значение stringModified
        var stringModified = if (taskList.isEmpty()) "" else taskList[0]
        var flag = true

        // компонуем строку stringModified
        for (element in taskList)
        {
            if (flag)
            {
                flag = false
                continue
            }

            stringModified = "$stringModified$split$element"
        }

        // сохраняем строку в память
        editor.clear().putString(keyMemory, stringModified).apply()

        // возвращаемся в MainActivity
        finish()
    }
}