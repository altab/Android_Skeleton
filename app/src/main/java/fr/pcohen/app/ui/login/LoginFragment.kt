package fr.pcohen.app.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import fr.pcohen.app.BuildConfig
import fr.pcohen.app.R
import fr.pcohen.app.base.BaseFragment
import fr.pcohen.app.base.onBackPressedCallBackNavControllerOrParent
import fr.pcohen.app.databinding.FragmentLoginBinding
import javax.inject.Inject

class LoginFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val loginViewModel: LoginViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)

        val navController = findNavController()
        bindUi(binding, navController)
        subscribeUi(binding, navController)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallBackNavControllerOrParent())
    }

    private fun bindUi(binding: FragmentLoginBinding, navController: NavController) {
        if (BuildConfig.DEBUG) {
            binding.etEmail.setText("bob@test.com")
            binding.etPassword.setText("Aa1234567!")
        }
        binding.btLogin.setOnClickListener {
            loginViewModel.login(binding.etEmail.text?.toString(), binding.etPassword.text?.toString())
        }
        binding.btRegister.setOnClickListener {
            navController.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment(false))
        }
        binding.mbForgotPassword.setOnClickListener {
        }
    }

    private fun subscribeUi(binding: FragmentLoginBinding, navController: NavController) {
        loginViewModel.authenticationState.observe(viewLifecycleOwner, Observer {
            if (it == LoginViewModel.AuthenticationState.AUTHENTICATED) {
                navController.popBackStack()
            }
        })
        loginViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.isLoading = it
        })
        loginViewModel.loginState.observe(viewLifecycleOwner, Observer {
            val errorResource = when (it.getContentIfNotHandled() ?: return@Observer) {
                LoginViewModel.LoginState.FILL_FIELDS -> R.string.fill_all_fields
                LoginViewModel.LoginState.NO_INTERNET -> R.string.check_internet
                LoginViewModel.LoginState.NOT_ACTIVATED -> R.string.not_activated
                LoginViewModel.LoginState.WRONG_CREDENTIALS -> R.string.wrong_credentials
            }
            Toast.makeText(context, errorResource, Toast.LENGTH_LONG).show()
        })
    }
}
