#include "I2Cdev.h" //utilizo una opción de las propuestas por la cátedra
#include "MPU6050.h"
#include "Wire.h"

// La dirección del MPU6050 puede ser 0x68 o 0x69, dependiendo 
// del estado de AD0. Si no se especifica, se utiliza 0x68.
MPU6050 sensor;

// Valores crudos del acelerometro y giroscopio en tres ejes
int ax, ay, az;
int gx, gy, gz;

long tiempo_prev;
float dt;
float ang_x, ang_y;
float ang_x_prev, ang_y_prev;

void setup() {
  Serial.begin(9600);    //Inicio puerto
  Wire.begin();           //Inicio I2C  
  sensor.initialize();    //Inicio el sensor

  if (sensor.testConnection()) Serial.println("Se inició de manera correcta el sensor");
  else Serial.println("El sensor no pudo ser iniciado");
}

void loop() {
  // Leo aceleraciones y velocidades angulares
  sensor.getAcceleration(&ax, &ay, &az);
  sensor.getRotation(&gx, &gy, &gz);
  
  dt = (millis()-tiempo_prev)/1000.0;
  tiempo_prev=millis();
  
  //Calcular ángulos (acelerómetro)
  float accel_ang_x=atan(ay/sqrt(pow(ax,2) + pow(az,2)))*(180.0/3.14);
  float accel_ang_y=atan(-ax/sqrt(pow(ay,2) + pow(az,2)))*(180.0/3.14);
  
  //Calculo ángulos (filtro complementarios) 
  ang_x = 0.98*(ang_x_prev+(gx/131)*dt) + 0.02*accel_ang_x;
  ang_y = 0.98*(ang_y_prev+(gy/131)*dt) + 0.02*accel_ang_y;
  
  
  ang_x_prev=ang_x;
  ang_y_prev=ang_y;

  //Mostrar los angulos separados por /

  Serial.print(ang_x);
  Serial.print("/"); 
  Serial.println(ang_y);

  delay(100);
}
 
