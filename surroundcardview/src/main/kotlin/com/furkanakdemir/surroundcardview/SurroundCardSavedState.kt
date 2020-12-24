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
package com.furkanakdemir.surroundcardview

import android.os.Parcel
import android.os.Parcelable
import android.view.View.BaseSavedState
import com.furkanakdemir.surroundcardview.StartPoint.TOP_START
import com.furkanakdemir.surroundcardview.SurroundCardView.Companion.DURATION_DEFAULT

internal class SurroundCardSavedState : BaseSavedState {

    var savedIsSurrounded: Boolean = false
    var savedStartPoint: StartPoint = TOP_START
    var savedDuration: Long = DURATION_DEFAULT
    var savedSurroundColor: Int = -1
    var savedSurroundStrokeWidth: Int = 0
    var savedIsAnimatedOnRotation: Boolean = true

    constructor(superState: Parcelable?) : super(superState)

    constructor(source: Parcel) : super(source) {
        savedIsSurrounded = source.readByte().toInt() != 0
        savedIsAnimatedOnRotation = source.readByte().toInt() != 0
        savedStartPoint = StartPoint.values()[source.readInt()]
        savedSurroundColor = source.readInt()
        savedDuration = source.readLong()
        savedSurroundStrokeWidth = source.readInt()
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeByte((if (savedIsSurrounded) 1 else 0).toByte())
        out.writeByte((if (savedIsAnimatedOnRotation) 1 else 0).toByte())
        out.writeInt(savedStartPoint.ordinal)
        out.writeInt(savedSurroundColor)
        out.writeLong(savedDuration)
        out.writeInt(savedSurroundStrokeWidth)
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<SurroundCardSavedState> {
            override fun createFromParcel(source: Parcel): SurroundCardSavedState =
                SurroundCardSavedState(
                    source
                )

            override fun newArray(size: Int): Array<SurroundCardSavedState?> = arrayOfNulls(size)
        }
    }
}
