import smbus 
import RPi.GPIO as GPIO
import time
import math
import datetime
from firebase import firebase

firebase = firebase.FirebaseApplication('firebase_link_here',None)

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False) 
 
#GPIO SETUP
channel = 21
btnpin = 15
redpin = 20   
greenpin = 16  
bluepin = 12 

GPIO.setup(redpin, GPIO.OUT)
GPIO.setup(greenpin, GPIO.OUT)
GPIO.setup(bluepin, GPIO.OUT)
GPIO.setup(btnpin, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)

def fun():
	GPIO.output(redpin, GPIO.HIGH)
	print("LED RED (LOW)...")
	
	last_sec = datetime.datetime.now()
	while(True):
		curr_sec = datetime.datetime.now()
		
		if (GPIO.input(btnpin)==GPIO.HIGH): 
			print("Accident denied by Rider, Button Pressed within 5 sec.")
			GPIO.output(redpin, GPIO.LOW)
			print("LED RED (LOW)...")
			break
		
		elif ( (curr_sec - last_sec).seconds == 5):
			GPIO.output(redpin, GPIO.LOW)
			print("LED RED (LOW)...")
			firebase.put('','accident_flag','true')
			print("Firebase Record Updated (Acclerometer)")
			break
	print("fun call successfully.")

power_mgmt_1=0x6b
power_mgmt_2=0x6c

def read_byte(adr):
	return bus.read_byte_data(address, adr)

def read_word(adr):
	high=bus.read_byte_data(address,adr)
	low=bus.read_byte_data(address,adr+1)
	val=(high<<8)+low
	return val
	
def read_word_2c(adr):
	val=read_word(adr)
	if(val>=0x8000):
		return -((65535 - val)+1)
	else:
		return val

def dist(a,b):
	return math.sqrt((a*a)+(b*b))
		
def get_x(x,y,z):
	radians=math.atan2(y,dist(x,z))
	return math.degrees(radians)

def get_y(x,y,z):
	radians=math.atan2(x,dist(y,z))
	return math.degrees(radians)

bus=smbus.SMBus(1)
address=0x68


print("Acclerometer Running")
while True:
	
	gyro_x=read_word_2c(0x43)
	gyro_y=read_word_2c(0x45)
	gyro_z=read_word_2c(0x47)
	
	accel_xout=(read_word_2c(0x3b))/16384.0
	accel_yout=(read_word_2c(0x3d))/16384.0
	accel_zout=(read_word_2c(0x3f))/16384.0
	
	#print("X-Rotation : ",get_x(accel_xout,accel_yout,accel_zout),"\t\t Y-Rotation : ",get_y(accel_xout,accel_yout,accel_zout))
	time.sleep(1)
	x=get_x(accel_xout,accel_yout,accel_zout)
	y=get_y(accel_xout,accel_yout,accel_zout)
	if(x>=45 or x<=-45):
		print("Something wrong in X ...")
		fun()
	elif(y>=45 or y<=-45):
		print("Something wrong in Y ...")
		fun()
