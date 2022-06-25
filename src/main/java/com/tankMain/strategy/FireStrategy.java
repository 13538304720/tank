package com.tankMain.strategy;

import com.tankMain.Tank;

import java.io.Serializable;

public interface FireStrategy extends Serializable {
	void fire(Tank t);
}
