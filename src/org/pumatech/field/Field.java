package org.pumatech.field;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.List;

import org.pumatech.physics.Body;
import org.pumatech.physics.Material;
import org.pumatech.physics.Polygon;
import org.pumatech.physics.Vec2;

public class Field {

	private List<Body> walls;
	private List<Body> glyphs;
	private List<Body> jewels;
	public Field() {
		walls = new LinkedList<Body>();
		Vec2[] verticeshoriz = { new Vec2(72, 10), new Vec2(-72, 10), new Vec2(-72, -10), new Vec2(72, -10) };
		Vec2[] verticesvert = { new Vec2(10, 92), new Vec2(-10, 92), new Vec2(-10, -92), new Vec2(10, -92) };
		Body wall1 = new Polygon(verticeshoriz, Material.IMMOVEABLE);
		wall1.moveBy(new Vec2(72, -10));
		Body wall2 = new Polygon(verticeshoriz, Material.IMMOVEABLE);
		wall2.moveBy(new Vec2(72, 154));
		Body wall3 = new Polygon(verticesvert, Material.IMMOVEABLE);
		wall3.moveBy(new Vec2(-10, 72));
		Body wall4 = new Polygon(verticesvert, Material.IMMOVEABLE);
		wall4.moveBy(new Vec2(154, 72));
		walls.add(wall1);
		walls.add(wall2);
		walls.add(wall3);
		walls.add(wall4);
		jewels=new LinkedList<Body>();
		jewels.add (new Polygon(new Vec2(3,108),2,13,Material.WOOD));
		jewels.add (new Polygon(new Vec2(3,102),2,13,Material.WOOD));
		jewels.add (new Polygon(new Vec2(3,30),2,13,Material.WOOD));
		jewels.add (new Polygon(new Vec2(3,36),2,13,Material.WOOD));
		jewels.add (new Polygon(new Vec2(141,108),2,13,Material.WOOD));
		jewels.add (new Polygon(new Vec2(141,102),2,13,Material.WOOD));
		jewels.add (new Polygon(new Vec2(141,30),2,13,Material.WOOD));
		jewels.add (new Polygon(new Vec2(141,36),2,13,Material.WOOD));
		glyphs = new LinkedList<Body>();
		for (int i = 0; i < 4; i++) {
			Vec2[] glyphVertices = {new Vec2(3, 3), new Vec2(-3, 3), new Vec2(-3, -3), new Vec2(3, -3) };
			glyphs.add(new Polygon(glyphVertices, Material.RUBBER));
			glyphs.get(i).moveBy(new Vec2(72, 36 + 8 * i));
//			glyphs.applyForce(glyphs.getVelocity().scaled(-10000*dt));
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.BLUE);
		g.draw(new Line2D.Double(73, 0, 73, 24));
		g.setColor(Color.RED);
		g.draw(new Line2D.Double(72, 0, 72, 24));
		g.setColor(Color.BLUE);
		g.draw(new Line2D.Double(73, 24, 96, 47));
		g.setColor(Color.RED);
		g.draw(new Line2D.Double(72, 24, 48, 47));
		g.setColor(Color.BLUE);
		g.draw(new Line2D.Double(96, 48, 96, 72));
		g.setColor(Color.RED);
		g.draw(new Line2D.Double(48, 48, 48, 72));
		g.setColor(Color.BLUE);
		g.draw(new Line2D.Double(73, 95, 96, 73));
		g.setColor(Color.RED);
		g.draw(new Line2D.Double(72, 95, 48, 73));
		g.setColor(Color.BLUE);
		g.draw(new Line2D.Double(73, 144, 73, 96));
		g.setColor(Color.RED);
		g.draw(new Line2D.Double(72, 144, 72, 96));
		for (Body glyph : glyphs) {
			glyph.draw(g);
		}
		for (Body jewel : jewels) {
			jewel.draw(g);
		}
		for (Body wall : walls) {
			wall.draw(g);
		}
	}

	public List<Body> getBodies() {
		List<Body> bodies = new LinkedList<Body>();
		bodies.addAll(walls);
		bodies.addAll(glyphs);
		bodies.addAll(jewels);
		return bodies;
	}

}
