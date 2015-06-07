
Java library to control Robotis Dynamixel servos. Currently compatible with 

* XL-320 servo using OpenCM control board (A arduino sketch provided in srs/main/sketch dir)
* AX12A servos connected with USB2Dynamixel controller

The library consists Java code and Arduino like code for OpenCM board. 

Example usage (for RESTFull API see RestServer/README.md):

Getting first serial port

    serialPort = SerialPortFactory.getFirst()

Opening specific serial ports
    
    SerialPortFactory.get("/dev/ttyUSB0");
    
Opening controller on a specific port
    
    controller = new USB2Dynamixel(SerialPortFactory.get("/dev/ttyUSB0"));
    
Listing servos connecte to the controller
    
    List<Servo> servos = controller.listServos()
    
Connecting to a specific servo with id 5 via the controller

    servo = new ServoXL320(5,controller)
    
Getting info from servo like present position
    
    servo.getPresentPosition();
    
Setting value to a servo like goal position
    
    servo.setGoalPosition(200);

Setting new servo ID:
    
    servo.setId(4);
    
All registers are propagated to methods on the `Controller` or the `Servo` interfaces. 

Sync read and writes are also supported for several registers. 

    controller.setServoList(servos);
    Map<Integer,LedColor> leds = controller.servoList.getLedOn();
    leds.entrySet().stream().forEach(System.out::println);

    controller.servoList.setGoalPosition(400);
    

