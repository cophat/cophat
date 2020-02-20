package com.matheus.cophat.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.matheus.cophat.data.local.entity.Answer
import com.matheus.cophat.data.local.entity.ApplicationStatus
import com.matheus.cophat.data.local.entity.Patient
import java.util.*

class DataConverter {

    @TypeConverter
    fun toAnswers(answers: String?): HashMap<String, Answer>? {
        return answers?.let {
            val type = object : TypeToken<HashMap<String, Answer>>() {}.type
            Gson().fromJson<HashMap<String, Answer>>(answers, type)
        }
    }

    @TypeConverter
    fun fromAnswers(answers: HashMap<String, Answer>?): String? {
        return answers?.let {
            val type = object : TypeToken<HashMap<String, Answer>>() {}.type
            return Gson().toJson(answers, type)
        }
    }

    @TypeConverter
    fun toPatient(patient: String?): Patient? {
        return patient?.let { Gson().fromJson(patient, Patient::class.java) }
    }

    @TypeConverter
    fun fromPatient(patient: Patient?): String? {
        return Gson().toJson(patient)
    }

    @TypeConverter
    fun toApplicationStatus(status: String?): ApplicationStatus? {
        return status?.let { ApplicationStatus.valueOf(it) }
    }

    @TypeConverter
    fun fromApplicationStatus(status: ApplicationStatus?): String? {
        return status?.toString()
    }
}