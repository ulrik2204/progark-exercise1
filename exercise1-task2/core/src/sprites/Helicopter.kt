package sprites

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import kotlin.math.abs

class Helicopter (x: Float, y: Float, startAccX: Float = -1f, private val maxSpeed: Float = 10f, private val difficultyInterval: Int = 10) {

    private val heli = Texture("heli1.png")
    val sprite = Sprite(heli)

    var position = Vector2(x, y)
    var velocity = Vector2(0f, 0f)
    var acc = Vector2(startAccX, -9.8f)
    private var clickCount = 0

    fun update(dt: Float) {
        val adjustment = if (abs(velocity.x + acc.x * dt) < maxSpeed) {
            velocity.add(acc.x * dt, acc.y * dt)
        } else {
            velocity.add(0f, acc.y * dt)
        }
        position.add(adjustment)
        val halfWidth = sprite.width / 2f
        val halfHeight = sprite.height / 2f

        if (position.x < halfWidth || position.x > Gdx.graphics.width - halfWidth) {
            acc.scl(-1f, 1f)
            velocity.scl(-1f, 1f)
            sprite.flip(true, false)
        }
        if (position.y > Gdx.graphics.height - halfHeight) {
            position.y = Gdx.graphics.height - halfHeight

        }
        if (position.y < halfHeight) {
            position.y = halfHeight

        }
    }

    fun checkLose(): Boolean {
        return position.y <= sprite.height / 2f
    }

    fun render(sb: SpriteBatch) {
        sprite.setCenter(position.x, position.y)
        sprite.draw(sb)
    }

    fun dispose() {
        heli.dispose()
    }

    fun jump() {
        clickCount++
        velocity.y = 3f
        if (clickCount > difficultyInterval) {
            clickCount = 0
            acc.add(0f, -3f)
        }
    }



}