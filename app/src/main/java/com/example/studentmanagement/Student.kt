package com.example.studentmanagement

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "students")
data class Student(
    val name: String,
    @PrimaryKey val mssv: String, // MSSV là id chính
    val email: String,
    val phone: String
) : Serializable
