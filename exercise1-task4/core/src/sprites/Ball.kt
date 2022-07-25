package sprites

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2

class Ball(private val initX: Float, private val initY: Float, radius: Int, startSpeed: Vector2) : GameSprite(initX, initY, startSpeed)
{

    private var lastCollided: GameSprite? = null
    init {
        val pixmap = Pixmap(2*radius, 2*radius, Pixmap.Format.RGBA8888)
        pixmap.setColor(Color(0f, 0f, 0f, 1f))
        pixmap.fillCircle(pixmap.width/2, pixmap.height/2, radius-1)
        texture = Texture(pixmap)
        sprite = Sprite(texture)
    }

    override fun applyPositionChange(dt: Float) {
        super.applyPositionChange(dt)
        position.add(velocity.cpy().scl(dt))
    }

    override fun handleBoundCollision() {
        super.handleBoundCollision()
        val halfHeight = sprite.height / 2f
        val topBound = Gdx.graphics.height - halfHeight

        if (position.y >= topBound || position.y <= halfHeight) {
            velocity.scl(1f, -1f)
            lastCollided = null
        }
    }


    override fun handleSpriteCollision(sprite: GameSprite) {
        if (lastCollided === sprite) return
        velocity.scl(-1f, 1f)
        lastCollided = sprite
    }

    fun checkPlayerWin(): Boolean? {
        if (position.x <= sprite.width/2) return false
        if (position.x >= (Gdx.graphics.width - sprite.width/2)) return true
        return null
    }

    fun reset() {
        position.set(initX, initY)
        velocity.set(random(100f, 300f), random(100f, 300f))
    }

}