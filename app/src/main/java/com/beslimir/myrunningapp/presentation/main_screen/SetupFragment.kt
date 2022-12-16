package com.beslimir.myrunningapp.presentation.main_screen

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.beslimir.myrunningapp.R
import com.beslimir.myrunningapp.databinding.FragmentSetupBinding
import com.beslimir.myrunningapp.other.Constants.KEY_FIRST_TIME_TOGGLE
import com.beslimir.myrunningapp.other.Constants.KEY_NAME
import com.beslimir.myrunningapp.other.Constants.KEY_WEIGHT
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SetupFragment: Fragment(R.layout.fragment_setup) {

    @Inject lateinit var sharedPrefs: SharedPreferences
    @set:Inject var isFirstOpened = true
    lateinit var setupBinding: FragmentSetupBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBinding = FragmentSetupBinding.bind(view)

        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.setupFragment, true)
            .build()
        if (!isFirstOpened) {
            findNavController().navigate(
                R.id.action_setupFragment_to_runFragment,
                savedInstanceState,
                navOptions
            )
        }

        setupBinding.tvContinue.setOnClickListener {
            val success = writePersonalDataToSharedPref()
            if (success) {
                findNavController().navigate(
                    R.id.action_setupFragment_to_runFragment,
                    savedInstanceState,
                    navOptions
                )
            } else {
                Snackbar.make(requireView(), "Please, enter all the fields", Snackbar.LENGTH_SHORT).show()
            }

        }
    }

    private fun writePersonalDataToSharedPref(): Boolean {
        val name = setupBinding.etName.text.toString()
        val weight = setupBinding.etWeight.text.toString()
        if (name.isEmpty() || weight.isEmpty()) {
            return false
        }
        sharedPrefs.edit()
            .putString(KEY_NAME, name)
            .putFloat(KEY_WEIGHT, weight.toFloat())
            .putBoolean(KEY_FIRST_TIME_TOGGLE, false)
            .apply()
        isFirstOpened = false

        val toolbarText = "Let's go, $name!"
        (requireActivity() as MainActivity).mainBinding.tvToolbarTitle.text = toolbarText

        return true
    }

}