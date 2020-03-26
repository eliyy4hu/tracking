package com.example.tracking

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class Habit : Parcelable {
    constructor()
    public constructor(
        name: String,
        description: String,
        priority: Int,
        type: Int,
        frequency: String
    ) {
        this.name = name
        this.description = description
        this.priority = priority
        this.type = type
        this.frequency = frequency
    }



    public var name: String = ""
    public var description: String = ""
    public var priority: Int = 0
    public var type: Int = 0
    public var frequency: String = ""
    public val id: UUID = UUID.randomUUID()

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()?: ""
        description = parcel.readString()?: ""
        priority = parcel.readInt()?: 0
        type = parcel.readInt()?: 0
        frequency = parcel.readString()?: ""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(priority)
        parcel.writeInt(type)
        parcel.writeString(frequency)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Habit> {
        override fun createFromParcel(parcel: Parcel): Habit {
            return Habit(parcel)
        }

        override fun newArray(size: Int): Array<Habit?> {
            return arrayOfNulls(size)
        }
    }

}
