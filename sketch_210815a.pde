import processing.net.*;
import processing.serial.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

Client c;
Serial puerto;

String data="";
float roll, pitch;

void setup() {
  size(800, 600, P3D);
  puerto = new Serial(this, "COM3", 9600); // empieza la comunicación con el puerto
  puerto.bufferUntil('\n');
  c = new Client (this, "localhost", 8080); //conecto el servidor
  thread("HttpRequest");
}

void draw() {
  translate(width/2, height/2, 0);
  background(233);
  textSize(22);
  fill(0, 0, 0);
  text("Roll: " + int(roll) + "     Pitch: " + int(pitch), -100, 265);

  // Rotate the object
  rotateX(radians(-pitch));
  rotateZ(radians(roll));
 
  // 3D 0bject
  textSize(30); 
  fill(0, 76, 153);
  box (386, 40, 200); //dibujo la box azul
  textSize(25);
  fill(255, 255, 255);
  text("MPU6050 - TdP II", -160, 10, 101);

  //delay(10);
  //println("ypr:\t" + angleX + "\t" + angleY); // print para tantear
}

// lee datos del puerto
void serialEvent (Serial puerto) {
  // lee hasta salto de linea y pone todo en el string data
  data = puerto.readStringUntil('\n');

  // agarro todo y lo spliteo con la /
  if (data != null) {
    data = trim(data);
    String items[] = split(data, '/');
    if (items.length > 1) {

      //me guardo lo que leí en roll y pitch
      roll = float(items[0]);
      pitch = float(items[1]);
    }
  }
}

void HttpRequest(){
  
  while(true){
      
  c.write("POST /ph4/processing HTTP/1.1\r\n"); // envio datos al servidor
  c.write("Host: localhost\r\n");
  c.write("Content-Type: application/x-www-form-urlencoded\r\n");
  String body="valueRotX="+roll+"&valueRotY="+pitch;
  c.write("Content-Length: "+body.length()+"\r\n");
  c.write("\r\n");
  c.write("valueRotX="+roll+"&valueRotY="+pitch);
  print(roll);
  print(pitch);
  delay(1000);
  }
  
}
