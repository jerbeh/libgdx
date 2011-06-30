/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * <h2>Functionality</h2>
 * A slider lets you select a value within a range (min, max), with stepping between each value the slider represents.
 * To listen for changes of the slider value one can register a {@link ValueChangedListener} with the slider.
 * 
 * <h2>Layout</h2>
 * A slider's (preferred) width and height are determined by the parameter past to its constructor as well as the maximum
 * height of the {@link NinePatch} and {@link TextureRegion} involved in the display of the slider. Use {@link #setPrefSize(int, int)} to programmatically change the size
 * to your liking. In case the width and height you set are to small you will see artifacts.
 * 
 * <h2>Style</h2>
 * TODO FIX THIS UP, TO TIRED... ALSO, MAKE SURE IMPL == DOCS!
 * 
 * @author mzechner
 *
 */
public class Slider extends Widget {
	final SliderStyle style;
	float min;
	float max;
	float steps;
	float value;	
	float sliderPos;
	ValueChangedListener listener = null;
	
	public Slider(String name, float prefWidth, float min, float max, float steps, SliderStyle style) {
		super(name, prefWidth, 0);
		this.style = style;
		if(min > max) throw new IllegalArgumentException("min must be > max");
		if(steps < 0) throw new IllegalArgumentException("unit must be > 0");
		this.min = min;
		this.max = max;
		this.steps = steps;
		this.value = min;		
		layout();
		this.width = prefWidth;
		this.height = prefHeight;
	}	

	@Override
	public void layout() {
		prefHeight = style.knob.getRegionHeight();
		invalidated = false;
	}

	@Override
	protected void draw(SpriteBatch batch, float parentAlpha) {
		final TextureRegion knob = style.knob;
		final NinePatch slider = style.slider;
		
		if(invalidated) layout();
		sliderPos = value / (max-min) * (width - knob.getRegionWidth());
		sliderPos = Math.max(0, sliderPos);
		sliderPos = Math.min(width - knob.getRegionWidth(), sliderPos);
		
		slider.draw(batch, x, y, width, height);
		batch.draw(knob, x + sliderPos, y);
	}

	@Override
	protected boolean touchDown(float x, float y, int pointer) {
		if(pointer != 0) return false;
		if(hit(x, y) != null) {			
			calculateSliderPosAndValue(x);
			parent.focus(this, pointer);
			return true;
		}
		return false;
	}

	@Override
	protected boolean touchUp(float x, float y, int pointer) {
		if(pointer != 0) return false;
		if(parent.focusedActor[0] == this) {
			calculateSliderPosAndValue(x);
			parent.focus(null, pointer);
			return true;
		}
		return false;		
	}

	@Override
	protected boolean touchDragged(float x, float y, int pointer) {
		if(pointer != 0) return false;
		if(parent.focusedActor[0] == this) {
			calculateSliderPosAndValue(x);
			return true;
		}
		return false;
	}

	private void calculateSliderPosAndValue(float x) {
		final TextureRegion knob = style.knob;
		
		sliderPos = x - knob.getRegionWidth() / 2;
		sliderPos = Math.max(0, sliderPos);
		sliderPos = Math.min(width - knob.getRegionWidth(), sliderPos);
		value = min + (max - min) * (sliderPos / (width - knob.getRegionWidth()));
		if(listener != null) listener.changed(this, getValue());		
	}
	
	@Override
	public Actor hit(float x, float y) {
		return x > 0 && x < width && y > 0 && y < height?this: null;
	}
	
	public static class SliderStyle {
		NinePatch slider;
		TextureRegion knob;
		
		public SliderStyle(NinePatch sliderPatch, TextureRegion knobRegion) {
			this.slider = sliderPatch;
			this.knob = knobRegion;
		}
	}
	
	public interface ValueChangedListener {
		public void changed(Slider slider, float value);
	}
	
	public Slider setValueChangedListener(ValueChangedListener listener) {
		this.listener = listener;
		return this;
	}
	
	public float getValue() {
		return (int)(value / steps) * steps;	
	}	
	
	public void setValue(float value) {
		if(value < min || value > max) throw new IllegalArgumentException("value must be >= min && <= max");		
		this.value = value;		
		if(listener != null) listener.changed(this, getValue());
	}
}