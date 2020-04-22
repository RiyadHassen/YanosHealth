/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.yanoshealth

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView




import com.example.yanoshealth.adapter.InstructionAdapter
import com.example.yanoshealth.domain.Instruction
import com.example.yanoshealth.network.NetworkInstruction


@BindingAdapter("listHospital")
fun bindHRecyclerView(recyclerView: RecyclerView,data:List<Instruction>?){
    val adapter = recyclerView.adapter as InstructionAdapter
    adapter.submitList(data)
}
@BindingAdapter("hname")
fun hname(textView: TextView, networkInstruction:Instruction){
       networkInstruction.let {
           textView.text = it.hname
       }
}
@BindingAdapter("relative")
fun hrelative(textView: TextView, networkInstruction:Instruction){
    networkInstruction.let {
        textView.text = it.relativeAddress
    }
}
@BindingAdapter("hpass")
fun hother(textView: TextView, networkInstruction:Instruction){
    networkInstruction.let {
        textView.text = it.phoneNumb
    }
}

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
