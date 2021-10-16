/*
 * Copyright 2020 Furkan Akdemir
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
 */
package com.furkanakdemir.surroundcardviewsample

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.furkanakdemir.surroundcardview.ReleaseListener
import com.furkanakdemir.surroundcardview.SurroundListener
import com.furkanakdemir.surroundcardviewsample.SampleAdapter.SampleItemViewHolder
import com.furkanakdemir.surroundcardviewsample.databinding.ListItemSampleBinding

class SampleAdapter(private val onAnimationEnd: (SampleItem) -> Unit) :
    RecyclerView.Adapter<SampleItemViewHolder>() {

    private var sampleItems = mutableListOf<SampleItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleItemViewHolder {
        return SampleItemViewHolder(
            ListItemSampleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = sampleItems.size

    override fun onBindViewHolder(holder: SampleItemViewHolder, position: Int) {
        holder.bind(sampleItems[position], onAnimationEnd)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun loadItems(sampleItems: List<SampleItem>) {
        this.sampleItems = sampleItems.toMutableList()
        notifyDataSetChanged()
    }

    fun loadItem(sampleItem: SampleItem, position: Int) {
        sampleItems[position] = sampleItem
        notifyItemChanged(position)
    }

    class SampleItemViewHolder(private val binding: ListItemSampleBinding) :
        ViewHolder(binding.root) {

        fun bind(sampleItem: SampleItem, onAnimationEnd: (SampleItem) -> Unit) = with(binding) {
            sampleTitleTextView.text = sampleItem.title
            sampleSurroundCardView.setSurrounded(sampleItem.isSurrounded)
            sampleSurroundCardView.setSurroundStrokeColor(sampleItem.color)
            sampleSurroundCardView.setDuration(sampleItem.duration)
            sampleSurroundCardView.setStartPoint(sampleItem.startPoint)
            sampleSurroundCardView.setSurroundStrokeWidth(sampleItem.strokeWidth)
            sampleInnerTextView.text = sampleItem.stateText

            sampleSurroundCardView.setOnClickListener {
                if (sampleItem.isSurrounded) {
                    sampleSurroundCardView.release()
                } else {
                    sampleSurroundCardView.surround()
                }
            }

            sampleSurroundCardView.surroundListener = object : SurroundListener {
                override fun onSurround() {
                    onAnimationEnd(sampleItem)
                }
            }

            sampleSurroundCardView.releaseListener = object : ReleaseListener {
                override fun onRelease() {
                    onAnimationEnd(sampleItem)
                }
            }
        }
    }
}
