package org.pumatech.simulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.pumatech.physics.Vec2;
import org.pumatech.states.SimulationState;
import org.pumatech.states.State;

/* Top level simulation class:
 *  Creates window (800x800 JFrame)
 *  Initializes a JPanel that fills entire JFrame and can be drawn on with Graphics object
 *  Implements loop to draw and update simulated objects
 *  Manages states and updates and draws current state 
 */
public class Simulator extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 30L;

	public static final Vec2 GRAVITY = new Vec2(0, .2);
	
	private boolean running = false; // Bool for when Tread is running
	private Stack<State> states; // Stack of running states (operates like a call stack)
	private double scale;
	
	public Simulator() {
		// Size of JFrame window
		Dimension size = new Dimension(800, 800);
			
		// Start in Main menu
		states = new Stack<State>();
		//states.push(new PhysicsState());
		states.push(new SimulationState());
		
		// Set size of this JPanel (Simulator extends JPanel)
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		
		// Setup JPanel with event listeners and such
		setBackground(Color.BLACK);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		// Create JFrame to house JPanel and size it to JPanel
		JFrame f = new JFrame("Puma Robotics Simulator");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.add(this);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		
		running = true;
		scale = Math.min(size.width, size.height) / 400;
	}
	
	public void paintComponent(Graphics g) {
		// Init G2D and fill screen with black
		double width = getSize().getWidth(), height = getSize().getHeight();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, (int) width, (int) height);
		
		// Rescale so that resolution is 400x400 and always displays in  square in JFrame
		if (width > height) {
			g2d.translate((width - height) / 2, 0);
		} else {
			g2d.translate(0, (height - width) / 2);			
		}
		scale = Math.min(width, height) / 400;
		g2d.scale(scale, scale);
		
		g2d.setFont(GraphicsLib.FONT);
		
		// Draw the current state
		if (!states.isEmpty()) states.peek().draw(g2d);
		
		// Fill in black on the screen
		g2d.setColor(new Color(230, 230, 235));
		if (width > height) {
			g2d.fillRect((int) -width, 0, (int) width, 400);
			g2d.fillRect(400, 0, (int) width, 400);
		} else {
			g2d.fillRect(0, (int) -height, 400, (int) height);
			g2d.fillRect(0, 400, 400, (int) height);
		}
	}
	
	// Update runs 60 times a second causing a tick
	// dt parameter is the amounts of time elapsed since the last frame
	public void update(double dt) {
		// Terminate simulation if there is no state
		if (states.isEmpty()) 
			System.exit(0);
		
		State g = states.peek();
		g.update(dt); // Update current state
		
		// Pop a state when it is finished, and push the next state if there is one
		if (g.isDone())
			System.out.println("Popping" + states.pop());
		if (g.nextState() != null) {
			System.out.println("Pushing: " + g.nextState());
			states.push(g.nextState());
		}
	}
	
	public void run() {
		// Loops while running calling update 60 times per second
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double dt = 1.0 / amountOfTicks;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update(dt);
				repaint(); // Calls the paintcomponent method
				delta--;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
			}
		}
	}
	
	private void correctMouseEvent(MouseEvent e) {
		int x = (int) (e.getX() / scale);
		int y = (int) (e.getY() / scale);
		e.translatePoint(x - e.getX(), y - e.getY());
	}

	// Mouse and Key event handlers below. (Called when mouse does actions and keys are pressed and released)
	public void keyPressed(KeyEvent e) { 
		if (!states.isEmpty())
			states.peek().keyPressed(e.getKeyCode()); 
	}

	public void keyReleased(KeyEvent e) { 
		if (!states.isEmpty())
			states.peek().keyReleased(e.getKeyCode()); 
	}
	
	public void keyTyped(KeyEvent e) { } // don't dispatch to states because it isn't useful
	
	public void mouseClicked(MouseEvent e) { 
		if (!states.isEmpty())
			correctMouseEvent(e);
			states.peek().mouseClicked(e); 
	}

	public void mouseEntered(MouseEvent e) { 
		if (!states.isEmpty())
			correctMouseEvent(e);
			states.peek().mouseEntered(e); 
	}

	public void mouseExited(MouseEvent e) { 
		if (!states.isEmpty())
			correctMouseEvent(e);
			states.peek().mouseExited(e); 
	}

	public void mousePressed(MouseEvent e) { 
		if (!states.isEmpty())
			correctMouseEvent(e);
			states.peek().mousePressed(e); 
	}
	
	public void mouseReleased(MouseEvent e) { 
		if (!states.isEmpty())
			correctMouseEvent(e);
			states.peek().mouseReleased(e); 
	}

	public void mouseDragged(MouseEvent e) { 
		if (!states.isEmpty())
			correctMouseEvent(e);
			states.peek().mouseDragged(e);
	}

	public void mouseMoved(MouseEvent e) { 
		if (!states.isEmpty())
			correctMouseEvent(e);
			states.peek().mouseMoved(e); 
	}
}
