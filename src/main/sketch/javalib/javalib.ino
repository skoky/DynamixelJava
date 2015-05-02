#define DXL_BUS_SERIAL1 1
#define highByte(x) ( (x) >> (8) ) // keep upper 8 bits
#define lowByte(x) ( (x) & (0xff) ) // keep lower 8 bits

Dynamixel Dxl(DXL_BUS_SERIAL1);
byte servo;
byte address;

void setup() {
  // setup communication to servos, speed 1Mb
  Dxl.begin(3);
  // Dxl.jointMode(1);
 Dxl.wheelMode(1); 

  SerialUSB.attachInterrupt(usbInterrupt);
  pinMode(BOARD_LED_PIN, OUTPUT);  //toggleLED_Pin_Out

}

//USB max packet data is maximum 64byte, so nCount can not exceeds 64 bytes
// C S R A B
// C = command - 1,2,3 or 4
// S = Servo ID
// R = Register ID
// A and B is value A for byte and A+B for word
void usbInterrupt(byte* buffer, byte nCount) {
  //  SerialUSB.print("//Received data, length ");
  //  SerialUSB.println(nCount);

  if (nCount>5 || nCount <3) {
    SerialUSB.println("#data too long!");
    return;
  }
  servo=buffer[1];
  address=buffer[2];
  byte byteData = buffer[3];
  word wordData = buffer[3]*255+buffer[4];
  switch (buffer[0]) {
  case 1:  // write byte
    byteData = buffer[3];
    Dxl.writeByte(servo, address, buffer[3]);
    break;
  case 2:  // read byte
    byteData = Dxl.readByte(servo,address);
    buffer[3]=byteData;
    SerialUSB.write(buffer,5);
    break;
  case 3:  // write word
    SerialUSB.println(nCount);
    wordData = word(buffer[3]);
    Dxl.writeWord(servo, address, wordData);      
    break;
  case 4:  // read word
    wordData = Dxl.readWord(servo,address);
    //      SerialUSB.print("//Word extracted");
    buffer[3]=highByte(wordData);
    buffer[4]=lowByte(wordData);
    SerialUSB.write(buffer,5);
    break;
  default: 
    SerialUSB.println("#Invalid command!");
  }
}

void loop(){
  toggleLED();
  // SerialUSB.println("Positions TBD");
  byte b[5];
  word p = Dxl.readWord(1,0x36);
  b[3]=highByte(p);
  b[4]=lowByte(p);
  SerialUSB.write(b,5);
  delay(1000);
}


