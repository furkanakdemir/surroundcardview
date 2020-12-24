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

import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkanakdemir.surroundcardviewsample.SampleDataProvider.SAMPLE_ITEMS
import com.furkanakdemir.surroundcardviewsample.SampleDataProvider.standAloneState
import com.furkanakdemir.surroundcardviewsample.databinding.ActivitySampleBinding

class SampleActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySampleBinding
    private lateinit var sampleAdapter: SampleAdapter

    private val handler = Handler()
    private var listState: Parcelable? = null

    private lateinit var sampleItems: MutableList<SampleItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sampleItems = SAMPLE_ITEMS

        setup()
    }

    override fun onResume() {
        super.onResume()
        if (listState != null) {
            binding.sampleRecyclerView.layoutManager?.onRestoreInstanceState(listState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        listState = binding.sampleRecyclerView.layoutManager?.onSaveInstanceState()
        outState.putParcelable(KEY_STATE_LIST, listState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        listState = savedInstanceState.getParcelable(KEY_STATE_LIST)
    }

    private fun setup() = with(binding) {
        sampleSurroundCardView.setSurrounded(standAloneState)
        sampleTitleTextView.text = getString(R.string.sample_stand_alone)

        actionButton.text = getString(R.string.sample_surround)
        actionButton.setOnClickListener {
            if (!standAloneState) {
                sampleSurroundCardView.surround()
                actionButton.text = getString(R.string.sample_release)
                actionButton.setIconResource(R.drawable.ic_unlocked)
            } else {
                sampleSurroundCardView.release()
                actionButton.text = getString(R.string.sample_surround)
                actionButton.setIconResource(R.drawable.ic_locked)
            }

            standAloneState = standAloneState.not()
        }

        sampleAdapter = SampleAdapter(
            onAnimationEnd = {
                val index = sampleItems.indexOf(it)
                val sampleItem = sampleItems[index]
                sampleItem.isSurrounded = sampleItem.isSurrounded.not()

                handler.post { sampleAdapter.loadItem(sampleItem, index) }
            }
        )

        sampleRecyclerView.apply {
            adapter = sampleAdapter
            layoutManager = LinearLayoutManager(this@SampleActivity, RecyclerView.VERTICAL, false)
            itemAnimator = null
            addItemDecoration(DividerItemDecoration(this@SampleActivity, RecyclerView.VERTICAL))
        }

        sampleAdapter.loadItems(sampleItems)

        sampleTitleTextView.requestFocus()
    }

    private companion object {
        private const val KEY_STATE_LIST: String = "KEY_STATE_LIST"
    }
}
