package org.pumatech.field;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import org.pumatech.physics.Body;
import org.pumatech.physics.Material;
import org.pumatech.physics.Polygon;
import org.pumatech.physics.Vec2;

public class Field {

	private List<Body> walls;
	
	public Field() {
		walls = new LinkedList<Body>();
		Vec2[] verticeshoriz = {new Vec2(200, 50), new Vec2(-200, 50), new Vec2(-200, -50), new Vec2(200, -50)};
		Vec2[] verticesvert = {new Vec2(50, 300), new Vec2(-50, 300), new Vec2(-50, -300), new Vec2(50, -300)};
		Body wall1 = new Polygon(verticeshoriz, Material.IMMOVEABLE);
		wall1.moveBy(new Vec2(200,-50));
		Body wall2 = new Polygon(verticeshoriz, Material.IMMOVEABLE);
		wall2.moveBy(new Vec2(200,450));
		Body wall3 = new Polygon(verticesvert, Material.IMMOVEABLE);
		wall3.moveBy(new Vec2(-50,200));
		Body wall4 = new Polygon(verticesvert, Material.IMMOVEABLE);
		wall4.moveBy(new Vec2(450,200));
		walls.add(wall1);
		walls.add(wall2);
		walls.add(wall3);
		walls.add(wall4);
	}
	
	public void draw(Graphics2D g) {
		for(Body wall : walls) {
			wall.draw(g);
		}
	}

	public List<Body> getBodies() {
		return walls;
	}
	
}
