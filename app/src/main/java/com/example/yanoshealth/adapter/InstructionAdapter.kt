package com.example.yanoshealth.adapter



import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


import com.example.yanoshealth.databinding.ListItemYanosInstrutionBinding
import com.example.yanoshealth.firebase.Instruction


class InstructionAdapter(val onClickListener: OnClickListener): ListAdapter<Instruction, InstructionAdapter.ViewHolder>(InstructionDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i("Information provided","Adapter created")
        return ViewHolder(ListItemYanosInstrutionBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val instruction = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(instruction)
        }
        holder.bind(instruction)
    }
    class ViewHolder(private  val binding: ListItemYanosInstrutionBinding):
            RecyclerView.ViewHolder(binding.root){
        fun bind(instruction: Instruction) {
            binding.inst = instruction
            Log.i("MESSAGE", instruction.toString())
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }


    }
    class OnClickListener(val clickListener: (instructionProperty: Instruction) -> Unit) {
        fun onClick(instruction: Instruction) = clickListener(instruction)
    }

}

private class InstructionDiffCallback: DiffUtil.ItemCallback<Instruction>(){
    override fun areItemsTheSame(oldItem: Instruction, newItem: Instruction): Boolean {
        return oldItem.weekno == newItem.weekno
    }

    override fun areContentsTheSame(oldItem: Instruction, newItem: Instruction): Boolean {
        return  oldItem == newItem
    }

}



