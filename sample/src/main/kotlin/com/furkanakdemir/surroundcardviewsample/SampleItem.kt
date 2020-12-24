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

import com.furkanakdemir.surroundcardview.StartPoint
import com.furkanakdemir.surroundcardview.StartPoint.TOP_START

data class SampleItem(
    val id: Int,
    val title: String,
    var isSurrounded: Boolean = false,
    val color: Int = INVALID_COLOR,
    val startPoint: StartPoint = TOP_START,
    val duration: Long = 600L,
    val strokeWidth: Int = 12,
) {

    val stateText: String
        get() {
            return if (isSurrounded) {
                "Surrounded"
            } else {
                "Released"
            }
        }

    private companion object {
        private const val INVALID_COLOR = -1
    }
}
