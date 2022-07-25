package states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class MenuState(gsm: GameStateManager, private val playerWon: Boolean?) : State(gsm) {
    private val playBtn = Texture("play.png")
    private val titleText = BitmapFont()
    private val previousWinner = BitmapFont()

    override fun handleInput() {
        if (Gdx.input.justTouched()) {
            gsm.set(PlayState(gsm))
            dispose()
        }
    }

    override fun update(dt: Float) {
        handleInput()
    }

    override fun render(sb: SpriteBatch) {
        val winnerText =
            if (playerWon == true) "YOU WON!" else if (playerWon == false) "YOU LOST!" else "Press play!"
        titleText.setColor(Color(0f, 0f, 0f, 1f))
        previousWinner.setColor(Color(0f, 0f, 0f, 1f))
        val middleX = Gdx.graphics.width / 2f
        val middleY = Gdx.graphics.height / 2f
        sb.begin()

        sb.draw(playBtn, middleX - playBtn.width / 2f, middleY - playBtn.height / 2)
        titleText.draw(sb, "Pong", 20f, Gdx.graphics.height - 20f)
        previousWinner.draw(sb, winnerText, 20f, Gdx.graphics.height - 50f)
        sb.end()
    }

    override fun dispose() {
        playBtn.dispose()
    }
}