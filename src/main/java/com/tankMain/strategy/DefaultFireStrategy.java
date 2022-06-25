package com.tankMain.strategy;

import com.tankMain.Audio;
import com.tankMain.Bullet;
import com.tankMain.Group;
import com.tankMain.Tank;

public class DefaultFireStrategy implements FireStrategy {

	@Override
	public void fire(Tank t) {
		int bX = t.x + Tank.w/2 - Bullet.w/2;
		int bY = t.y + Tank.h/2 - Bullet.h/2;
		
		//Bug? new Bullet把自己加了一遍
//		GameModel.getInstance().add(
//				new RectDecorator(
//						new TailDecorator(
//						new Bullet(bX, bY, t.dir, t.group))));
		new Bullet(bX, bY, t.dir, t.group);
		
		if(t.group == Group.GOOD) new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
	}

}
