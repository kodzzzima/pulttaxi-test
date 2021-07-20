package com.example.testapp.ui.inputNumber

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.data.api.RetrofitBuilder
import com.example.testapp.data.repository.AppRepositoryImpl
import com.example.testapp.databinding.FragmentInputNumberBinding
import com.example.testapp.ui.ViewModelFactory
import com.example.testapp.util.PhoneNumberFormatting
import com.example.testapp.util.Resource
import com.example.testapp.util.handleApiError
import kotlinx.coroutines.launch

class InputNumberFragment : Fragment(R.layout.fragment_input_number) {

    private lateinit var binding: FragmentInputNumberBinding

    private val viewModel by viewModels<InputNumberFragmentViewModel> {
        ViewModelFactory(appRepository = AppRepositoryImpl(RetrofitBuilder.apiService))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInputNumberBinding.bind(view)

        binding.progressbar.isVisible = false
        binding.buttonContinue.isEnabled = false

        setupObserver()

        binding.editText.doOnTextChanged { _, _, _, _ ->
            PhoneNumberFormatting
            when {
                PhoneNumberFormatting.countNumber ?: 0 > 10 -> {
                    binding.buttonContinue.isEnabled = false
                    binding.textInputLayout.error = " Ошибка"
                }
                PhoneNumberFormatting.countNumber ?: 0 < 10 -> {
                    binding.buttonContinue.isEnabled = false
                    binding.textInputLayout.error = null
                }
                PhoneNumberFormatting.countNumber ?: 0 == 10 ->
                    binding.buttonContinue.isEnabled = true
            }
        }

        binding.editText.addTextChangedListener(PhoneNumberFormatting)

        binding.buttonContinue.setOnClickListener {
            enterNumber()
            binding.editText.clearFocus()
        }
    }

    private fun setupObserver() {
        viewModel.smsCodeResponse.observe(viewLifecycleOwner, {
            binding.progressbar.isVisible = it is Resource.Loading
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveUserPhone("7" +
                                PhoneNumberFormatting.unFormattedNumber.toString())

                        findNavController()
                            .navigate(R.id.action_inputNumberFragment_to_inputCodeFragment)
                    }
                }
                is Resource.Failure -> handleApiError(it)
            }
        })
    }

    private fun enterNumber() {
        val phoneNumber = "7" + PhoneNumberFormatting.unFormattedNumber.toString()
        viewModel.enterNumber(phoneNumber)
    }
}