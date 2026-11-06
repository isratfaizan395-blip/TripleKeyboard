package com.triplekeyboard

import android.inputmethodservice.InputMethodService
import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout

class TripleKeyboardService : InputMethodService() {

    override fun onCreateInputView(): View {
        val keyboard = LinearLayout(this)
        keyboard.orientation = LinearLayout.VERTICAL
        keyboard.setBackgroundColor(Color.rgb(20, 20, 20))
        keyboard.setPadding(5, 10, 5, 10)

        val rows = listOf(
            listOf("ক", "খ", "গ", "ঘ", "ঙ"),
            listOf("চ", "ছ", "জ", "ঝ", "ঞ"),
            listOf("ট", "ঠ", "ড", "ঢ", "ণ"),
            listOf("ত", "থ", "দ", "ধ", "ন"),
            listOf("প", "ফ", "ব", "ভ", "ম"),
            listOf("English", "বাংলা", "العربية", "⌫", "↵"),
            listOf("🌐", "😊", "স্পেস", "।")
        )

        for (row in rows) {
            val line = LinearLayout(this)
            line.orientation = LinearLayout.HORIZONTAL
            line.gravity = Gravity.CENTER

            for (key in row) {
                val button = Button(this)
                button.text = key
                button.textSize = 17f
                button.setTextColor(Color.WHITE)
                button.setTypeface(null, Typeface.BOLD)
                button.setBackgroundColor(Color.rgb(45, 45, 45))

                val params = LinearLayout.LayoutParams(
                    0,
                    52,
                    if (key == "স্পেস") 2f else 1f
                )
                params.setMargins(2, 3, 2, 3)
                line.addView(button, params)

                button.setOnClickListener {
                    val input = currentInputConnection
                    when (key) {
                        "⌫" -> input?.deleteSurroundingText(1, 0)
                        "↵" -> input?.commitText("\n", 1)
                        "স্পেস" -> input?.commitText(" ", 1)
                        "🌐", "😊", "বাংলা", "English", "العربية" -> {}
                        else -> input?.commitText(key, 1)
                    }
                }
            }

            keyboard.addView(
                line,
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
        }

        return keyboard
    }
}
