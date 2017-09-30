package com.qualcomm.robotcode.hardware;

import java.util.Map;
import java.util.TreeMap;

public class HardwareMap {

	public DeviceMapping<DcMotor> dcMotor;
	
	public HardwareMap() {
		dcMotor = new DeviceMapping<DcMotor>();
	}
	
	public class DeviceMapping<D extends HardwareDevice> {
		private Map<String, D> map;
		
		DeviceMapping() {
			map = new TreeMap<String, D>();
		}
		
		public void put(String deviceName, D device) {
			map.put(deviceName, device);
		}
		
		public boolean contains(String deviceName) {
			return map.containsKey(deviceName);
		}
		
		public D get(String deviceName) {
			return map.get(deviceName);
		}
	}
	
}
