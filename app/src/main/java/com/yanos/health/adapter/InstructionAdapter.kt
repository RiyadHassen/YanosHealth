package com.yanos.health.adapter



import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions


import com.yanos.health.databinding.ListItemYanosInstrutionBinding
import com.yanos.health.firebase.Instruction

class InstructionAdapter(val onClickListener: OnClickListener, val option:FirebaseRecyclerOptions<Instruction>): FirebaseRecyclerAdapter<Instruction, ViewHolder>(option){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemYanosInstrutionBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int, post: Instruction) {
        // Bind to ViewHolder
        val instruction = getItem(position)
        viewHolder.itemView.setOnClickListener{
            onClickListener.onClick(instruction)
        }
        viewHolder.bind(post)
    }

    class OnClickListener(val clickListener: (instructionProperty: Instruction) -> Unit) {
        fun onClick(instruction: Instruction) = clickListener(instruction)
    }

}
class ViewHolder(private  var binding: ListItemYanosInstrutionBinding):
    RecyclerView.ViewHolder(binding.root) {
    fun bind(instruction: Instruction) {
        binding.inst = instruction
        binding.executePendingBindings()
    }
}



