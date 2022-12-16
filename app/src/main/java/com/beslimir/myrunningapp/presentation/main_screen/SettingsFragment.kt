package com.beslimir.myrunningapp.presentation.main_screen

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.beslimir.myrunningapp.R
import com.beslimir.myrunningapp.databinding.FragmentSettingsBinding
import com.beslimir.myrunningapp.databinding.FragmentSetupBinding
import com.beslimir.myrunningapp.other.Constants.KEY_NAME
import com.beslimir.myrunningapp.other.Constants.KEY_WEIGHT
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment: Fragment(R.layout.fragment_settings) {

    lateinit var settingsBinding: FragmentSettingsBinding
    @Inject lateinit var sharedPrefs: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsBinding = FragmentSettingsBinding.bind(view)

        loadFieldsFromSharedPrefs()

        settingsBinding.btnApplyChanges.setOnClickListener {
            val success = applyChangesToSharedPref()
            if (success) {
                Snackbar.make(view, "Changes saved", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(view, "Please, fill out all fields", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadFieldsFromSharedPrefs() {
        val name = sharedPrefs.getString(KEY_NAME, "")
        val weight = sharedPrefs.getFloat(KEY_WEIGHT, 80f)
        settingsBinding.etName.setText(name)
        settingsBinding.etWeight.setText(weight.toString())

    }

    private fun applyChangesToSharedPref(): Boolean {
        val nameText = settingsBinding.etName.text.toString()
        val weightText = settingsBinding.etWeight.text.toString()
        if (nameText.isEmpty() || weightText.isEmpty()) {
            return false
        }

        sharedPrefs.edit()
            .putString(KEY_NAME, nameText)
            .putFloat(KEY_WEIGHT, weightText.toFloat())
            .apply()
        val toolbarText = "Let's go, $nameText"
        (requireActivity() as MainActivity).mainBinding.tvToolbarTitle.text = toolbarText

        return true
    }

}