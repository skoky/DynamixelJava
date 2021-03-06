#define DXL_BUS_SERIAL1 1

Dynamixel Dxl(DXL_BUS_SERIAL1);
byte servo;
byte address;
byte b;
byte command;
int i;

void setup() {
  // setup communication to servos, speed 1Mb
  Dxl.begin(3);
  Dxl.jointMode(1);
// Dxl.wheelMode(1);

  SerialUSB.attachInterrupt(usbInterrupt);
  pinMode(BOARD_LED_PIN, OUTPUT);  //toggleLED_Pin_Out
  command=0;
}

//USB max packet data is maximum 64byte, so nCount can not exceeds 64 bytes
// C S R A B
// C = command - 1,2,3 or 4
// S = Servo ID
// R = Register ID
// A and B is value A for byte and A+B for word
void usbInterrupt(byte* buffer, byte nCount) {
  toggleLED();
  if (nCount!=5) {
    SerialUSB.println(100); //error
    toggleLED();
    return;
  }

  servo=buffer[1];
  address=buffer[2];
  command=buffer[0];
  switch (command) {
  case 1:  // write byte
    Dxl.writeByte(servo, address, buffer[3]);
    SerialUSB.println(buffer[3]);
    toggleLED();
    break;
  case 2:  // read byte
    // direct read in the interrupt does not work
    // this is handled in the loop()    
    break;
  case 3:  // write word
    i = buffer[3]*255;  
    i = i + buffer[4];
    Dxl.writeWord(servo, address, i);      
    SerialUSB.println(i);
    toggleLED();
    break;
  case 4:  // read word
    // direct read in the interrupt does not work
    // this is handled in the loop()
    break;
  default: 
    SerialUSB.println(101); // error
    toggleLED();
  } 
}

void loop(){

  if (command==4) {
    i = Dxl.readWord(servo,address);
    SerialUSB.println(i);
    command=0;
    toggleLED();
  } else if (command==2) {
    b = Dxl.readByte(servo,address);
    SerialUSB.println(b);
    command=0;
    toggleLED();
  }

}


