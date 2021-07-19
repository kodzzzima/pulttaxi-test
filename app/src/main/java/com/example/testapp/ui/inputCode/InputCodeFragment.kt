package com.example.testapp.ui.inputCode

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.data.api.RetrofitBuilder
import com.example.testapp.data.repository.AppRepositoryImpl
import com.example.testapp.databinding.FragmentInputCodeBinding
import com.example.testapp.ui.ViewModelFactory
import com.example.testapp.util.Resource
import com.example.testapp.util.handleApiError
import com.example.testapp.util.snackBar
import kotlinx.coroutines.launch

class InputCodeFragment : Fragment(R.layout.fragment_input_code) {

    private lateinit var binding: FragmentInputCodeBinding

    private val viewModel by viewModels<InputCodeFragmentViewModel> {
        ViewModelFactory(appRepository = AppRepositoryImpl(RetrofitBuilder.apiService))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentInputCodeBinding.bind(view)
        binding.progressbar.isVisible = false

        setupObservers()

        binding.inputCode1.addTextChangedListener {
            if (binding.inputCode1.text.length == 1) {
                binding.inputCode2.requestFocus()
            }
        }

        binding.inputCode2.addTextChangedListener {
            if (binding.inputCode2.text.length == 1) {
                binding.inputCode3.requestFocus()
            } else if (binding.inputCode2.text.isEmpty()) {
                binding.inputCode1.requestFocus()
            }
        }

        binding.inputCode3.addTextChangedListener {
            if (binding.inputCode3.text.length == 1) {
                binding.inputCode4.requestFocus()
            } else if (binding.inputCode3.text.isEmpty()) {
                binding.inputCode2.requestFocus()
            }
        }

        binding.inputCode4.addTextChangedListener {
            if (binding.inputCode4.text.length == 1) {
                binding.inputCode4.clearFocus()
            } else if (binding.inputCode4.text.isEmpty()) {
                binding.inputCode3.requestFocus()
            }
        }

        binding.buttonDone.setOnClickListener {
            if (binding.inputCode1.text.isNotEmpty()
                && binding.inputCode2.text.isNotEmpty()
                && binding.inputCode3.text.isNotEmpty()
                && binding.inputCode4.text.isNotEmpty()
            ) {
                enterCode()
            }
        }

        binding.buttonRepeatSmsCode.setOnClickListener {
            sendSmsCode()
        }
    }

    private fun setupObservers() {
        viewModel.tokenResponse.observe(viewLifecycleOwner, {
            binding.progressbar.isVisible = it is Resource.Loading
            when (it) {
                is Resource.Success -> {
                    if (it.value.status == "error") {
                        requireView().snackBar("Неверный код")
                    } else {
                        lifecycleScope.launch {
                            viewModel.saveUserToken(
                                it.value.token!!,
                            )
                            findNavController().navigate(R.id.action_inputNumberFragment_to_inputCodeFragment)
                        }
                    }
                }
                is Resource.Failure -> handleApiError(it)
            }
        })

        viewModel.timerFlag.observe(viewLifecycleOwner, {
            if (it == true) {
                binding.buttonRepeatSmsCode.visibility = View.VISIBLE
                binding.repeatSmsCode.visibility = View.GONE
            } else {
                binding.buttonRepeatSmsCode.visibility = View.GONE
                binding.repeatSmsCode.visibility = View.VISIBLE
            }
        })
    }

    private fun sendSmsCode() {
        viewModel.repeatSmsCode()
        viewModel.startTimer()
    }


    private fun enterCode() {
        val smsCode = binding.inputCode1.text.toString() +
                binding.inputCode2.text.toString() +
                binding.inputCode3.text.toString() +
                binding.inputCode4.text.toString()

        lifecycleScope.launch {
            val phoneNum = viewModel.loadUserPhone()!!
            viewModel.enterCode(phoneNum, smsCode)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (viewModel.timer != null) viewModel.timer!!.cancel()
    }
}