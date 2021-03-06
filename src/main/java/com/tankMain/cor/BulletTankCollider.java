package com.tankMain.cor;

import com.tankMain.Bullet;
import com.tankMain.Explode;
import com.tankMain.GameObject;
import com.tankMain.Tank;

public class BulletTankCollider implements Collider {

	@Override
	public boolean collide(GameObject o1, GameObject o2) {
		if(o1 instanceof Bullet && o2 instanceof Tank) {
			Bullet b = (Bullet)o1;
			Tank t = (Tank)o2;
			//TODO copy code from method collideWith
			if(b.group == t.getGroup()) return true;
			
			if(b.rect.intersects(t.rect)) {
				t.die();
				b.die();
				int eX = t.getX() + Tank.w/2 - Explode.WIDTH/2;
				int eY = t.getY() + Tank.h/2 - Explode.HEIGHT/2;
				new Explode(eX, eY);
				return false;
			}
			
		} else if (o1 instanceof Tank && o2 instanceof Bullet) {
			return collide(o2, o1);
		} 
		
		return true;
		
	}

}
