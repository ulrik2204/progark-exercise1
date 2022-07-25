package sprites

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import kotlin.math.pow

abstract class GameSprite(
    internalPaths: List<String>,
    x: Float,
    y: Float,
    startAcc: Vector2 = Vector2(10f, -9.8f),
    private val maxSpeed: Float = 10f,
    facingLeft: Boolean = true,
    startSpeed: Vector2 = Vector2(0f, 0f),
    public val mass: Float = 1f
) {
    // List of the textrues the helicopter sprite can take
    protected val pictures = internalPaths.map { Texture(it) }


    // Determining what state it is in
    protected var sprite = Sprite(pictures[0])

    // Physics paramters
    var position = Vector2(x, y)
    var velocity = Vector2(startSpeed)
    var acc = Vector2(startAcc.x, startAcc.y)

    protected var facingLeft = facingLeft

    open fun update(dt: Float) {
        updateAnimationFrame(dt)
        applyAcceleration(dt)
        handleWallCollision()
        handleFloorCollision()
        handleRoofCollision()
    }

    private fun applyAcceleration(dt: Float) {
        val adjustment = velocity.add(acc.x * dt, acc.y * dt)
        position.add(adjustment)
    }

    open fun handleWallCollision() {
        val halfWidth = sprite.width / 2f

        val collideWallLeft = position.x < halfWidth
        val collideWallRight = position.x > Gdx.graphics.width - halfWidth
        if (collideWallLeft || collideWallRight)  {
            acc.scl(-1f, 1f)
            velocity.scl(-1f, 1f)
            sprite.flip(true, false)
            facingLeft = !facingLeft
            position.x = if (collideWallLeft) halfWidth else Gdx.graphics.width - halfWidth
        }


    }

    open fun handleRoofCollision() {
        val halfHeight = sprite.height / 2f
        if (position.y > Gdx.graphics.height - halfHeight) {
            velocity.scl(1f, -1f)
            position.y = Gdx.graphics.height - halfHeight
        }
    }

    open fun handleFloorCollision() {
        val halfHeight = sprite.height / 2f
        if (position.y < halfHeight) {
            velocity.scl(1f, -1f)
            position.y = halfHeight
        }
    }

    companion object {

    fun handleSpriteCollision(sprite1: GameSprite, sprite2: GameSprite) {
        val m1 = sprite1.mass
        val m2 = sprite2.mass
        val v1 = sprite1.velocity.cpy()
        val x1 = sprite1.position.cpy()
        val v2 = sprite2.velocity.cpy()
        val x2 = sprite2.position.cpy()
        val v1n = v1.cpy().sub(
            x1.cpy().sub(x2).scl(
                ((2 * m2 / (m1 + m2)) *
                        ((v1.cpy().sub(v2))).dot(x1.cpy().sub(x2))) / (x1.cpy().sub(x2).len()
                    .pow(2))
            )
        )
        val v2n = v2.cpy().sub(
            x2.cpy().sub(x1).scl(
                ((2 * m1 / (m1 + m2)) *
                        ((v2.cpy().sub(v1))).dot(x2.cpy().sub(x1))) / (x2.cpy().sub(x1).len()
                    .pow(2))
            )
        )
        sprite1.velocity = v1n
        sprite2.velocity = v2n
    }
    }
    open fun overlaps(other: GameSprite): Boolean {
//        val halfHeight = sprite.height / 2f
//        val halfWidth = sprite.width / 2f
//        val oPos = other.position
//        val oHalfHeight = other.sprite.height / 2f
//        val oHalfWidth = other.sprite.width / 2f
//        val leftSide = position.x + halfWidth
//        // Check collision from the left side
//        if (leftSide > oPos.x - oHalfWidth && leftSide < oPos.x + oHalfWidth) {
//            handleSpriteCollision()
//        }

        val thisRect = this.sprite.boundingRectangle
        val otherRect = other.sprite.boundingRectangle
        return thisRect.overlaps(otherRect)

    }


    open fun render(sb: SpriteBatch) {
        sprite.setCenter(position.x, position.y)
        sprite.draw(sb)
    }

    open fun updateAnimationFrame(dt: Float) {

    }

    open fun dispose() {
        pictures.forEach() {
            it.dispose()
        }

    }
}