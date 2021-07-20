package com.example.testapp.util

import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable

object PhoneNumberFormatting : PhoneNumberFormattingTextWatcher() {
    var unFormattedNumber: String? = null
    var countNumber: Int? = null

    private val elementsToRemove = listOf(" ", "-", ")", "(", "  ")

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        super.onTextChanged(s, start, before, count)
        countNumber = s.toString().removeElements(elementsToRemove).length
    }

    override fun afterTextChanged(s: Editable?) {
        super.afterTextChanged(s)
        unFormattedNumber = s.toString().removeElements(elementsToRemove)
    }

}