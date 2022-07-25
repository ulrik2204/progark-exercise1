package sprites

import com.badlogic.gdx.math.Vector2

class Ball(
    x: Float,
    y: Float,
    startAcc: Vector2 = Vector2(-1f, -9.8f),
    startSpeed: Vector2 = Vector2(0f, 0f),
    private val maxSpeed: Float = 10f
) : GameSprite(listOf("ball.png"), x, y, startAcc, maxSpeed, startSpeed = startSpeed) {
}
