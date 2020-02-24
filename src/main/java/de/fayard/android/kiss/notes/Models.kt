package de.fayard.android.kiss.notes

import org.threeten.bp.Instant
import java.util.UUID

interface ListItem

data class Note(
    val id: String = UUID.randomUUID().toString(),
    val description: String,
    val title: String,
    val url: String? = null,
    val createdAt: Instant = Instant.now()
) : ListItem

fun List<Note>.capitalize() =
    this.map { it.copy(description = it.description.toUpperCase(), title = it.title.toUpperCase()) }

data class SectionItem(val title: String) : ListItem

val LoadingList = listOf(SectionItem("Loading, please wait"))
