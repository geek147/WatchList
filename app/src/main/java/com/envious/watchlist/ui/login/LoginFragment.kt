package com.envious.watchlist.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.envious.watchlist.R
import com.envious.watchlist.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            buttonLogin.setOnClickListener {
                if (textUserName.text.isNullOrEmpty() ||
                    textPassword.text.isNullOrEmpty()
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Username atau password tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_watchListFragment)
                }
            }
        }
    }
}
