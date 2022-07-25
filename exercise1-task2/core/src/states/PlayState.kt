package states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import sprites.Helicopter

class PlayState(gsm: GameStateManager, private val highScore: Float) : State(gsm) {

//    private val heli = Texture("heli1.png")
//    private val sprite = Sprite(heli)
//
//    private var position = Vector2(Gdx.graphics.width / 2f, Gdx.graphics.height / 2f)
//    private var heliSpeed = Vector2(0f, 0f)
//    private var heliAcc = Vector2(-1f, 0f)
//    private var maxHeliSpeed = 10f

    private val heli = Helicopter(Gdx.graphics.width/2f, Gdx.graphics.height/2f)
    private val heliPosition = BitmapFont()
    private val timerText = BitmapFont()
    private val time = System.currentTimeMillis()
    private var timer = 0f

    override fun handleInput() {
        if (Gdx.input.justTouched()) {
            heli.jump()
        }
        if(heli.checkLose()) {
            val highestScore = if(highScore > timer) highScore else timer
            gsm.set(MenuState(gsm, highestScore))
        }
    }

    override fun render(sb: SpriteBatch) {
        sb.begin()
        heli.render(sb)
        heliPosition.draw(sb, heli.position.toString(), 10f, Gdx.graphics.height.toFloat()-10f)
        val currentTime = (System.currentTimeMillis() - time)/1000f
        val currentTimeText = currentTime.toString()
        timerText.draw(sb, "$currentTimeText s", 10f, Gdx.graphics.height.toFloat()-30f)
        sb.end()
        timer = currentTime

    }
    override fun update(dt: Float) {
        handleInput()
        heli.update(dt)
    }

    override fun dispose() {
        heli.dispose()
    }


}