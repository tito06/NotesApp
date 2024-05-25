package com.example.mynotes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val Migration_1_2 = object : Migration(1,2){
    @RequiresApi(Build.VERSION_CODES.O)
    override fun migrate(db: SupportSQLiteDatabase) {
        val currentDate = LocalDate.now()
        val formattedDate = currentDate.format(DateTimeFormatter.ISO_DATE)
        db.execSQL("ALTER TABLE notes ADD COLUMN date TEXT NOT NULL DEFAULT '$formattedDate'")
    }

}

val Migration_2_3 = object : Migration(2,3){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE notes ADD COLUMN category TEXT NOT NULL DEFAULT ''")
    }


}