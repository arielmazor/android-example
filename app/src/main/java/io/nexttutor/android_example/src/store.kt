package io.nexttutor.android_example.src

import kotlinx.coroutines.flow.MutableStateFlow

data class Tutor(
    val name: String
)

object Store {
    var tutor: MutableStateFlow<Tutor?> = MutableStateFlow(null)
}