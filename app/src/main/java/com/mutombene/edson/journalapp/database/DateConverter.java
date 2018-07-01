package com.mutombene.edson.journalapp.database;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by EMutombene on 6/25/2018.
 */

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long timeStamp) {
        return timeStamp == null ? null : new Date(timeStamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
