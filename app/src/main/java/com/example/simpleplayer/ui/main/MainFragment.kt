package com.example.simpleplayer.ui.main

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.transition.Fade
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.simpleplayer.App
import com.example.simpleplayer.MainActivity
import com.example.simpleplayer.R
import com.example.simpleplayer.ui.film.PlayerFragment
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.player_fragment.*
import javax.inject.Inject

class MainFragment : Fragment(), MainActivity.BeckPressedHelper {

    companion object {
        private var instance: MainFragment? = null
        fun getInstance() =
            instance ?: let {
                instance = MainFragment()
                instance!!
            }
    }

    @Inject
    lateinit var mainFactory: MainFactory

    private lateinit var viewModel: MainViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).let { it.backPressedHelper = this }
        (context.applicationContext as App).mainViewModelComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, mainFactory).get(MainViewModel::class.java)
        films_list.adapter = viewModel.listAdapter
        viewModel.liveData.observe(viewLifecycleOwner, createObserver())
        viewModel.setupDataOnView()
        activity?.let { it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT }
    }

    private fun createObserver() = Observer { response: MainViewModel.Response ->
        when (response) {
            is MainViewModel.Response.Error ->
                Toast.makeText(
                    viewModel.app.applicationContext,
                    response.errorMsg,
                    Toast.LENGTH_SHORT
                ).show()

            is MainViewModel.Response.ActionItemClick ->
                activity?.run {
                    (this as MainActivity).mainFragmentExit = true
                    supportFragmentManager.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.main_container, PlayerFragment.getInstance(response.film))
                        .commit()
                }
        }

    }

    override fun backPressed() {
        activity?.finish()
    }


}