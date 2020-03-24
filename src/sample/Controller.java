package sample;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Controller {

    public static void main(String[] args){
       File test = new File("C:\\Users\\magnu\\OneDrive\\Skrivebord\\piccollect\\src\\sample\\resources\\testBilde1.jpg");
       System.out.println(test.getName());
       String splitTest = test.getName();
       String[] testFormat = splitTest.split("[.]");
       System.out.println(Arrays.toString(testFormat));
       System.out.println(testFormat[testFormat.length - 1]);
    }
}
