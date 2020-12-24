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

import android.graphics.Canvas
import com.furkanakdemir.surroundcardview.canvas.CanvasTransformerFactory.DEGREE_FLIPPED
import com.furkanakdemir.surroundcardview.canvas.CanvasTransformerFactory.SCALE_DEFAULT_X
import com.furkanakdemir.surroundcardview.canvas.CanvasTransformerFactory.SCALE_DEFAULT_Y

class BottomEndCanvasTransformer(private val width: Float, private val height: Float) :
    CanvasTransformer {

    override fun transform(canvas: Canvas) {
        canvas.translate(width, height)
        canvas.scale(SCALE_DEFAULT_X, SCALE_DEFAULT_Y)
        canvas.rotate(DEGREE_FLIPPED)
    }
}
