package org.pumatech.physics;

import java.awt.Graphics2D;

import com.qualcomm.robotcore.hardware.DcMotor;

// Physical body used for simulating physics
public abstract class Body {
	// Constants representing values of mass
	public static final double UNDEFINED_MASS = -1;
	public static final double INFINITE_MASS = 0;
	
	protected Vec2 vel; // Velocity vector of object in 2D
	protected Vec2 force; // force being exerted on object
	
	protected double angularVel; // Rate (radians/s) at which body rotates
	protected double torque; // Force causing the object to rotate
	
	// Mass, moment of inertia as well as inverses stored for convenience
	protected double mass;
	protected double invMass; // invMass = 1 / mass
	protected double inertia;
	protected double invInertia; // invMass = 1 / mass
	
	// Material of body used to calculate friction, density, color
	protected Material mat;
	
	public Body(Material mat) {
		// Initialize a non-moving body
		this.mat = mat;
		vel = new Vec2(0, 0);
		force = new Vec2(0, 0);
		
		// Preinit to undefined & compute when needed
		mass = UNDEFINED_MASS;
		invMass = UNDEFINED_MASS;
		inertia = UNDEFINED_MASS;
		invInertia = UNDEFINED_MASS;
	}

	// Methods to be implement by Circle, Polygon
	public abstract void draw(Graphics2D g);
	public abstract void moveBy(Vec2 v);
	public void rotateBy(double angle) {}
	public abstract double area();
	public abstract Vec2 centerPoint();
	public abstract double direction();
	public abstract boolean containsPoint(Vec2 p);
	public abstract boolean isColliding(Body b);
	
	public void update(double dt) {
		// Approximates continues movement by slowly moving position and velocity
		moveBy(vel.scaled(dt));
		vel.add(force.scaled(getMassInv())); // F = ma => vel = fnInt(F/m,T,Ti,Tf)
		force.scale(0); // Set force to 0 so that only new forces applied every update affect body

		// Do above with scalar rotation
		rotateBy(angularVel * dt);
		angularVel += torque * dt;
		torque = 0;
	}
	
	// Applies a force to the body for the next update 
	public void applyForce(Vec2 f) {
		force.add(f);
	}
	
	// Applies a torque to the body for the next update
	public void applyTorque(double t) {
		torque += t;
	}
	
	// Applies an impulse (instant change in momentum) for collision resolution
	public void applyImpulse(Vec2 impulse, Vec2 contactPoint) {
		vel.add(impulse.scaled(getMassInv())); // velocity += impulse / mass
		// angular vel takes contact point into consideration
		angularVel += impulse.cross(centerPoint().subtracted(contactPoint)) * getInertiaInv();
	}
	
	// getMass && getMassInv compute mass if undefined
	public double getMass() {
		if (mass == Body.UNDEFINED_MASS) {
			mass = area() * mat.density();
		}
		return mass;
	}

	public double getMassInv() {
		if (mass == Body.UNDEFINED_MASS)
			getMass();
		if (invMass == Body.UNDEFINED_MASS) {
			if (mass == Body.INFINITE_MASS) {
				invMass = 0;
			} else {
				invMass = 1 / mass;
			}
		}
		return invMass;
	}
	
	// getInertia && getInertiaInv compute moment of inertia if undefined
	public double getInertia() {
		if (inertia == Body.UNDEFINED_MASS) {
			inertia = area() * mat.density() * 2000;
		}
		return mass;
	}

	public double getInertiaInv() {
		if (inertia == Body.UNDEFINED_MASS)
			getInertia();
		if (invInertia == Body.UNDEFINED_MASS) {
			if (mass == Body.INFINITE_MASS) {
				invInertia = 0;
			} else {
				invInertia = 1 / inertia;
			}
		}
		return invInertia;
	}
	
	// Creates an attachment at position v that remains attached to same point after movement and rotation
	public Attachment getAttachment(Vec2 v) {
		if (!containsPoint(v)) return null;
		return new Attachment(this, v.subtracted(centerPoint()).rotated(-direction()));
	}
	
	public Material getMaterial() {
		return mat;
	}
	
	public Vec2 getVelocity() {
		return vel;
	}
	
	public double getAngularVelocity() {
		return angularVel;
	}
	
	public Vec2 getForce() {
		return force;
	}
	
	public double getTorque() {
		return torque;
	}

}
