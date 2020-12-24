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

import com.furkanakdemir.surroundcardview.StartPoint.BOTTOM_END
import com.furkanakdemir.surroundcardview.StartPoint.BOTTOM_START
import com.furkanakdemir.surroundcardview.StartPoint.TOP_END
import com.furkanakdemir.surroundcardview.StartPoint.TOP_START

@Suppress("MagicNumber")
object SampleDataProvider {

    private const val DURATION_CUSTOM = 2000L

    val SAMPLE_ITEMS = mutableListOf<SampleItem>()
    var standAloneState = false

    init {
        val customColor = R.color.customColor
        val defaultColor = R.color.scv_surround_color_default
        val defaultWidth = R.dimen.scv_stroke_width_default
        val customWidth = R.dimen.stroke_width_custom

        SAMPLE_ITEMS.apply {
            add(SampleItem(0, "Default", color = defaultColor, strokeWidth = defaultWidth))
            add(
                SampleItem(
                    1,
                    "Custom Color (#E91E63)",
                    color = customColor,
                    strokeWidth = defaultWidth
                )
            )
            add(
                SampleItem(
                    2,
                    "Start Point (TOP_START)",
                    color = defaultColor,
                    strokeWidth = defaultWidth,
                    startPoint = TOP_START
                )
            )
            add(
                SampleItem(
                    3,
                    "Start Point (TOP_END)",
                    color = defaultColor,
                    strokeWidth = defaultWidth,
                    startPoint = TOP_END
                )
            )
            add(
                SampleItem(
                    4,
                    "Start Point (BOTTOM_START)",
                    color = defaultColor,
                    strokeWidth = defaultWidth,
                    startPoint = BOTTOM_START
                )
            )
            add(
                SampleItem(
                    5,
                    "Start Point (BOTTOM_END)",
                    color = defaultColor,
                    strokeWidth = defaultWidth,
                    startPoint = BOTTOM_END
                )
            )
            add(
                SampleItem(
                    6,
                    "Custom Duration ($DURATION_CUSTOM ms)",
                    color = defaultColor,
                    strokeWidth = defaultWidth,
                    duration = DURATION_CUSTOM
                )
            )
            add(
                SampleItem(
                    7,
                    "Custom Stroke Width (2dp)",
                    color = defaultColor,
                    strokeWidth = customWidth
                )
            )
            add(
                SampleItem(
                    8,
                    "Initial State (True)",
                    isSurrounded = true,
                    color = defaultColor,
                    strokeWidth = defaultWidth
                )
            )
        }
    }
}
