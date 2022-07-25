package sprites

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import org.w3c.dom.Text
import kotlin.math.pow
import kotlin.random.Random

abstract class GameSprite(x: Float, y: Float, startSpeed: Vector2) {
    lateinit var texture: Texture
    lateinit var sprite: Sprite
    // Physics paramters
    var position = Vector2(x, y)
    var velocity = Vector2(startSpeed)

    fun update(dt: Float) {
        handleInput()
        applyPositionChange(dt)
        handleBoundCollision()
    }



    open fun applyPositionChange(dt: Float) {
    }

    open fun handleBoundCollision() {
        val halfWidth = sprite.width / 2f
        val rightBound = Gdx.graphics.width - halfWidth
        val halfHeight = sprite.height / 2f
        val topBound = Gdx.graphics.height - halfHeight
        if (position.x < halfWidth) {
            position.x = halfWidth
        } else if (position.x > rightBound) {
            position.x = rightBound
        }

        if (position.y > topBound) {
            position.y = topBound
        }
        else if (position.y < halfHeight) {
            position.y = halfHeight
        }
    }


    open fun handleSpriteCollision(sprite: GameSprite) {
    }

    fun overlaps(other: GameSprite): Boolean {
        val thisRect = this.sprite.boundingRectangle
        val otherRect = other.sprite.boundingRectangle
        return thisRect.overlaps(otherRect)

    }


    fun render(sb: SpriteBatch) {
        sprite.setCenter(position.x, position.y)
        sprite.draw(sb)
    }

    fun dispose() {
        texture.dispose()

    }

    open fun handleInput() {
    }
}