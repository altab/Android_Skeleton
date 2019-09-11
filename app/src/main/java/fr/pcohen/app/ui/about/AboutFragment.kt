package fr.pcohen.app.ui.about

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import fr.pcohen.app.base.BaseFragment
import fr.pcohen.app.databinding.FragmentAboutBinding
import fr.pcohen.app.ui.login.LoginViewModel
import javax.inject.Inject

class AboutFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val loginViewModel: LoginViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAboutBinding.inflate(inflater, container, false)

        bindUi(binding)

        return binding.root
    }

    private fun bindUi(binding: FragmentAboutBinding) {
        val context = requireContext()
        val info = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
        binding.versionNumber = info.versionName

        binding.btLogout.setOnClickListener {
            loginViewModel.logout()
        }
    }
}
