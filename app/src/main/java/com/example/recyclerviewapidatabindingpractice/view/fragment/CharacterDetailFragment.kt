package com.example.recyclerviewapidatabindingpractice.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.recyclerviewapidatabindingpractice.R
import com.example.recyclerviewapidatabindingpractice.databinding.FragmentCharacterDetailBinding
import com.example.recyclerviewapidatabindingpractice.databinding.FragmentCharactersBinding
import com.example.recyclerviewapidatabindingpractice.viewModel.CharacterDetailViewModel
import com.example.recyclerviewapidatabindingpractice.viewModel.CharactersViewModel

class CharacterDetailFragment : Fragment() {

    private var characterId: String? = null

    private val viewModel: CharacterDetailViewModel by lazy {
        ViewModelProviders.of(this).get(CharacterDetailViewModel::class.java)
    }
    private lateinit var binding: FragmentCharacterDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            characterId = it.getString("character_id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_detail, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.characterId = characterId
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchCharacterDetail()
    }

    companion object {
        @JvmStatic
        fun newInstance(characterId: String) =
            CharacterDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("character_id", characterId)

                }
            }
    }
}