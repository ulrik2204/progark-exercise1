package sprites

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import kotlin.math.abs


class Helicopter(
    x: Float,
    y: Float,
    startAcc: Vector2 = Vector2(-1f, -9.8f),
    private val maxSpeed: Float = 10f,
    private val difficultyInterval: Int = 10
) : GameSprite(
    listOf("heli1.png", "heli2.png", "heli3.png", "heli4.png"),
    x,
    y,
    startAcc,
    maxSpeed,
    mass = 1f
) {

    // Determining what state it is in
    private var heliState = 0
    private var frameDisplayTime = 0f
    private val FRAME_CHANGE_INTERVAL = 0.1f

    private var clickCount = 0

    /**
     * Method to update the animationframe if applicable
     */
    override fun updateAnimationFrame(dt: Float) {
        frameDisplayTime += dt
        if (frameDisplayTime >= FRAME_CHANGE_INTERVAL) {
            frameDisplayTime = 0f
            heliState = (heliState + 1) % 4
            sprite = Sprite(pictures[heliState])
            if (!facingLeft) sprite.flip(true, false)
        }

    }

    override fun update(dt: Float) {
        super.update(dt)
    }

    /**
     * Method to check if the helicopter is in a loosing position
     */
    fun checkLose(): Boolean {
        return position.y <= sprite.height / 2f
    }

    fun jump() {
        clickCount++
        velocity.y = 3f
        if (clickCount > difficultyInterval) {
            clickCount = 0
            acc.add(0f, -3f)
        }
    }

    override fun handleRoofCollision() {
        val halfHeight = sprite.height / 2f
        if (position.y > Gdx.graphics.height - halfHeight) {
            position.y = Gdx.graphics.height - halfHeight

        }
    }

    override fun handleFloorCollision() {
        val halfHeight = sprite.height / 2f
        if (position.y < halfHeight) {
            position.y = halfHeight
        }
    }





}