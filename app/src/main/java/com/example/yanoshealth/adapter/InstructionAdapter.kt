package com.example.yanoshealth.adapter



import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


import com.example.yanoshealth.databinding.ListItemYanosInstrutionBinding
import com.example.yanoshealth.domain.Instruction



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
        fun bind(networkInstruction:Instruction) {
            binding.inst = networkInstruction
            Log.i("MESSAGE", networkInstruction.toString())
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }


    }
    class OnClickListener(val clickListener: (instruction:Instruction) -> Unit) {
        fun onClick(networkInstruction: Instruction) = clickListener(networkInstruction)
    }

}

private class InstructionDiffCallback: DiffUtil.ItemCallback<Instruction>(){
    override fun areItemsTheSame(oldItem: Instruction, newItem: Instruction): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Instruction, newItem: Instruction): Boolean {
        return  oldItem == newItem
    }

}



