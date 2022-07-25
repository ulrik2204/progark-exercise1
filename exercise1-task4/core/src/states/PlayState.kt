package states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import sprites.Ball
import sprites.Bat
import kotlin.math.pow
import kotlin.random.Random

class PlayState(gsm: GameStateManager) : State(gsm) {

    private val winThreshold = 21
    private val playerScoreText = BitmapFont()
    private val otherScoreText = BitmapFont()

    private var playerScore = 0
    private var otherScore = 0

    private val batHeight = 100
    private val batWidth = 10

    private val ball =
        Ball(Gdx.graphics.width / 2f, Gdx.graphics.height / 2f, 10, Vector2(-200f, 200f))
    private val batPlayer = Bat(
        Gdx.graphics.width * 0.05f,
        Gdx.graphics.height / 2f,
        batWidth,
        batHeight,
        Vector2(0f, 10f),
        true
    )
    private val batOther = Bat(
        Gdx.graphics.width * 0.95f,
        Gdx.graphics.height / 2f,
        batWidth,
        batHeight,
        Vector2(0f, 10f),
        false
    )

    fun handleCollisions() {
        if (ball.overlaps(batPlayer))
            ball.handleSpriteCollision(batPlayer)
        if (ball.overlaps(batOther))
            ball.handleSpriteCollision(batOther)
    }


    override fun handleInput() {
        batPlayer.handleInput()
        batOther.handleInput()
    }

    private fun renderText(sb: SpriteBatch) {
        playerScoreText.setColor(Color(0f, 0f, 0f, 1f))
        otherScoreText.setColor(Color(0f, 0f, 0f, 1f))
        playerScoreText.draw(sb, "$playerScore", Gdx.graphics.width/2f - 50f, Gdx.graphics.height - 20f)
        otherScoreText.draw(sb, "$otherScore", Gdx.graphics.width/2f + 50f, Gdx.graphics.height - 20f)

    }

    override fun render(sb: SpriteBatch) {
        sb.begin()
        ball.render(sb)
        batPlayer.render(sb)
        batOther.render(sb)
        renderText(sb)
        sb.end()

    }

    override fun update(dt: Float) {
        handleCollisions()
        handleInput()
        ball.update(dt)
        batPlayer.update(dt)
        batOther.update(dt)
        checkWin()
    }

    override fun dispose() {
        ball.dispose()
        batPlayer.dispose()
        batOther.dispose()
    }

    fun checkWin() {
        val playerWin = ball.checkPlayerWin() ?: return
        if (playerWin) {
            playerScore++
            ball.reset()
        }
        else if (!playerWin) {
            otherScore++
            ball.reset()
        }

        if (playerScore >= winThreshold) return finish(true)
        else if (otherScore >= winThreshold) return finish(false)

    }

    fun finish(playerWon: Boolean) {
        gsm.set(MenuState(gsm, playerWon))
    }


}