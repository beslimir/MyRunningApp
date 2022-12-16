package com.beslimir.myrunningapp.presentation.main_screen

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.beslimir.myrunningapp.R
import com.beslimir.myrunningapp.adapters.RunAdapter
import com.beslimir.myrunningapp.databinding.FragmentRunBinding
import com.beslimir.myrunningapp.other.SortType
import com.beslimir.myrunningapp.util.TrackingUtility
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class RunFragment: Fragment(R.layout.fragment_run), EasyPermissions.PermissionCallbacks {

    private val mainViewModel: MainViewModel by viewModels()
    lateinit var runBinding: FragmentRunBinding
    private lateinit var runAdapter: RunAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        runBinding = FragmentRunBinding.bind(view)

        requestPermissions()
        setupRecyclerView()

        when (mainViewModel.sortType) {
            SortType.DATE -> runBinding.spFilter.setSelection(0)
            SortType.DISTANCE -> runBinding.spFilter.setSelection(1)
            SortType.CALORIES_BURNED -> runBinding.spFilter.setSelection(2)
            SortType.RUNNING_TIME -> runBinding.spFilter.setSelection(3)
            SortType.AVG_SPEED -> runBinding.spFilter.setSelection(4)
        }

        runBinding.spFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                when (pos) {
                    0 -> mainViewModel.sortRuns(SortType.DATE)
                    1 -> mainViewModel.sortRuns(SortType.DISTANCE)
                    2 -> mainViewModel.sortRuns(SortType.CALORIES_BURNED)
                    3 -> mainViewModel.sortRuns(SortType.RUNNING_TIME)
                    4 -> mainViewModel.sortRuns(SortType.AVG_SPEED)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        mainViewModel.runs.observe(viewLifecycleOwner, Observer {
            runAdapter.submitList(it)
        })

        runBinding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }
    }


    private fun setupRecyclerView() = runBinding.rvRuns.apply {
        runAdapter = RunAdapter()
        adapter = runAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun requestPermissions() {
        if (TrackingUtility.hasLocationPermissions(requireContext())) {
            return
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions to use this app.",
                0,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions to use this app.",
                0,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}