package com.tankMain.observer;

import com.tankMain.Tank;

public class TankFireEvent {
	Tank tank;
	
	public Tank getSource() {
		return tank;
	}
	
	public TankFireEvent(Tank tank) {
		this.tank = tank;
	}

}
