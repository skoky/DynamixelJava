#define DXL_BUS_SERIAL1 1
#define highByte(x) ( (x) >> (8) ) // keep upper 8 bits
#define lowByte(x) ( (x) & (0xff) ) // keep lower 8 bits

#define J_ID 1
#define PRESENT_POS 54


Dynamixel Dxl(DXL_BUS_SERIAL1);
byte servo;
byte address;
byte b;
int i;

void setup() {
  // setup communication to servos, speed 1Mb
  Dxl.begin(3);
  Dxl.jointMode(1);
// Dxl.wheelMode(1); 

  SerialUSB.attachInterrupt(usbInterrupt);
  pinMode(BOARD_LED_PIN, OUTPUT);  //toggleLED_Pin_Out

  Dxl.maxTorque(J_ID,1); // it has maxtorque for weak movement  
}

//USB max packet data is maximum 64byte, so nCount can not exceeds 64 bytes
// C S R A B
// C = command - 1,2,3 or 4
// S = Servo ID
// R = Register ID
// A and B is value A for byte and A+B for word
void usbInterrupt(byte* buffer, byte nCount) {

  if (nCount!=5) {
    SerialUSB.println(-2);
    return;
  }

  servo=buffer[1];
  address=buffer[2];
  switch (buffer[0]) {
  case 1:  // write byte
    Dxl.writeByte(servo, address, buffer[3]);
    SerialUSB.println(buffer[3]);
    break;
  case 2:  // read byte
    b = Dxl.readByte(servo,address);
    SerialUSB.println(b);
    break;
  case 3:  // write word
    i = buffer[3];  // FIXME
    Dxl.writeWord(servo, address, i);      
    SerialUSB.println(i);
    break;
  case 4:  // read word
    i = Dxl.readWord(servo,address);
    int pos;
    pos = Dxl.readWord(J_ID, PRESENT_POS);
    SerialUSB.println(pos);
    break;
  default: 
    buffer[0]=11;
    SerialUSB.println(-1);
  } 
}

void loop(){
  toggleLED();
//  int pos;
//  pos = Dxl.readWord(J_ID, PRESENT_POS); // Read present position
//  SerialUSB.print("Present Position: ");
//  SerialUSB.println(pos);

  delay(1000);
//  Dxl.writeWord(1, 30, 0);
//  delay(1000);
//  Dxl.writeWord(1, 30, 99);
  
}


