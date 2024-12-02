package com.spase_y.vladfooddelivery.main.add_card.registration_card

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.databinding.FragmentAddCardRegistrationBinding
import com.spase_y.vladfooddelivery.main.add_card.add_card.data.model.Card
import com.spase_y.vladfooddelivery.main.add_card.add_card.ui.view_model.CardViewModel
import com.spase_y.vladfooddelivery.main.root.MainAppFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddCardRegistrationFragment : Fragment() {

    private var _binding: FragmentAddCardRegistrationBinding? = null
    private val binding get() = _binding!!
    private val cardViewModel: CardViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCardRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainAppFragment.getInstance().hideNavigation()

        setupFocusListeners()

        binding.ivArrowBack3.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        checkFieldsAndEnableButton()

        binding.btnSave.isClickable = false

        binding.btnSave.setOnClickListener {
            val card = Card(
                cardNumber = binding.etCardNumber.text.toString().replace(" ", ""),
                cardHolderName = binding.etCardHolderName.text.toString(),
                expiryDate = binding.etDate.text.toString(),
                cvv = binding.etAddCvv.text.toString()
            )
            cardViewModel.addCard(card)
            Toast.makeText(requireContext(), "Карта добавлена", Toast.LENGTH_SHORT).show()

            parentFragmentManager.popBackStack()
        }

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
                if(inputText.length == 16){
                    binding.etCardHolderName.requestFocus()
                }
            }

            // Метод вызывается после изменения текста (можем здесь ничего не делать)
            override fun afterTextChanged(s: Editable?) {
                checkFieldsAndEnableButton()
            }
        })

        binding.etDate.addTextChangedListener(object :TextWatcher{
            private var isUpdating = false
            private var previousText = ""
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                previousText = s.toString()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(isUpdating) return

                val currentText = s.toString()
                val isDeleting = before > count

                if(isDeleting){
                    return
                }

                val cleanInput = currentText.replace("/", "")

                // Формируем строку в формате MM/YY
                val formattedText = StringBuilder()
                for (i in cleanInput.indices) {
                    if (i == 2) formattedText.append("/") // Добавляем слэш после двух цифр
                    formattedText.append(cleanInput[i])
                }

                isUpdating = true
                binding.etDate.setText(formattedText.toString())
                binding.etDate.setSelection(formattedText.length)
                isUpdating = false
            }
            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return // Если сейчас идёт обновление, пропускаем

                // Синхронизируем tvDate с уже отформатированным текстом
                binding.tvDate.text = binding.etDate.text.toString()

                // Переводим фокус на etAddCountry3, если длина текста достигла 5 символов
                if (binding.etDate.text.length == 5) {
                    binding.etAddCvv.requestFocus()
                }
                checkFieldsAndEnableButton()
            }

        })

        binding.etCardHolderName.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tvCardHolderName.text = s?.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                checkFieldsAndEnableButton()
            }

        })

        binding.etAddCvv.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tvCvv.text = s?.toString()
            }
            override fun afterTextChanged(s: Editable?) {
                checkFieldsAndEnableButton()
            }

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
        binding.etAddCvv.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.etAddCvv.setBackgroundResource(R.drawable.button_shape_stroke)
                flipCard(binding.cardView3, binding.cardView2)
            } else {
                binding.etAddCvv.setBackgroundResource(R.drawable.button_shape_stroke_gray)
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

    private fun checkFieldsAndEnableButton(){
        val isCardNumberFull = binding.etCardNumber.text.toString().length == 19
        val isCardNameFull = binding.etCardHolderName.text.isNotEmpty()
        val isCardDate = binding.etDate.text.toString().length == 5
        val isCardCvv = binding.etAddCvv.text.toString().length == 3

        val allFieldsIsFull = isCardNumberFull && isCardNameFull && isCardDate && isCardCvv

        binding.btnSave.isEnabled = allFieldsIsFull
        binding.btnSave.alpha = if (allFieldsIsFull) 1f else 0.7f

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