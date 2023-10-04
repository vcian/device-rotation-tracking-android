# device-rotation-tracking-android

# Introduction: 
This library track device 360-degree rotation and gives Angle value while Rotating device or moving in “Right” direction only.

# How to use:
-	Clone or download the code from GitHub and Run. 
-	Call RotationTrackingActivity from the “rotationTrackinglibrary” module.
-	Call function initView() , initSensor() , registerSensor() to setup UI views , Sensor.
- After register sensor implement SensorEventListener and its override method “onSensorChanged”.
- Move towards your “Right” direction only (Rotate device into Right direction), and get “zRotation” value in Degrees. (use this value as per your requirement) 
-	Call this “getProgressFromAngle” function to get “Progress” value (0-100 %) using z Angle value. Library currently using that progress value to show fill circle UI (You can customize UI as per your requirement).
	 
# Details:
Compile SDK Version: 33
Target SDK Version: 33
Min SDK: 23
Language: Java
Source Compatibility Java Version: VERSION_1_8
Target Compatibility Java Version: VERSION_1_8
Appcompat version: 1.6.1
JVM Target: 1.8
