package states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import kotlin.math.abs

class PlayState(gsm: GameStateManager) : State(gsm) {

    private val heli = Texture("heli1.png")
    private val sprite = Sprite(heli)

    private var position = Vector2(Gdx.graphics.width / 2f, Gdx.graphics.height / 2f)
    private var heliSpeed = Vector2(0f, 0f)
    private var heliAcc = Vector2(-1f, 0f)
    private var maxHeliSpeed = 10f


    override fun handleInput() {
    }

    override fun render(sb: SpriteBatch) {
        sb.begin()
//        sb.draw(heli, , )

        sprite.setCenter(position.x, position.y)
        sprite.draw(sb)
        sb.end()

    }

    override fun dispose() {

    }

    override fun update(dt: Float) {
        val adjustment = if (abs(heliSpeed.x + heliAcc.x * dt) < maxHeliSpeed) {
            heliSpeed.add(heliAcc.x * dt, heliAcc.y * dt)
        } else {
            heliSpeed
        }
        position.add(adjustment)
        val halfWidth = sprite.width / 2f

        if (position.x < halfWidth || position.x > Gdx.graphics.width - halfWidth) {
            heliAcc.scl(-1f, 0f)
            heliSpeed.scl(-1f, 0f)
            sprite.flip(true, false)

        }


    }

}