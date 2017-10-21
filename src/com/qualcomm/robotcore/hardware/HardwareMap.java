package com.qualcomm.robotcore.hardware;

import java.util.Map;
import java.util.TreeMap;

import com.qualcomm.hardware.bosch.BNO055IMU;

public class HardwareMap {

	public DeviceMapping<DcMotor> dcMotor;
	public DeviceMapping<BNO055IMU> imu;
	public HardwareMap() {
		dcMotor = new DeviceMapping<DcMotor>();
		imu =new DeviceMapping<BNO055IMU>();
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
