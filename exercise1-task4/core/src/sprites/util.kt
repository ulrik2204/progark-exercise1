package sprites

import kotlin.math.pow
import kotlin.random.Random

fun random(min: Float, max: Float, allowNegative: Boolean = true): Float {
        val negativity = if (allowNegative) (-1f).pow(Random.nextInt(0, 2)) else 1f
        return (min + Random.nextFloat() * (max-min)) * negativity
    }
