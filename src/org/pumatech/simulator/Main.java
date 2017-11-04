package org.pumatech.simulator;

import org.pumatech.physics.Vec2;

// Entry class, contains only main method
public class Main {

	public static void main(String[] args) {
		// Start simulator in a new Thread
		 new Thread(new Simulator()).start();

		//<25.0, 16.0> <144.0, 0.0> <0.0, 0.0> 1.5707963267948966
//		System.out.println(getVision(new Vec2(25, 16), Math.PI / 2, new Vec2(0, 0), new Vec2(144, 0)));
	}

	static Vec2 getVision(Vec2 pos, double direction, Vec2 a, Vec2 b) {
		a.y = -a.y;
		b.y = -b.y;
		pos.y = -pos.y;
		
		if (a.x > b.x) {
			Vec2 moo = b;
			b = a;
			a = moo;
		}

		double m1 = Math.tan(direction);
		double m2 = (b.y - a.y) / (b.x - a.x);
		double x, y;
		
		if (Math.abs(m1 - m2) < 0.01) {
			return null;
		}

		if (m1 > 100 || m1 < -100) {
			x = pos.x;
			y = m2 * (x - a.x) + a.y;

		} else if (a.x == b.x) {
			x = a.x;
			y = m1 * (x - pos.x) + pos.y;
		} else {
			x = (m1 * pos.x - m2 * a.x + a.y - pos.y) / (m1 - m2);
			y = m1 * (x - pos.x) + pos.y;
		}
		
		if (a.x <= x && x <= b.x && (a.y <= y && y <= b.y || b.y <= y && y <= a.y)) {
			return new Vec2(x - pos.x, -y + pos.y);
		}
		return null;
	}

}
