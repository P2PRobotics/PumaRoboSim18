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
	private List<Body> crypto;
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
		jewels.add (new Polygon(new Vec2(3,108),2,13, Material.WOOD1));
		jewels.add (new Polygon(new Vec2(3,102),2,13, Material.WOOD2));
//		jewels.add (new Polygon(new Vec2(3,30),2,13,Material.WOOD));
//		jewels.add (new Polygon(new Vec2(3,36),2,13,Material.WOOD));
//		jewels.add (new Polygon(new Vec2(141,108),2,13,Material.WOOD));
//		jewels.add (new Polygon(new Vec2(141,102),2,13,Material.WOOD));
//		jewels.add (new Polygon(new Vec2(141,30),2,13,Material.WOOD));
//		jewels.add (new Polygon(new Vec2(141,36),2,13,Material.WOOD));
		glyphs = new LinkedList<Body>();
		for (int i = 0; i < 8; i++) {
			Vec2[] glyphVertices = {new Vec2(3, 3), new Vec2(-3, 3), new Vec2(-3, -3), new Vec2(3, -3) };
			glyphs.add(new Polygon(glyphVertices, Material.RUBBER));
			glyphs.get(glyphs.size()-1).moveBy(new Vec2(72, 32 + 8 * i));
		}
		for (int i = 0; i < 3; i++) {
			Vec2[] glyphVertices = {new Vec2(3, 3), new Vec2(-3, 3), new Vec2(-3, -3), new Vec2(3, -3) };
			glyphs.add(new Polygon(glyphVertices, Material.RUBBER));
			glyphs.get(glyphs.size()-1).moveBy(new Vec2(92, 52 + 8 * i));
		}
		for (int i = 0; i < 3; i++) {
			Vec2[] glyphVertices = {new Vec2(3, 3), new Vec2(-3, 3), new Vec2(-3, -3), new Vec2(3, -3) };
			glyphs.add(new Polygon(glyphVertices, Material.RUBBER));
			glyphs.get(glyphs.size()-1).moveBy(new Vec2(52, 52 + 8 * i));
		}
		for (int i = 0; i < 5; i++) {
			Vec2[] glyphVertices = {new Vec2(3, 3), new Vec2(-3, 3), new Vec2(-3, -3), new Vec2(3, -3) };
			glyphs.add(new Polygon(glyphVertices, Material.RUBBER));
			glyphs.get(glyphs.size()-1).moveBy(new Vec2(82, 44 + 8 * i));
		}
		for (int i = 0; i < 5; i++) {
			Vec2[] glyphVertices = {new Vec2(3, 3), new Vec2(-3, 3), new Vec2(-3, -3), new Vec2(3, -3) };
			glyphs.add(new Polygon(glyphVertices, Material.RUBBER));
			glyphs.get(glyphs.size()-1).moveBy(new Vec2(62, 44 + 8 * i));
		}
		
		crypto = new LinkedList<Body>();
//		for (int i = 0; i < 4; i++) {
//			Vec2[] cryptoBoxVert = {new Vec2(0.5,2), new Vec2(-0.5, 2), new Vec2(-0.5 -2), new Vec2(0.5, -2) };
//			Vec2[] cryptoBoxHoriz = {new Vec2(2,0.5), new Vec2(-2, 0.5), new Vec2(-2, -0.5), new Vec2(2, -0.5) };
//			crypto.add(new Polygon(cryptoBoxHoriz, Material.IMMOVEABLE));
//			crypto.get(crypto.size()-1).moveBy(new Vec2(142, 48 + 7 * i));
//		}
		for (int i = 0; i < 4; i++) {
			Vec2[] cryptoBoxVert = {new Vec2(0.5,2), new Vec2(-0.5, 2), new Vec2(-0.5 -2), new Vec2(0.5, -2) };
			Vec2[] cryptoBoxHoriz = {new Vec2(2,0.5), new Vec2(-2, 0.5), new Vec2(-2, -0.5), new Vec2(2, -0.5) };
			crypto.add(new Polygon(cryptoBoxVert, Material.IMMOVEABLE));
			crypto.get(crypto.size()-1).moveBy(new Vec2(24 + 7 * i,141.5));
		}
//		for (int i = 0; i < 4; i++) {
//			Vec2[] cryptoBoxVert = {new Vec2(0.5,2), new Vec2(-0.5, 2), new Vec2(-0.5 -2), new Vec2(0.5, -2) };
//			Vec2[] cryptoBoxHoriz = {new Vec2(2,0.5), new Vec2(-2, 0.5), new Vec2(-2, -0.5), new Vec2(2, -0.5) };
//			crypto.add(new Polygon(cryptoBoxHoriz, Material.IMMOVEABLE));
//			crypto.get(crypto.size()-1).moveBy(new Vec2(2, 48 + 7 * i));
//		}
//		for (int i = 0; i < 4; i++) {
//			Vec2[] cryptoBoxVert = {new Vec2(0.5,2), new Vec2(-0.5, 2), new Vec2(-0.5 -2), new Vec2(0.5, -2) };
//			Vec2[] cryptoBoxHoriz = {new Vec2(2,0.5), new Vec2(-2, 0.5), new Vec2(-2, -0.5), new Vec2(2, -0.5) };
//			crypto.add(new Polygon(cryptoBoxVert, Material.IMMOVEABLE));
//			crypto.get(crypto.size()-1).moveBy(new Vec2(96 + 7 * i,142));
//		}
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
		for (Body box : crypto) {
			box.draw(g);
		}
	}
	
	public void update(double dt) {
		for (Body glyph : glyphs) {
			glyph.applyForce(glyph.getVelocity().scaled(-2000*dt));
			glyph.applyTorque(glyph.getAngularVelocity() * -175 * dt);
		}
		for (Body jewel : jewels) {
			jewel.applyForce(jewel.getVelocity().scaled(-100*dt));
			jewel.applyTorque(jewel.getAngularVelocity() * -175 * dt);
		}
	}

	public List<Body> getBodies() {
		List<Body> bodies = new LinkedList<Body>();
		bodies.addAll(walls);
		bodies.addAll(glyphs);
		bodies.addAll(jewels);
		bodies.addAll(crypto);
		return bodies;
	}

}
