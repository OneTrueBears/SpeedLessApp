import RPi.GPIO as GPIO
import math
import time
from bluetooth import *
import socket
import threading

#GPIO
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

greenLed = 4
yellowLed = 3
redLed = 2

button = 14

GPIO.setup(greenLed, GPIO.OUT)
GPIO.setup(yellowLed, GPIO.OUT)
GPIO.setup(redLed, GPIO.OUT)

GPIO.setup(button, GPIO.IN, GPIO.PUD_UP)



#BTServer
server_sock=BluetoothSocket( RFCOMM )
server_sock.bind(("",PORT_ANY))
server_sock.listen(1)
port = server_sock.getsockname()[1]
server_sock.settimeout(30.0)

uuid = "94f39d29-7d6d-437d-973b-fba39e49d4ee"

advertise_service(server_sock, "SampleServer",
                  service_id = uuid,
                  service_classes = [ uuid, SERIAL_PORT_CLASS ],
                  #profiles = [ SERIAL_PORT_PROFILE ], 
                  )

#Global variables
speed = 0
speedlimit = 0
connected = False
enable = True

def control(speed, speedlimit):
    margin = 0.0625
    global connected
    if not connected:
        GPIO.output(greenLed, 1)
        GPIO.output(redLed, 1)
        GPIO.output(yellowLed, 1)
    elif speed > speedlimit*(1+margin):
        #red light
        GPIO.output(redLed, 1)
        GPIO.output(yellowLed, 0)
        GPIO.output(greenLed, 0)
    elif speed <= speedlimit:
        #greeen light
        GPIO.output(greenLed, 1)
        GPIO.output(redLed, 0)
        GPIO.output(yellowLed, 0)
    elif math.ceil(speed) == math.ceil(speedlimit*(1+margin)):
        #greeen light
        GPIO.output(greenLed, 0)
        GPIO.output(redLed, 1)
        GPIO.output(yellowLed, 1)
    else:
        #yellow light
        GPIO.output(yellowLed, 1)
        GPIO.output(greenLed, 0)
        GPIO.output(redLed, 0)

def blink(number):
    for i in range(number):
        GPIO.output(greenLed, 0)
        GPIO.output(redLed, 0)
        GPIO.output(yellowLed, 0)
        time.sleep (0.5)
        GPIO.output(greenLed, 1)
        GPIO.output(redLed, 1)
        GPIO.output(yellowLed, 1)
        time.sleep (0.5)

def buttonControl():
    global enable
    global speed
    global speedlimit
    while True:
        if GPIO.input(button) == False:
            if (enable):
                enable = False
                GPIO.output(yellowLed, 0)
                GPIO.output(greenLed, 0)
                GPIO.output(redLed, 0)
            else:
                enable = True
                control(speed, speedlimit)
            time.sleep(0.3)
                
            
        if enable == False:
            GPIO.output(yellowLed, 0)
            GPIO.output(greenLed, 0)
            GPIO.output(redLed, 0)
        time.sleep(0.07)
        
def run():
    global connected
    global enable
    global speed
    global speedlimit
    while True:
        try:
            if not connected:
                print "Waiting for connection on RFCOMM channel %d" % port
                control(speed, speedlimit)
                client_sock, client_info = server_sock.accept()
                client_sock.settimeout(300.0)
                print "Accepted connection from ", client_info
                connected = True
                blink(2)
                
            #print "Waiting to receive data."
            data = client_sock.recv(1024)
            print "Data = %s" % data
            if len(data) == 0:
                break
            datas = data.split(':')
            if datas[0] == 'speed':
                speed = float(datas[1])
                client_sock.send("ACK speed")
                print "speed set to: %f" % speed
            elif datas[0] == "speedlimit":
                client_sock.send("ACK speed Limit")
                speedlimit = int(float(datas[1]))
                print "speed limit set to: ", speedlimit
            elif datas[0] == "enable":
                if datas[1] == "false":
                    enable = False;
                    client_sock.send("ACK enable False")
                    print "Disabling lights"
                elif datas[1] == "true":
                    enable = True;
                    client_sock.send("ACK enable True")
                    print "Enabling lights"
                else:
                    print "Input error: Received: %s" % datas[1]
            else:
                print "Input error: Received: %s" % data
            #client_sock.send(reply)
            #client_sock.send("heihei")
            #print "sending [%s]" % reply
            if enable:
                control(speed, speedlimit)

        except KeyboardInterrupt:
            print "disconnected"

            client_sock.close()
            server_sock.close()
            GPIO.cleanup()
            print "all done"

            break
        except socket.error, exc:
            print "Socket error"
            connected = False
        except socket.timeout:
            print "Timed out"
            connected = False
        except BluetoothError:
            print "Bluetooth Timeout"
            connected = False
        

t = threading.Thread(target=buttonControl)
t.daemon = True
t.start()
run()
        
