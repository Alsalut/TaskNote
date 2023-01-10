package com.example.tasknote

// Программа служит для:
// записи списка дел,
// просмотра списка дел,
// пометки выполненных дел,
// удаления выполненных дел

import android.app.ListActivity
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

// Ключ для sharedPreferences
val keyMemory = "keyMemory"

// Объявляем sharedPreferences
lateinit var sharedPreferences: SharedPreferences
lateinit var editor: Editor

// Создаём ArrayList для хранения списка дел в виде строк
val taskList = arrayListOf("")

// Индекс нажатого элемента
var index = 0

// pattern для разбиения строки
val split = "&split&"

class MainActivity : ListActivity(), View.OnClickListener, AdapterView.OnItemClickListener
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // При нажатии кнопки '+' переходим на AddActivity для добавления нового элемента
        image_button_plus.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }

        // Инициализируем sharedPreferences
        initMemory()
    }

    // Инициализируем sharedPreferences
    private fun initMemory()
    {
        sharedPreferences = getSharedPreferences(keyMemory, MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    override fun onResume()
    {
        super.onResume()

        // Заполняем taskList
        putToArrayList()

        // Создаём адаптёр коллекции arrayList
        setAdater()
    }

    // Заполняем taskList
    private fun putToArrayList()
    {
        // Получаем строку из sharedPreferences
        val text = sharedPreferences.getString(keyMemory, "")

        // Очищаем taskList
        taskList.clear()

        // Заполняем taskList
        val arrayString = text!!.split(split)

        for (element in arrayString)
        {
            if (element != "") taskList.add(element)
        }
    }

    // Создаём адаптёр коллекции arrayList
    private fun setAdater()
    {
        // Объявляем и инициализируем адаптёр коллекции arrayList
        val adapter = ArrayAdapter(this, R.layout.list_view, taskList)

        // Подключаем адаптёр
        setListAdapter(adapter)

        // Задаём слушателя OnItemLongClickListener
        listView.setOnItemClickListener(this)
    }

    // При кратком нажатии на элементе списка
    // переходим на ModifyActivity
    // для удаления или редактирования
    override fun onItemClick(adapterView: AdapterView<*>, element: View?, position: Int, id: Long)
    {
        super.onListItemClick(listView, element, position, id)

        index = position

        // Переходим на ModifyActivity
        startActivity(Intent(this, ModifyActivity::class.java))
    }

    // Не используется
    override fun onClick(element: View?)
    {
        TODO("Not yet implemented")
    }

    // Жест "Назад" ( кнопка "Назад" ) - закрытие приложения и удаление из списка приложений
    override fun onBackPressed()
    {
        super.onBackPressed()

        // Завершаем работу приложения
        finishAndRemoveTask()
    }
}
