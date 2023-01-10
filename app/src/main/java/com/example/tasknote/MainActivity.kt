package com.example.tasknote

// программа служит для :
// записи списка дел
// просмотра списка дел
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

// ключ для sharedPreferences
val keyMemory = "keyMemory"

// объявляем sharedPreferences
lateinit var sharedPreferences: SharedPreferences
lateinit var editor: Editor

// создаём ArrayList для хранения списка дел в виде строк
val taskList = arrayListOf("")

// индекс нажатого элемента
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

        // инициализируем sharedPreferences
        initMemory()
    }

    // инициализируем sharedPreferences
    private fun initMemory()
    {
        sharedPreferences = getSharedPreferences(keyMemory, MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    override fun onResume()
    {
        super.onResume()

        // заполняем taskList
        putToArrayList()

        // создаём Адаптёр коллекции arrayList
        setAdater()
    }

    // заполняем taskList
    private fun putToArrayList()
    {
        // получаем строку из sharedPreferences
        val text = sharedPreferences.getString(keyMemory, "")

        // очищаем taskList
        taskList.clear()

        // заполняем taskList
        val arrayString = text!!.split(split)

        for (element in arrayString)
        {
            if (element != "") taskList.add(element)
        }
    }

    // создаём Адаптёр коллекции arrayList
    private fun setAdater()
    {
        // объявляем и инициализируем Адаптёр коллекции arrayList
        val adapter = ArrayAdapter(this, R.layout.list_view, taskList)

        // подключаем Адаптёр
        setListAdapter(adapter)

        // задаём слушателя OnItemLongClickListener
        listView.setOnItemClickListener(this)
    }

    // При кратком нажатии на элементе списка
    // переходим на ModifyActivity
    // для удаления или редактирования
    override fun onItemClick(adapterView: AdapterView<*>, element: View?, position: Int, id: Long)
    {
        super.onListItemClick(listView, element, position, id)

        index = position

        // переходим на ModifyActivity
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

        // завершаем приложение
        finishAndRemoveTask()
    }
}