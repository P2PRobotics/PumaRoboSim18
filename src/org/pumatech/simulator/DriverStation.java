package org.pumatech.simulator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import org.firstinspires.ftc.robotcore.external.GUITelemetry;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Autonomous;
import org.firstinspires.ftc.teamcode.BasicTeleOp;
import org.pumatech.field.Field;
import org.pumatech.physics.Vec2;
import org.pumatech.robot.Robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

public class DriverStation {
	@SuppressWarnings("unused")
	private static final int WIDTH = 505, HEIGHT = WIDTH * 690 / 505;
	private static final double PIXEL = WIDTH / 505d;
	private static final Color[] RED = { new Color(16, 1, 2), new Color(45, 1, 4), new Color(63, 2, 6),
			new Color(73, 12, 16), new Color(83, 2, 7), new Color(98, 2, 8), new Color(121, 14, 21),
			new Color(166, 4, 14), new Color(192, 11, 13) };

	private boolean show; // boolean for whether DS is shown (toggles with tab)
	private OpMode opmode; // Currently running opmode
	private Robot robot; // Will be used to initialize opmode and reset robot on restart
	private Field field; // Will be used to reset the field on restart
	private Gamepad gamepad1, gamepad2;
	private List<Class<? extends OpMode>> teleopOpModes, autonomousOpModes;
	private boolean hasInit, isRun;
	private Telemetry telemetry;

	public DriverStation(Robot robot, Field field) {
		this.robot = robot;
		this.field = field;
		show = false; // DS starts hidden

		// Gamepads start out null (disconnected) and are connected with setGamepad
		// methods
		gamepad1 = new Gamepad(null);
		gamepad2 = new Gamepad(null);

		// vvv This is not working :(
		// Reflections reflections = new Reflections("org.firstinspires.ftc.teamcode");
		// Set<Class<? extends OpMode>> opmodes =
		// reflections.getSubTypesOf(OpMode.class);
		// for (Class<? extends OpMode> op : opmodes) {
		// Teleop teleopAnnotation = op.getAnnotation(Teleop.class);
		// Autonomous autonomousAnnotation = op.getAnnotation(Autonomous.class);
		// if (teleopAnnotation != null) {
		// teleopOpModes.add(op);
		// } else if (autonomousAnnotation != null) {
		// autonomousOpModes.add(op);
		// }
		// }

		// Initialize opmode and connect it to robot and gamepads (and telemetry later)
		//opmode = new Autonomous();
		opmode = new BasicTeleOp();
		opmode.setup(robot.getHardwareMap(), gamepad1, gamepad2);
		opmode.init();
		opmode.start();
		hasInit = true;//true;
		isRun = true;//true;
		telemetry = new GUITelemetry();
		
	}

	public void update(double dt) {
		if (isRun) {
			opmode.init_loop();
			opmode.loop();
		}
		gamepad1.update(dt);
		gamepad2.update(dt);
	}

