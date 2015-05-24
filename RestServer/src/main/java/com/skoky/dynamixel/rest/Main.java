package com.skoky.dynamixel.rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.skoky.dynamixel.Controller;
import com.skoky.dynamixel.Servo;
import com.skoky.dynamixel.controller.OpenCM;
import com.skoky.dynamixel.controller.USB2Dynamixel;
import com.skoky.dynamixel.port.SerialPort;
import com.skoky.dynamixel.servo.ax12a.ServoAX12A;
import org.eclipse.jetty.io.EofException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;
import static spark.SparkBase.externalStaticFileLocation;
import static spark.SparkBase.port;

/**
 * Created by skoky on 24.5.15.
 */
public class Main {

    private static int PORT = 8080;

    public static void main(String[] args) {

        if (args.length < 2) printUsage();
        SerialPort port = SerialPort.getPort(args[0]);

        Controller c = null;
        if ("v1".compareToIgnoreCase(args[1]) == 0)
            c = new USB2Dynamixel(port);
        else if ("v2".compareToIgnoreCase(args[1]) == 0)
            c = new OpenCM(port);
        else printUsage();

        port(PORT);
//        externalStaticFileLocation("web");

        final Controller controller = c;

        get("/controller", (request, response) -> {
            response.status(200);
            JsonObject r = new JsonObject();
            r.addProperty("controller", controller.getClass().getName());
            List<Servo> servos = controller.listServos();
            List<Integer> servoList = new ArrayList<>();
            for (Servo s : servos) {
                servoList.add(s.getId());
            }
            r.add("servos", new Gson().toJsonTree(servoList));
            return r;
        });


        get("/servo/*", (request, response) -> {
            response.status(200);
            String servoId = request.splat()[0];
            Servo servo = null;
            for (Servo s : controller.listServos())
                if (servoId.equals(String.valueOf(s.getServoId()))) servo = s;
            if (servo == null) halt(404, "Servo not found");
            String param = request.queryParams("param");
            if (param == null) return new Gson().toJson(servo.getClass().getName());
            try {
                Object result = servo.getClass().getMethod(param).invoke(servo);
                return new Gson().toJson(result);
            } catch (NoSuchMethodException e) {
                halt(404, "Param not found");
                return null;
            }
        });


        put("/servo/*", (request, response) -> {
                    response.status(200);
                    String servoId = request.splat()[0];
                    Servo servo = null;
                    for (Servo s : controller.listServos())
                        if (servoId.equals(String.valueOf(s.getServoId()))) servo = s;
                    if (servo == null) halt(404, "Servo not found");
                    String param = request.queryParams("param");
                    String value = request.queryParams("value");
                    if (param == null) return new Gson().toJson(servo.getClass().getName());
                    try {
                        Object result = servo.getClass().getMethod(param,Integer.TYPE).invoke(servo, Integer.valueOf(value).intValue());
                        return new Gson().toJson(result);
                    } catch (NoSuchMethodException e) {
                        halt(404, "Param not found");
                        return null;
                    }
                }

        );
    }

    private static void printUsage() {
        System.out.println("Usage <portName> <version>. Example /dev/ttyUSB0 v1");
    }
}
