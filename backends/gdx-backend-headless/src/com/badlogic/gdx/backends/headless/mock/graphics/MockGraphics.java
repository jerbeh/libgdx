package com.badlogic.gdx.backends.headless.mock.graphics;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;

public class MockGraphics implements Graphics {
	float deltaTime = 0;
	long frameStart = 0;
	int frames = 0;
	int fps;
	long lastTime = System.nanoTime();

	@Override
	public boolean isGL11Available() {
		return false;
	}

	@Override
	public boolean isGL20Available() {
		return false;
	}

	@Override
	public GLCommon getGLCommon() {
		return null;
	}

	@Override
	public GL10 getGL10() {
		return null;
	}

	@Override
	public GL11 getGL11() {
		return null;
	}

	@Override
	public GL20 getGL20() {
		return null;
	}

	@Override
	public int getWidth() {
		return 0;
	}

	@Override
	public int getHeight() {
		return 0;
	}

	@Override
	public float getDeltaTime() {
		return deltaTime;
	}

	@Override
	public float getRawDeltaTime() {
		return 0;
	}

	@Override
	public int getFramesPerSecond() {
		return 0;
	}

	@Override
	public GraphicsType getType() {
		return GraphicsType.Mock;
	}

	@Override
	public float getPpiX() {
		return 0;
	}

	@Override
	public float getPpiY() {
		return 0;
	}

	@Override
	public float getPpcX() {
		return 0;
	}

	@Override
	public float getPpcY() {
		return 0;
	}

	@Override
	public float getDensity() {
		return 0;
	}

	@Override
	public boolean supportsDisplayModeChange() {
		return false;
	}

	@Override
	public DisplayMode[] getDisplayModes() {
		return new DisplayMode[0];
	}

	@Override
	public DisplayMode getDesktopDisplayMode() {
		return null;
	}

	@Override
	public boolean setDisplayMode(DisplayMode displayMode) {
		return false;
	}

	@Override
	public boolean setDisplayMode(int width, int height, boolean fullscreen) {
		return false;
	}

	@Override
	public void setTitle(String title) {

	}

	@Override
	public void setVSync(boolean vsync) {

	}

	@Override
	public BufferFormat getBufferFormat() {
		return null;
	}

	@Override
	public boolean supportsExtension(String extension) {
		return false;
	}

	@Override
	public void setContinuousRendering(boolean isContinuous) {

	}

	@Override
	public boolean isContinuousRendering() {
		return false;
	}

	@Override
	public void requestRendering() {

	}

	@Override
	public boolean isFullscreen() {
		return false;
	}

	public void updateTime () {
		long time = System.nanoTime();
		deltaTime = (time - lastTime) / 1000000000.0f;
		lastTime = time;

		if (time - frameStart >= 1000000000) {
			fps = frames;
			frames = 0;
			frameStart = time;
		}
		frames++;
	}
}
