#include <FB_Const.h>
#include <FB_Error.h>
#include <FB_Net.h>
#include <FB_Utils.h>
#include <Firebase.h>
#include <FirebaseESP32.h>
#include <FirebaseFS.h>
#include <MB_File.h>

//Here we are using ESP8266. If needed, we will switch to ESP32, considering
//memory requirements.

/* TODO
 * ========================================================================= 
 * ✓  basic LED blinking
 * ✓  attach MQ2 sensor
 * ✓  relay connection
 * ✓  attach LDR module
 * ✓  attach rain module
 * ✓  create device registry & connect to Google Cloud IoT Core
 * ✓  create basic Pub/Sub
 * ✓  send data test
 * □  migrate to esp32
 * □  create esp32 device registry & connect to Google Cloud IoT Core
 * □  create basic Pub/Sub (is it needed?)
 * □  send data test, esp32
 * □  more to come
 * □  more to come
 */

//right side
const int acControl1 = 23;    // using IO23 pin, for sending signal to relay input port 1
const int acControl2 = 22;    // using IO22 pin, for sending signal to relay input port 2
const int acControl3 = 1;     // using IO1 pin, for sending signal to relay input port 3
const int acControl4 = 3;     // using IO3 pin, for sending signal to relay input port 4
const int ldr = 0;            // using IO0 pin, for receiving signal from LDR analog output
const int rain = 2;           // using IO2 pin, for receiving signal from rain sensor analog output

//left side
const int mq2 = 35;           // using IO34 pin, for receiving signal from MQ2 sensor analog output
const int buzz = 32;          // using IO35 pin, for sending signal to buzzer
const int doorLock = 33;       // using IO35 pin, for sending signal to door lock

//debug
const int debugLed = 19;

//sensor threshold
int sensorThreshold = 597; //consentration, in ppm

void setup() {
  pinMode(acControl1, OUTPUT);
  pinMode(acControl2, OUTPUT);
  pinMode(acControl3, OUTPUT);
  pinMode(acControl4, OUTPUT);
  pinMode(doorLock, OUTPUT);
  pinMode(buzz, OUTPUT);
  pinMode(mq2, INPUT);
  pinMode(ldr, INPUT);
  pinMode(rain, INPUT);
  pinMode(debugLed, OUTPUT);

  Serial.begin(115200);
}

/*void ledBlink(){
    //heartrate blinking for NodeMCU
  digitalWrite(led, HIGH);
  delay(100);
  digitalWrite(led, LOW);
  delay(100);
  delay(1666); //100 bpm
}
*/
void loop() {
  // Initiation
  int mq2Read = analogRead(mq2);   //for reading mq2 sensor
  int rainRead = analogRead(rain);
  int ldrRead = analogRead(ldr);

  //Relay block==========================================================
  digitalWrite(acControl1, LOW);   //for relay port 1
  digitalWrite(acControl2, LOW);   //for relay port 2
  digitalWrite(acControl3, LOW);   //for relay port 3
  digitalWrite(acControl4, LOW);   //for relay port 4

  //LDR sensor block=====================================================
  Serial.print("light: ");
  Serial.println(ldrRead);
  delay(200);

  if(ldrRead>500){
    Serial.println("it's a sunny day, perfect time for laundry!");
    delay(200);
  } else {
    Serial.println("you won't do your laundry, will you?");
    delay(200);
    }

  //Rain sensor block====================================================
  Serial.print("rain: ");
  Serial.println(rainRead);
  delay(1000);

  if(rainRead>3200){
    Serial.println("it's not raining, you can dry your clothes!");
    } else {
      Serial.println("it's raining! go get your clothes!");
    }
  
  //Gas sensor block=====================================================
  Serial.print("MQ2 value: ");
  Serial.println(mq2Read);
  delay(1000);
  // Checks if it has reached the threshold value (ANALOG)
  if (mq2Read > sensorThreshold)
  {
    Serial.println("Alert!\n");
    digitalWrite(buzz, HIGH);
    digitalWrite(debugLed, HIGH);
    //tone(buzzer, 1000, 200);
    } else {
      //Serial.println("It's safe here, no leakage!\n");
      //noTone(buzzer);
      }
      delay(1000);
  // Checks if it has reached the threshold value (DIGITAL)
  /*if(!mq2Read){
    Serial.print("It's safe here, no leakage!\n");
    delay(1000);
    } else {
      Serial.print("Alert!\n");
      digitalWrite(buzz, HIGH);
      delay(400);
    //tone(buzzer, 1000, 200);}
    }*/

  //Door lock block======================================================
  digitalWrite(doorLock, HIGH);
}
