package ru.nikich5.funfacts.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.nikich5.funfacts.dto.Fact

@Entity
data class FactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val text: String
) {
    fun toDto() = Fact(id, text)

    companion object {
        fun fromDto(dto: Fact) = FactEntity(dto.id, dto.text)
    }

    fun List<FactEntity>.toDto(): List<Fact> = map(FactEntity::toDto)
    fun List<Fact>.toEntity(): List<FactEntity> = map(FactEntity.Companion::fromDto)
    fun Fact.toEntity(): FactEntity = FactEntity.fromDto(this)
}