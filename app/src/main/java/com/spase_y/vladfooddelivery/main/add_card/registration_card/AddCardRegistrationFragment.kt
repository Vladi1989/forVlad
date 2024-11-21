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

    private fun setupFocusListeners() {
        // Слушатель фокуса для etAddCountry3
        binding.etAddCountry3.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                flipCard(binding.cardView3, binding.cardView2)
            } else {
                flipCard(binding.cardView2, binding.cardView3)
            }
        }

        // Слушатели фокуса для других EditText
        val otherEditTexts = listOf(
            binding.etAddCountry, binding.etAddCountry1, binding.etAddCountry2
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}