package com.zxl.remotecontrol;

import java.io.Serializable;

public class Move implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float x;
    private float y;

    public Move(float x, float y) {
        this.x = x;
        this.y = y;
    }

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

}
