package org.firstinspires.ftc.robotcore.external;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Map;
import java.util.TreeMap;

public class GUITelemetry implements Telemetry {
	private Map<String, Object> mappy;

	public GUITelemetry() {
		mappy = new TreeMap<String, Object>();
	}
	@Override
	public Item addData(String caption, Object value) {
		// TODO Auto-generated method stub
		mappy.put(caption,value);
		return null;
	}

	@Override
	public void clearAll() {
		// TODO Auto-generated method stub
	mappy.clear();
	}

	@Override
	public boolean update() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void draw(Graphics2D d2) {
		d2.setColor(Color.YELLOW);
		for(Map.Entry<String,Object> entry:mappy.entrySet()) {
			d2.drawString(entry.getKey()+": "+entry.getValue(),10,15);
		}
	}
}