	public void draw(Graphics2D g) {
		if (!show)
			return;
		// remember previous clip bounds because new clip will be set
		// (clips define the region of the screen which can be drawn on)
		Rectangle oldClip = g.getClipBounds();

		// Draws the DS GUI
		rect(g, 0, 0, 505, 2000, Color.BLACK);
		rect(g, 0, 112, 505, 223, RED[6]);
		// TODO convert the rest of the drawing to use rect and circle
		g.setColor(RED[7]);
		g.fillRect((int) (3 * PIXEL), (int) (339 * PIXEL), (int) (500 * PIXEL), (int) (67 * PIXEL));
		g.setColor(RED[4]);
		g.fillRect((int) (3 * PIXEL), (int) (409 * PIXEL), (int) (500 * PIXEL), (int) (282 * PIXEL));
		g.setClip((int) (3 * PIXEL), (int) (409 * PIXEL), (int) (500 * PIXEL), (int) (282 * PIXEL));
		g.setColor(RED[5]);
		g.fillOval((int) (33 * PIXEL), (int) (331 * PIXEL), (int) (438 * PIXEL), (int) (428 * PIXEL));
		g.setColor(RED[2]);
		g.fillOval((int) (112 * PIXEL), (int) (444 * PIXEL), (int) (211 * PIXEL), (int) (211 * PIXEL));
		g.setColor(Color.WHITE);
		g.fillOval((int) (124 * PIXEL), (int) (456 * PIXEL), (int) (188 * PIXEL), (int) (188 * PIXEL));
		g.setClip(oldClip);
		g.setColor(RED[2]);
		g.fillRect((int) (3 * PIXEL), (int) (115 * PIXEL), (int) (499 * PIXEL), (int) (141 * PIXEL));
		rect(g, 3, 262, 247, 71, RED[2]);
		rect(g, 256, 262, 247, 71, new Color(89, 127, 0));
		ellipse(g, 189, 123, 127, 126, RED[5]);
		ellipse(g, 356, 123, 127, 126, RED[5]);
		ellipse(g, 347, 480, 92, 92, RED[1]);
		ellipse(g, 392, 591, 29, 29, RED[0]);
		ellipse(g, 365, 591, 29, 29, RED[0]);
		rect(g, 376, 592, 30, 29, RED[0]);
		triangle(g, new Vec2(32, 353), new Vec2(84, 353), new Vec2(58, 391), Color.WHITE);
		triangle(g, new Vec2(423, 353), new Vec2(474, 353), new Vec2(449, 391), Color.WHITE);

		// Start, stop, and init button
		if (!hasInit && !isRun) {
			g.setColor(RED[7]);
			GraphicsLib.drawStringCentered(g, "INIT", (int) (215 * PIXEL), (int) (545 * PIXEL), 50);
		} else if (hasInit && !isRun) {
			triangle(g, new Vec2(200, 525), new Vec2(200, 575), new Vec2(250, 550), RED[7]);
		} else {
			rect(g, 199, 530, 40, 40, RED[7]);
		}
		
		telemetry.draw(g);
		// Draw line dividing DS from rest of simulation
		g.setColor(Color.WHITE);
		g.drawLine((int) (505 * PIXEL), -30, (int) (505 * PIXEL), 2000);
		// Translate the rest of the simulation over to the right, but only half the SD
		// width to keep simulation centered
		g.translate(252.5 * PIXEL, 0);
		// Set the clip of the rest of the simulation to avoid overwriting DS area
		g.setClip((int) (252.5 * PIXEL + 1), 0, 10000, 2000); // Should be big rectangle size
	}

	private static void rect(Graphics2D g, double x, double y, double width, double height, Color c) {
		g.setColor(c);
		g.fill(new Rectangle2D.Double(x * PIXEL, y * PIXEL, width * PIXEL, height * PIXEL));
	}

	private static void ellipse(Graphics2D g, double x, double y, double width, double height, Color c) {
		g.setColor(c);
		g.fill(new Ellipse2D.Double(x * PIXEL, y * PIXEL, width * PIXEL, height * PIXEL));
	}

	private static void triangle(Graphics g, Vec2 a, Vec2 b, Vec2 c, Color color) {
		g.setColor(color);
		int[] x = { (int) (a.x * PIXEL), (int) (b.x * PIXEL), (int) (c.x * PIXEL) };
		int[] y = { (int) (a.y * PIXEL), (int) (b.y * PIXEL), (int) (c.y * PIXEL) };
		g.fillPolygon(x, y, 3);
	}

	public void setGamepad1(Gamepad gamepad1) {
		if (this.gamepad1 != null && this.gamepad1.equals(gamepad1))
			return;
		System.out.println("Gamepad 1 connected");
		if (gamepad2 != null && gamepad1.equals(gamepad2))
			setGamepad2(new Gamepad(null));
		this.gamepad1 = gamepad1;
		opmode.setGamepad1(gamepad1);
	}

	public void setGamepad2(Gamepad gamepad2) {
		if (this.gamepad2 != null && this.gamepad2.equals(gamepad2))
			return;
		System.out.println("Gamepad 2 connected");
		if (gamepad1 != null && gamepad2.equals(gamepad1))
			setGamepad1(new Gamepad(null));
		this.gamepad2 = gamepad2;
		opmode.setGamepad2(gamepad2);
	}

	public void toggle() {
		show = !show;
	}

	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		System.out.println(x + "," + y);
		if (Math.pow(x - 218, 2) + Math.pow(y - 550, 2) <= 94 * 94) {
			if (!hasInit) {
				hasInit = true;
				opmode.init();
				telemetry.addData("init",true );
			} else if (!isRun) {
				isRun = true;
				opmode.start();
			} else {
				isRun = false;
				hasInit = false;
				opmode.stop();
			}
		}
	}
}
