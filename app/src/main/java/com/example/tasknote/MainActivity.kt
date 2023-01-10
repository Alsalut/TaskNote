package com.example.tasknote

// программа служит для :
// записи списка дел
// просмотра списка дел
// удаления выполненных дел

import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

// создаём ArrayList для хранения списка дел в виде строк
val taskList = arrayListOf("")

// создаём ключ для Intent
val keyPosition = "keyPosition"

class MainActivity : ListActivity(), View.OnClickListener, AdapterView.OnItemClickListener
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setAdater() // создаём Адаптёр коллекции arrayList

        // При нажатии кнопки '+' переходим на AddActivity для добавления нового элемента
        image_button_plus.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }
    }

    // создаём Адаптёр коллекции arrayList
    private fun setAdater()
    { // объявляем и инициализируем Адаптёр коллекции arrayList
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

        // создаём Intent
        var intent = Intent(this, ModifyActivity::class.java)

        // записываем в intent позицию нажатого элемента
        intent.putExtra(keyPosition, position)

        // переходим на ModifyActivity
        startActivity(intent)
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