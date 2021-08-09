package org.bandev.buddhaquotes.core

import org.bandev.buddhaquotes.R

object Images {

    fun heart(liked: Boolean): Int {
        return if (liked) R.drawable.ic_heart_red else R.drawable.ic_heart_outline
    }

    const val MONK: Int = 1
    const val DHARMA_WHEEL: Int = 2
    const val ANAHATA: Int = 3
    const val MANDALA: Int = 4
    const val ENDLESS_KNOT: Int = 5
    const val ELEPHANT: Int = 6
    const val TEMPLE: Int = 7
    const val LAMP: Int = 8
    const val SHRINE: Int = 9
    const val LOTUS: Int = 10
    const val LOTUS_WATER: Int = 11

}