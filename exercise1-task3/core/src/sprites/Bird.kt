package sprites

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

class Bird(
    x: Float,
    y: Float,
    startAcc: Vector2 = Vector2(1f, 1f),
    startSpeed: Vector2 = Vector2(0f, 0f),
    private val maxSpeed: Float = 10f
) : GameSprite(listOf("bird2.png"), x, y, startAcc, maxSpeed, false, startSpeed = startSpeed) {
}