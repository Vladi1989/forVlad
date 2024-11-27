package com.spase_y.vladfooddelivery.main.add_card.registration_card

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spase_y.vladfooddelivery.databinding.FragmentAddCardRegistrationBinding
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.spase_y.vladfooddelivery.R
import okhttp3.internal.format


class AddCardRegistrationFragment : Fragment() {
    private var _binding: FragmentAddCardRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCardRegistrationBinding.inflate(inflater, container, false)
        setupFocusListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etCardNumber.addTextChangedListener(object : TextWatcher {
            // Флаг, чтобы избежать повторной обработки текста во время его изменения
            private var isUpdating = false
            // Переменная для хранения длины текста до изменения
            private var previousLength = 0

            // Метод вызывается перед изменением текста в EditText
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Сохраняем текущую длину текста, чтобы позже сравнить её с новой длиной
                previousLength = s?.length ?: 0
            }

            // Метод вызывается во время изменения текста
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Если уже идёт обновление текста (isUpdating == true), выходим, чтобы не зациклиться
                if (isUpdating) return

                // Определяем текущую длину текста
                val currentLength = s?.length ?: 0
                // Сравниваем текущую длину текста с предыдущей
                // Если длина увеличилась, значит пользователь добавил символы
                // Если уменьшилась, значит пользователь удалил символы
                val isAdding = currentLength > previousLength

                // Убираем все пробелы из текста, чтобы подготовить его к форматированию
                val inputText = s.toString().replace(" ", "")

                // Разделяем текст на две части:
                // - Первая часть: первые 12 символов (если их меньше 12, берём сколько есть)
                val firstPart = if (inputText.length >= 12) inputText.substring(0, 12) else inputText
                // - Вторая часть: символы после первых 12 (если их вообще нет, остаётся пустая строка)
                val secondPart = if (inputText.length > 12) inputText.substring(12) else ""

                // Форматируем первую часть: разбиваем её на группы по 4 символа и добавляем пробелы между группами
                val formattedFirstPart = firstPart.chunked(4).joinToString(" ")

                // Заменяем все цифры в первой части символом "•" для скрытия
                val hiddenFirstPart = formattedFirstPart.replace(Regex("\\d"), "•")

                // Устанавливаем скрытый текст для TextView, который отображает скрытые символы
                binding.tvPasswordHide.text = hiddenFirstPart
                // Устанавливаем оставшийся текст для другого TextView
                binding.tvPasswordEnd.text = secondPart

                // Начинаем обновление текста (устанавливаем флаг, чтобы не вызвать зацикливание)
                isUpdating = true
                // Устанавливаем новый текст в EditText, добавляя форматированную первую часть и оставшийся текст
                binding.etCardNumber.setText(formattedFirstPart + if (secondPart.isNotEmpty()) " $secondPart" else "")

                // Устанавливаем позицию курсора:
                if (isAdding) {
                    // Если пользователь добавляет символы, перемещаем курсор в конец текста
                    binding.etCardNumber.setSelection(binding.etCardNumber.text.length)
                } else {
                    // Если пользователь удаляет символы, оставляем курсор на прежнем месте
                    binding.etCardNumber.setSelection(start)
                }

                // Завершаем обновление текста (сбрасываем флаг)
                isUpdating = false
            }

            // Метод вызывается после изменения текста (можем здесь ничего не делать)
            override fun afterTextChanged(s: Editable?) {}
        })

        setFocusChangeListener(binding.etCardNumber)
        setFocusChangeListener(binding.etCardHolderName)
        setFocusChangeListener(binding.etDate)
    }



    private fun setupFocusListeners() {

        binding.cardView2.setOnClickListener {
            flipCard(binding.cardView3,binding.cardView2)
        }
        binding.cardView3.setOnClickListener {
            flipCard(binding.cardView2,binding.cardView3)
        }
        // Слушатель фокуса для etAddCountry3
        binding.etAddCountry3.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.etAddCountry3.setBackgroundResource(R.drawable.button_shape_stroke)
                flipCard(binding.cardView3, binding.cardView2)
            } else {
                binding.etAddCountry3.setBackgroundResource(R.drawable.button_shape_stroke_gray)
                flipCard(binding.cardView2, binding.cardView3)
            }
        }

        // Слушатели фокуса для других EditText
        val otherEditTexts = listOf(
            binding.etCardNumber, binding.etCardHolderName, binding.etDate
        )

        for (editText in otherEditTexts) {
            editText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    if (binding.cardView2.visibility != View.VISIBLE) {
                        binding.cardView3.visibility = View.GONE
                        binding.cardView2.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun flipCard(toShow: View, toHide: View) {
        if (toHide.visibility != View.VISIBLE) return // Если карточка уже скрыта, анимацию не запускаем

        // Устанавливаем расстояние до камеры
        setCameraDistance(toHide)
        setCameraDistance(toShow)

        // Pivot для вращения
        toHide.pivotX = toHide.width / 2f
        toHide.pivotY = toHide.height / 2f
        toShow.pivotX = toShow.width / 2f
        toShow.pivotY = toShow.height / 2f

        // Анимация скрытия
        val hideAnimator = ObjectAnimator.ofFloat(toHide, "rotationY", 0f, 90f).apply {
            duration = 300
        }
        // Анимация показа
        val showAnimator = ObjectAnimator.ofFloat(toShow, "rotationY", -90f, 0f).apply {
            duration = 300
        }
        hideAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                toHide.visibility = View.GONE // Скрываем карточку
                toShow.visibility = View.VISIBLE // Показываем следующую карточку
                showAnimator.start() // Запускаем анимацию показа
            }
        })

        AnimatorSet().apply {
            play(hideAnimator)
            start()
        }
    }

    private fun setCameraDistance(view: View) {
        val scale = view.resources.displayMetrics.density
        view.cameraDistance = 8000 * scale
    }
    private fun setFocusChangeListener(editText: EditText){
        editText.onFocusChangeListener = View.OnFocusChangeListener{_,hasFocus ->
            if(hasFocus){
                editText.setBackgroundResource(R.drawable.button_shape_stroke)
            } else {
                editText.setBackgroundResource(R.drawable.button_shape_stroke_gray)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}