package com.example.testapp.ui.userInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.data.api.RetrofitBuilder
import com.example.testapp.data.repository.AppRepositoryImpl
import com.example.testapp.databinding.DialogUserInfoBinding
import com.example.testapp.ui.ViewModelFactory
import com.example.testapp.util.Resource
import com.example.testapp.util.handleApiError
import com.example.testapp.util.snackBar

class UserInfoDialogFragment : DialogFragment() {

    private var _binding: DialogUserInfoBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel by viewModels<UserInfoDialogFragmentViewModel> {
        ViewModelFactory(appRepository = AppRepositoryImpl(RetrofitBuilder.apiService))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DialogUserInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    if (response.value.user.status == "error") {
                        requireView().snackBar("Неверный код")
                    } else {
                        response.value.user.let { user ->
                            binding.nameValue.text = user.name
                            binding.sexValue.text = user.sex
                            binding.phoneNumberValue.text = user.phone_number
                            binding.emailValue.text = user.email
                            binding.birthdayValue.text = user.birth_day
                            binding.cityValue.text = user.city
                            binding.ratingValue.text = user.rating
                        }
                    }
                }
                is Resource.Failure -> handleApiError(response)
            }
        })

        binding.buttonOk.setOnClickListener {
            findNavController().navigate(R.id.action_userInfoDialogFragment_to_inputCodeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}