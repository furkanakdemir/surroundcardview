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
package com.furkanakdemir.surroundcardview.canvas

import com.furkanakdemir.surroundcardview.StartPoint
import com.furkanakdemir.surroundcardview.StartPoint.BOTTOM_END
import com.furkanakdemir.surroundcardview.StartPoint.BOTTOM_START
import com.furkanakdemir.surroundcardview.StartPoint.TOP_END
import com.furkanakdemir.surroundcardview.StartPoint.TOP_START

object CanvasTransformerFactory {

    const val DEGREE_FLIPPED = 180f
    const val DEGREE_DEFAULT = 0f
    const val SCALE_MIRROR_X = -1f
    const val SCALE_MIRROR_Y = 1f
    const val SCALE_DEFAULT_X = 1f
    const val SCALE_DEFAULT_Y = 1f

    private const val SIZE_DEFAULT = 0f

    fun create(width: Float, height: Float, startPoint: StartPoint = TOP_START): CanvasTransformer {
        return when (startPoint) {
            TOP_START -> TopStartCanvasTransformer()
            TOP_END -> TopEndCanvasTransformer(width)
            BOTTOM_START -> BottomStartCanvasTransformer(height)
            BOTTOM_END -> BottomEndCanvasTransformer(width, height)
        }
    }

    fun default(): CanvasTransformer = create(SIZE_DEFAULT, SIZE_DEFAULT)
}
