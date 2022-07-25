package states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class MenuState(gsm: GameStateManager, private val highScore: Float) : State(gsm) {
    private val background = Texture("heli1.png")
    private val playBtn = Texture("play.png")
    private val highScoreText = BitmapFont()

    override fun handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(PlayState(gsm, highScore))
            dispose()
        }
    }

    override fun update(dt: Float) {
        handleInput()
    }

    override fun render(sb: SpriteBatch) {
        val middleX =  Gdx.graphics.width/2f
        val middleY = Gdx.graphics.height/2f
        sb.begin()

        sb.draw(background, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        sb.draw(playBtn, middleX  - playBtn.width / 2f, middleY  - playBtn.height/2)
        val text = "High score: $highScore"
        highScoreText.draw(sb, text,  20f, Gdx.graphics.height - 20f)
        sb.end()
    }

    override fun dispose() {
        background.dispose()
        playBtn.dispose()
    }
}