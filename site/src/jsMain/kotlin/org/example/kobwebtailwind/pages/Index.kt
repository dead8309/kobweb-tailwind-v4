package org.example.kobwebtailwind.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.text.SpanText

fun Modifier.tw(tw: String): Modifier = this.classNames(tw.split(" "))

@Page
@Composable
fun HomePage() {
    Box(
        modifier = Modifier.tw("flex items-center justify-center min-h-screen bg-gray-50")
    ) {
        Box(
            modifier = Modifier.tw("bg-gradient-to-br from-purple-600 via-violet-600 to-orange-500 border border-purple-300 rounded-lg shadow-lg px-8 py-6 transform transition-all duration-300 hover:scale-105 hover:shadow-xl hover:from-purple-500 hover:via-violet-500 hover:to-orange-400 cursor-pointer")
        ) {
            SpanText(
                text = "kobweb + tailwind",
                modifier = Modifier.tw("text-xl font-semibold text-white drop-shadow-sm")
            )
        }
    }
}