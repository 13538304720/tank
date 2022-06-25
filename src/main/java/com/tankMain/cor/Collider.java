package com.tankMain.cor;

import com.tankMain.GameObject;

public interface Collider {
	boolean collide(GameObject o1, GameObject o2);
}
