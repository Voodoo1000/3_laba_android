package com.example.a3_laba

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var resultsAdapter: ArrayAdapter<String>
    private val resultsList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Получение ссылок на элементы интерфейса
        val etLastName: EditText = findViewById(R.id.etLastName)
        val etFirstName: EditText = findViewById(R.id.etFirstName)
        val etMiddleName: EditText = findViewById(R.id.etMiddleName)
        val rgHousingType: RadioGroup = findViewById(R.id.rgHousingType)
        val etAddress: EditText = findViewById(R.id.etAddress)
        val etComplaint: EditText = findViewById(R.id.etComplaint)
        val btnSubmit: Button = findViewById(R.id.btnSubmit)
        val lvResults: ListView = findViewById(R.id.lvResults)

        // Инициализация адаптера для ListView
        resultsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, resultsList)
        lvResults.adapter = resultsAdapter

        // Установка обработчика на кнопку отправить
        btnSubmit.setOnClickListener {
            // Получаем введенные данные
            val lastName = etLastName.text.toString().trim()
            val firstName = etFirstName.text.toString().trim()
            val middleName = etMiddleName.text.toString().trim()
            val address = etAddress.text.toString().trim()
            val complaint = etComplaint.text.toString().trim()

            // Проверяем, что поля не пустые
            if (lastName.isEmpty()) {
                etLastName.error = "Заполните фамилию"
                return@setOnClickListener
            }
            if (firstName.isEmpty()) {
                etFirstName.error = "Заполните имя"
                return@setOnClickListener
            }
            if (address.isEmpty()) {
                etAddress.error = "Заполните адрес"
                return@setOnClickListener
            }
            if (complaint.isEmpty()) {
                etComplaint.error = "Заполните текст жалобы"
                return@setOnClickListener
            }

            // Проверяем, что выбран тип жилья
            val selectedHousingTypeId = rgHousingType.checkedRadioButtonId
            if (selectedHousingTypeId == -1) {
                Toast.makeText(this, "Выберите тип жилья", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Определяем тип жилья
            val housingType = when (selectedHousingTypeId) {
                R.id.rbHouse -> "Частный дом"
                R.id.rbApartment -> "Квартира"
                else -> "Не выбрано"
            }

            // Формируем выводимую информацию
            val resultText = """
                Фамилия: $lastName
                Имя: $firstName
                Отчество: $middleName
                Тип жилья: $housingType
                Адрес: $address
                ОБратная связь: $complaint
            """.trimIndent()

            // Добавляем результат в список и обновляем ListView
            resultsList.add(resultText)
            resultsAdapter.notifyDataSetChanged()

            // Очищаем поля ввода после отправки
            etLastName.text.clear()
            etFirstName.text.clear()
            etMiddleName.text.clear()
            etAddress.text.clear()
            etComplaint.text.clear()
            rgHousingType.clearCheck() // Сбрасываем выбор в RadioGroup
        }
    }
}
