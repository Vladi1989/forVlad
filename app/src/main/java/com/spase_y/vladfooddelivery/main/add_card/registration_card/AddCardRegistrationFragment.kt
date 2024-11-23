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
import android.widget.EditText
import com.spase_y.vladfooddelivery.R


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