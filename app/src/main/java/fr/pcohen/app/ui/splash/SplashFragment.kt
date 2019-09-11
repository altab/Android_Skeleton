package fr.pcohen.app.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import fr.pcohen.app.R
import fr.pcohen.app.base.BaseFragment
import fr.pcohen.app.ui.login.LoginViewModel
import javax.inject.Inject

class SplashFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val loginViewModel: LoginViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        val navController = findNavController()

        subscribeUi(navController)

        return view
    }

    private fun subscribeUi(navController: NavController) {
        loginViewModel.authenticationState.observe(viewLifecycleOwner, Observer {
            when (it) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> navController.navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment())
                LoginViewModel.AuthenticationState.UNAUTHENTICATED -> navController.navigate(SplashFragmentDirections.actionSplashFragmentToLoginGraph())
            }
        })
    }
}
