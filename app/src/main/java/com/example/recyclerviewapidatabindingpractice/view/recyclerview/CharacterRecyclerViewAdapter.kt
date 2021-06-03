package com.example.recyclerviewapidatabindingpractice.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewapidatabindingpractice.databinding.CharactersRecyclerItemBinding
import com.example.recyclerviewapidatabindingpractice.viewModel.CharactersViewModel

class CharacterRecyclerViewAdapter(private val viewModel: CharactersViewModel,
                                   private val parentLifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<CharacterRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CharactersRecyclerItemBinding.inflate(layoutInflater, parent, false)
        return CharacterRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterRecyclerViewHolder, position: Int) {
        holder.binding.character = viewModel.getCharacter(position)
//        holder.binding.characterImage = viewModel.getImage(position)
        holder.binding.lifecycleOwner = parentLifecycleOwner
        holder.binding.executePendingBindings()
    }


    override fun getItemCount(): Int {
        return viewModel.getCharacterCount() ?: 0
    }
}