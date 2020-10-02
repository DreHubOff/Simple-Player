package com.example.simpleplayer.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.simpleplayer.App
import com.example.simpleplayer.R
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var mainFactory: MainFactory

    private lateinit var viewModel: MainViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
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
    }

    private fun createObserver() = Observer { response: MainViewModel.Response ->
        when (response) {
            is MainViewModel.Response.Error ->
                Toast.makeText(
                    viewModel.app.applicationContext,
                    response.errorMsg,
                    Toast.LENGTH_SHORT
                ).show()
        }

    }


}