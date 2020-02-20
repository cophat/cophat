package com.matheus.cophat.data.local.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

enum class SubAnswerType(val chosenSubAnswer: String) {
    RAGE("Raiva"),
    FEAR("Medo"),
    SADNESS("Tristeza"),
    TIREDNESS("Cansaço"),
    APPETITE_LACK("Falta de apetite"),
    SICK("Enjoo"),
    PAIN("Dor"),
    DIZZINESS("Tontura"),
    WEAKNESS("Fraqueza"),
    SLEEP("Sono"),
    IRRITATION("Irritação"),
    ATTENTION_DIFFICULTY("Dificuldade de atenção"),
    SWEAT("Suor"),
    MOTOR_DIFFICULTY("Dificuldade motora"),
    OTHER("Outros"),
    SWELLING("Inchaço"),
    WEIGHT_LOSS("Perda de peso"),
    WEIGHT_GAIN("Ganho de peso"),
    HAIR_LOSS("Queda de cabelo"),
    INJECTION_BRANDS("Marcas de injeção / agulha"),
    STAINS("Manchas"),
    BODY_PARTS_LOSS("Perda de partes do corpo"),
    PALLOR("Palidez"),
    YELLOW_EYE("Olho amarelo"),
    LAMPS("Caroços"),
    CATHETER("Ter cateter ou fistula"),
    SADNESS_BECAUSE("Tristeza por causa disso"),
    OPEN("Alternativa aberta"),
    PILL("Comprimido"),
    INJECTION("Injeção"),
    VEIN("Na veia")
}

@Parcelize
data class Alternative(
    var id: Int? = null,
    var type: SubAnswerType? = null,
    var description: String? = type?.name
) : Parcelable

@Parcelize
data class SubAnswer(
    var id: Int? = null,
    var alternatives: HashMap<String, AlternativeAnswer>? = null
) : Parcelable

@Parcelize
data class AlternativeAnswer(
    var id: Int? = null,
    var other: String? = null,
    var chosenSubAnswer: AnswerType? = null
) : Parcelable