package com.example.testapp.util

import android.graphics.Color
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.handleApiError(
    failure: Resource.Failure,
) {
    when {
        failure.isNetworkError -> requireView().snackBar(
            "Проверьте Интернет соединение"
        )
        failure.errorCode == 400 -> {
            requireView().snackBar("Вы ввели некорректный номер")
        }
        failure.errorCode == 401 -> {
            requireView().snackBar("Ошибка авторизации")
        }
        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackBar(error)
        }
    }
}

fun View.snackBar(message: String, action: (() -> Unit)? = null) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    snackBar.setBackgroundTint(Color.WHITE)
    snackBar.setTextColor(Color.parseColor("#047BF8"))
    action?.let {
        snackBar.setAction("Retry") {
            it()
        }
    }
    snackBar.show()
}

fun String.removeElements(elements: List<String>): String {
    var newString = this
    elements.forEach {
        newString = newString.replace(it, "")
    }
    return newString
}


