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

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


import com.example.yanoshealth.adapter.InstructionAdapter
import com.example.yanoshealth.firebase.Instruction
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


@BindingAdapter("listInstruction")
fun bindHRecyclerView(recyclerView: RecyclerView,data:List<Instruction>?){

        val adapter = recyclerView.adapter as InstructionAdapter
        adapter.submitList(data)

}
@BindingAdapter("imageUrl")
fun bindingImage(imageView: ImageView, imageurl:String?){
    val storageRef = Firebase.storage.reference
    val pathReference = storageRef.child("images/$imageurl.jpg")
    Log.d("Image",pathReference.toString())

        var imageURL:String
        pathReference.downloadUrl.addOnSuccessListener { Uri ->

             imageURL= Uri.toString()
            Glide.with(imageView.context)
                .load(imageURL)
                .apply(RequestOptions().placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
                .into(imageView)

        }


}
@BindingAdapter("language")
fun hname(textView: TextView,instruction: Instruction){
       instruction.let {
           textView.text = it.language
       }
}
@BindingAdapter("content")
fun hrelative(textView: TextView,instruction: Instruction){
    instruction.let {
        textView.text = it.content
    }
}
@BindingAdapter("imageurl")
fun hother(textView: TextView,instruction: Instruction){
    instruction.let {
        textView.text = it.imageurl
    }
}
@BindingAdapter("weekno")
fun weekno(textView: TextView,instruction: Instruction){
    instruction.let {
        textView.text = it.weekno.toString()
    }
}
