package iti.mad.marketly.presentation.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.auth.FirebaseAuth
import iti.mad.marketly.R
import iti.mad.marketly.ResultResponse
import iti.mad.marketly.data.model.Customer
import iti.mad.marketly.data.model.CustomerBody
import iti.mad.marketly.databinding.FragmentRegisterBinding
import iti.mad.marketly.presentation.view.MainActivity
import iti.mad.marketly.presentation.auth.viewmodel.RegisterViewModel
import iti.mad.marketly.presentation.setCustomFocusChangeListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var alreadyHaveAccountTextView: TextView
    private lateinit var errorTextView: TextView
    private lateinit var backButton: ImageButton
    lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth
    private val registerViewModel by viewModels<RegisterViewModel> {
        RegisterViewModel.Factory
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                registerViewModel.customerRespoonse.collect { uiState ->

                    when (uiState) {
                        is ResultResponse.OnSuccess -> {
                            //todo
                            // navigate to login fragment
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        }

                        is ResultResponse.OnLoading -> {
                            //todo

                        }

                        is ResultResponse.OnError -> {
                            //todo
                            AlertDialog.Builder(requireContext())
                                .setTitle(getString(R.string.error))
                                .setMessage(getString(R.string.email_or_password_error))
                                .setPositiveButton(R.string.ok) { _, _ -> }
                                .setIcon(R.drawable.ic_baseline_clear_24)
                                .show()
                        }
                    }
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailEditText = binding.emailEt.apply {
            setCustomFocusChangeListener()
        }
        nameEditText = binding.nameEt.apply {
            setCustomFocusChangeListener()
        }
        alreadyHaveAccountTextView = binding.alreadyHaveAccTV.apply {
            setOnClickListener {
                //todo:

            }
        }
        passwordEditText = binding.PasswordET.apply { setCustomFocusChangeListener() }
        confirmPasswordEditText = binding.confirmPasswordET.apply { setCustomFocusChangeListener() }
        registerButton = binding.signUpBtn
        errorTextView = binding.emailErrorTV
        /* backButton.setOnClickListener {

         }*/

        registerButton.setOnClickListener {

            if (isUserValid()) {
                auth.createUserWithEmailAndPassword(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                )
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            val customer = Customer()
                            customer.email = emailEditText.text.toString()
                            customer.last_name = passwordEditText.text.toString()
                            customer.first_name = nameEditText.text.toString()
                            val customerBody = CustomerBody(customer)
                            registerViewModel.registerUser(customerBody)

                        } else {
                            errorTextView.text = task.exception?.message
                        }
                    }

            }

        }

    }

    private fun isUserValid(): Boolean {

        var isUserDataValid = false
        when {
            nameEditText.text.isNullOrEmpty() -> nameEditText.error =
                getString(R.string.required)

            emailEditText.text.isNullOrEmpty() -> emailEditText.error =
                getString(R.string.required)

            !isValidEmail(emailEditText.text.toString()) -> emailEditText.error =
                getString(R.string.invalid_email)

            passwordEditText.text.isNullOrEmpty() -> binding.passwordErrorTV.error =
                getString(R.string.required)

            confirmPasswordEditText.text.isNullOrEmpty() -> confirmPasswordEditText.error =
                getString(R.string.required)

            passwordEditText.text.length < 8 -> passwordEditText.error =
                getString(R.string.password_length_error)

            confirmPasswordEditText.text.toString() != passwordEditText.text.toString() -> binding.confirmPasswordET.error =
                getString(R.string.confirm_password_error)

            else -> isUserDataValid = true
        }
        return isUserDataValid
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return emailRegex.matches(email)
    }
}