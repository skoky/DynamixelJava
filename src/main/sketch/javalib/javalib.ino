
#define DXL_BUS_SERIAL1 1
Dynamixel Dxl(DXL_BUS_SERIAL1);
byte servo;
byte address;

void setup() {
  // setup communication to servos, speed 1Mb
  Dxl.begin(3);
  
  SerialUSB.attachInterrupt(usbInterrupt);
  pinMode(BOARD_LED_PIN, OUTPUT);  //toggleLED_Pin_Out

}

//USB max packet data is maximum 64byte, so nCount can not exceeds 64 bytes
// X Y A B 
void usbInterrupt(byte* buffer, byte nCount) {
  SerialUSB.print("//Received data, length ");
  SerialUSB.println(nCount);
  
  if (nCount>5) {
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
      SerialUSB.write(byteData);
    break;
    case 3:  // write word
      SerialUSB.println(nCount);
      wordData = word(buffer[3]);
      Dxl.writeWord(servo, address, wordData);      
    break;
    case 4:  // read word
      wordData = Dxl.readWord(servo,address);
      SerialUSB.print("//Word extracted");
      SerialUSB.write(wordData);
    break;
   // default: 
      // WTF?
  }
}
    

void loop(){
  toggleLED();
  SerialUSB.println("Positions TBD");
  
  delay(100);
    


}
