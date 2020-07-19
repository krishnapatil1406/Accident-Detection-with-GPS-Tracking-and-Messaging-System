import RPi.GPIO as GPIO
import time
import datetime
from firebase import firebase

firebase = firebase.FirebaseApplication('firebase_link_here',None)

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)  

sensor = 18
btnpin = 15
redpin = 20   
greenpin = 16  
bluepin = 12 

GPIO.setup(redpin, GPIO.OUT)
GPIO.setup(greenpin, GPIO.OUT)
GPIO.setup(bluepin, GPIO.OUT)
GPIO.setup(btnpin, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
GPIO.setup(sensor, GPIO.IN, pull_up_down = GPIO.PUD_UP)
  
def fun():
	GPIO.output(redpin, GPIO.HIGH)
	print("RED LED (HIGH)...")
	
	last_sec = datetime.datetime.now()
	while(True):
		curr_sec = datetime.datetime.now()
		
		if (GPIO.input(btnpin)==GPIO.HIGH): 
			print("Accident denied by Rider, Button Pressed within 5 sec.")
			GPIO.output(redpin, GPIO.LOW)
			print("RED LED (LOW)...")
			break
		
		elif ( (curr_sec - last_sec).seconds == 5):
			GPIO.output(redpin, GPIO.LOW)
			print("RED LED (LOW)...")
			firebase.put('','accident_flag','true')
			print("Firebase Record Updated (Knock Sensor)")
			break
	print("fun call successfully.")


#Function executed on signal detection
def active(null):
	print("Knock Sensor Detected Activity.")
	fun()
	start()

#On detecting signal (falling edge), active function will be activated.
GPIO.add_event_detect(sensor, GPIO.FALLING, callback=active, bouncetime=100) 
  
# main program loop
def start():
	try:
		print("Starting Knock Sensor...")
		GPIO.output(redpin, GPIO.LOW)
		print("RED LED (LOW)...")
		while True:
			time.sleep(1)

	except KeyboardInterrupt:
		GPIO.cleanup()

start()
