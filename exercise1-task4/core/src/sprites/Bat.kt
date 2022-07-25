package sprites

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2

class Bat(
    x: Float,
    y: Float,
    width: Int,
    height: Int,
    private val speed: Vector2,
    public val player: Boolean
) : GameSprite(x, y, speed) {

    private var playerTouchAreaX = Gdx.graphics.width / 2
    private var directionTouchAreaY = Gdx.graphics.height / 2


    init {
        val pixmap = Pixmap(width, height, Pixmap.Format.RGBA8888)
        pixmap.setColor(Color(0f, 0f, 0f, 1f))
        pixmap.fillRectangle(0, 0, width, height)
        texture = Texture(pixmap)
        sprite = Sprite(texture)
    }

    fun move(y: Int) {
        if (y < directionTouchAreaY) {
            position.add(0f, speed.y)
        } else {
            position.add(0f, -speed.y)
        }

    }

    override fun handleInput() {
        if (Gdx.input.justTouched()) {
            val x = Gdx.input.x
            val y = Gdx.input.y
            if (x < playerTouchAreaX && player) {
                move(y)
            } else if (x > playerTouchAreaX && !player) {
                move(y)
            }

        }

    }

}