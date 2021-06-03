package com.example.recyclerviewapidatabindingpractice.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewapidatabindingpractice.R
import com.example.recyclerviewapidatabindingpractice.databinding.FragmentCharactersBinding
import com.example.recyclerviewapidatabindingpractice.view.recyclerview.CharacterRecyclerViewAdapter
import com.example.recyclerviewapidatabindingpractice.viewModel.CharactersViewModel
import kotlinx.android.synthetic.main.fragment_characters.*

class CharactersFragment : Fragment() {

    private val viewModel: CharactersViewModel by lazy {
        ViewModelProviders.of(this).get(CharactersViewModel::class.java)
    }
    private lateinit var binding: FragmentCharactersBinding
    private lateinit var recyclerAdapter: CharacterRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        recyclerAdapter = CharacterRecyclerViewAdapter(viewModel, viewLifecycleOwner)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_characters, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filtering_edit.addTextChangedListener(viewModel.createTextChangeListener())
        val recyclerView = characters_recycler_view
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = recyclerAdapter

        viewModel.liveCharacters.observe(viewLifecycleOwner) {
            println("test: character observe")
            recyclerAdapter.notifyDataSetChanged()
        }

        viewModel.liveImages.observe(viewLifecycleOwner) {
            println("test: image observe")
            recyclerAdapter.notifyDataSetChanged()
        }

    }

    override fun onStart() {
        super.onStart()

        viewModel.fetchCharacters()
    }

//    companion object {
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            CharactersFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}