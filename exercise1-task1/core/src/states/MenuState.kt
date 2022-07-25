package states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class MenuState(gsm: GameStateManager) : State(gsm) {
    private val background = Texture("heli1.png")
    private val playBtn = Texture("play.png")

    override fun handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(PlayState(gsm))
            dispose()
        }
    }

    override fun update(dt: Float) {
        handleInput()
    }

    override fun render(sb: SpriteBatch) {
        sb.begin()
        sb.draw(background, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        sb.draw(playBtn, Gdx.graphics.width/2f - playBtn.width / 2f, Gdx.graphics.height/2f - playBtn.height/2)
        sb.end()
    }

    override fun dispose() {
        background.dispose()
        playBtn.dispose()
    }
}