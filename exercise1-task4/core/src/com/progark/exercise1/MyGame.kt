package com.progark.exercise1

import states.GameStateManager
import states.MenuState
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.ScreenUtils

class MyGame : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var gsm: GameStateManager

    override fun create() {
        batch = SpriteBatch()
        gsm = GameStateManager()
        gsm.push(MenuState(gsm, null))
    }

    override fun render() {
        ScreenUtils.clear(1f, 1f, 1f, 1f)
        gsm.update(Gdx.graphics.deltaTime)
        gsm.render(batch)

    }

    override fun dispose() {
        batch.dispose()
    }


}