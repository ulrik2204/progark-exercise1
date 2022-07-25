package states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import sprites.Ball
import sprites.Bird
import sprites.GameSprite
import sprites.Helicopter
import kotlin.math.pow
import kotlin.random.Random

class PlayState(gsm: GameStateManager, private val highScore: Float) : State(gsm) {

    //    private val heli = Texture("heli1.png")
//    private val sprite = Sprite(heli)
//
//    private var position = Vector2(Gdx.graphics.width / 2f, Gdx.graphics.height / 2f)
//    private var heliSpeed = Vector2(0f, 0f)
//    private var heliAcc = Vector2(-1f, 0f)
//    private var maxHeliSpeed = 10f
    private val heli = Helicopter(Gdx.graphics.width / 2f, Gdx.graphics.height / 2f)
    private val sprites: List<GameSprite> = listOf(
        Bird(170f, 100f, startSpeed = Vector2(random(20f, false), random(20f))),
        Ball(
            Gdx.graphics.width - 100f,
            Gdx.graphics.height - 100f,
            startSpeed = Vector2(random(20f), random(20f))
        ),
        Ball(
            80f,
            Gdx.graphics.height - 100f,
            startAcc = Vector2(random(10f), -9.8f),
            startSpeed = Vector2(random(20f), random(20f))
        )

    )

    private val heliPosition = BitmapFont()
    private val timerText = BitmapFont()
    private val time = System.currentTimeMillis()
    private var timer = 0f


    private fun random(max: Float, allowNegative: Boolean = true): Float {
        val negativity = if (allowNegative) (-1f).pow(Random.nextInt(0, 2)) else 1f
        return Random.nextFloat() * max * negativity
    }

    fun handleCollisions() {
        val allSprites: List<GameSprite> = sprites + heli
        val appliedCollisions = mutableListOf<GameSprite>()
        for (sprite in allSprites) {
            if (appliedCollisions.contains(sprite)) continue
            for (sprite2 in allSprites) {
                if (sprite === sprite2 || appliedCollisions.contains(sprite2)) continue
                if (sprite.overlaps(sprite2)) {
                    GameSprite.handleSpriteCollision(sprite, sprite2)
                    appliedCollisions.add(sprite)
                    appliedCollisions.add(sprite2)
                }
            }
        }


    }


    override fun handleInput() {
        if (Gdx.input.justTouched()) {
            heli.jump()
        }
        if (heli.checkLose()) {
            val highestScore = if (highScore > timer) highScore else timer
            gsm.set(MenuState(gsm, highestScore))
        }
    }

    private fun renderText(sb: SpriteBatch) {
        heliPosition.draw(sb, heli.position.toString(), 10f, Gdx.graphics.height.toFloat() - 10f)
        val currentTime = (System.currentTimeMillis() - time) / 1000f
        val currentTimeText = currentTime.toString()
        timerText.draw(sb, "$currentTimeText s", 10f, Gdx.graphics.height.toFloat() - 30f)
        timer = currentTime
    }

    override fun render(sb: SpriteBatch) {
        sb.begin()
        heli.render(sb)
        sprites.forEach() {
            it.render(sb)
        }
        renderText(sb)
        sb.end()

    }

    override fun update(dt: Float) {
        handleCollisions()
        handleInput()
        heli.update(dt)
        sprites.forEach() {
            it.update(dt)
        }
    }

    override fun dispose() {
        heli.dispose()
    }


}