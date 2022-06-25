package com.tankMain.observer;


import com.tankMain.Tank;

public class TankFireHandler implements TankFireObserver {

	@Override
	public void actionOnFire(TankFireEvent e) {
		Tank t = e.getSource();
		t.fire();
	}

}
