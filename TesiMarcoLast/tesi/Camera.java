package tesi;
import java.lang.reflect.Array;
import java.lang.Object;
import lejos.nxt.*;
import lejos.util.*;
import lejos.geom.*;
//#include "NXCDefs.h"
//application specific defines
public class Camera
{
	public static I2CPort I2C_PORT = SensorPort.S1;
	public static int register=0x40;
	public static int CHAR_WIDTH=6;
	public static int LINE_WIDTH=16;
	public static int SCREEN_X_ORIG=5;
	public static int SCREEN_Y_ORIG=13;
	public static int SCREEN_WIDTH=120;
	public static int SCREEN_HEIGHT=120;
	public static int SPEED=70;
	public static int PIC_ADDRESS=38;
	public static int START_BUF=128;
	public static int GET_CHAR=129;
	public static int BUF_SIZE=66;
	
	//CMUcam4 specific defines
	public static String CAM_GET_VERSION="GV";
	public static String CAM_TRACK_PARAMS="GT";
	//SW topSX X, topSX Y, bottomDX X, bottomDX Y
	public static String CAM_SET_TRACK_WINDO="SW 0 60 159 119";
	//GH channel(0=RED,1=GREEN,2=BLUE), bins (0-5)
	public static String CAM_GET_HISTOG="";
	public static String [] CHANNEL= new String[3];
	public static String RED="GH 0 2";
	public static String GREEN="GH 1 2";
	public static String BLUE="GH 2 2";
	public static String CAM_LED_ON="L0 1";
	public static String CAM_LED_OFF="L0 0";
	public static lejos.nxt.I2CSensor prova = new lejos.nxt.I2CSensor(I2C_PORT);
	public static lejos.nxt.LCD pulisci = new lejos.nxt.LCD();


	/**
	  * send one byte to and receive one byte from i2c
	  */
	static byte txrxI2C(byte data) {
	   byte[] recv=new byte[1];
	   byte[] send=new byte[2];
	   send[0] = (byte) PIC_ADDRESS;
	   send[1] = data;
	   int controllo=prova.sendData(register, send, BUF_SIZE);
	   if(controllo!=0)
	   {
		   Sound.beep();
		   System.out.println("errore: l'invio del byte al PIC /n non è andato a buon fine./n il robot si spengerà tra 2 secondi");
		   Delay.msDelay(2000);
		   NXT.shutDown();
	   }
	   prova.getData(register, recv, BUF_SIZE);
	   return recv[0];
	}
	
	/**
	  * send a command byte-per-byte to i2c
	  */
	static void sendCommandI2C(String command) {
	   int answer;
	   txrxI2C((byte) 13);
	   char[] a= command.toCharArray();
	   for (int i = 0; i <a.length; i++) {
	     answer = 0;
	     do {
	        answer = txrxI2C((byte)a[i]);
	     } while (answer != a[i]);
	   }
	   do {
	     answer = txrxI2C((byte) 13);
	   } while (answer != 13);
	}
	/**
	  * send command to start buffering on the PIC16F690
	  */
	static void startBufferI2C() 
	{
	   txrxI2C((byte) START_BUF);
	}
	/**
	  * send command to stop buffering on the PIC16F690
	  */
	void stopBufferI2C() {
	   txrxI2C((byte) GET_CHAR);
	}
	/**
	  * read out the buffer from the PIC16F690
	 * @param result2 
	  */
	static void getResultI2C(byte[]  result)
	{
	   for (int i = 0; i < BUF_SIZE; i++) 
	   {
	      result[BUF_SIZE - i] = txrxI2C((byte) GET_CHAR);
	   }
	}
	/**
	  * get a dummy result as expected from the PIC16F690
	  */
	void dummyGetResultI2C(byte[] result)
	{
	   for (int i = 0; i < BUF_SIZE; i++)
	   {
	      result[i] = 0;
	   }
	   String dummy = "T 10 20 255 4 55 6 77 128 T 10 20 30 40 abcd";
	   byte[] dummyy=dummy.getBytes();
	   for (int i = 0; i < dummy.length(); i++) 
	   {
	     result[i] = dummyy[i];
	   }
	   result[25] = 13;
	}
	/**
	  * extract the last complete answer from the given array. look for
	  * the last carriage return (ASCII-code 13) in the array and return
	  * the values preceding this carriage return until another carriage
	  * return is found or the begin of the array is reached.
	 * @param result2 
	  */
	static void getLastAnswer(byte[] result) {
	   int i;
	   int j;
	   byte [] answer_temp= new byte[BUF_SIZE];
	   answer_temp[0] = 0;
	   //find last carriage return
	   for (i = BUF_SIZE - 1; i > 0; i--) {
	     if (result[i] == 13) {
	        break;
	     }
	   }
	   //no carriage return found
	   if (result[i] != 13) 
	   {
	     for (j = 0; j < 8; j++) 
	     {
	        return;
	     }
	   }
	   //copy bytes until next carriage return or 0 to answer_temp
	   j = 0;
	   do {
	     answer_temp[j++] = result[--i];
	   } while (i > 0 && result[i - 1] != 13 && result[i - 1] != 0);
	   for (i = 0; i <= j - 1; i++) {
	     result[i] = answer_temp[j - 1 - i];
	   }
	   //copy bytes in reversed order to result
	   for (i = j; i < BUF_SIZE; i++) {
	     result[i] = 0;
	   }
	}
	/**
	  * reset the camera
	  */
	static void resetCamera() {
	   byte[] result= new byte[BUF_SIZE];
	   do {
	     sendCommandI2C(CAM_LED_ON);
	     startBufferI2C();
	     sendCommandI2C(CAM_LED_OFF);
	     getResultI2C(result);
	     getLastAnswer(result);
	   } while (result[0] != ':' || result[1] != 'A' || result[2] != 'C' || result[3] != 'K');
	}
	/**
	  * extract coordinates from a received result where a result is of the
	  * form .* c0 c1 c2 c3 c4 c5 c6 c7\r.*, that is, a sequence of
	  * integers separated by spaces, and terminated bya carriage return
	  * (ASCII-code 13)
	  */
	static void getCoordinates( byte [] result, byte [] coords) {
	   int i;
	   //find last carriage return
	   for (i = BUF_SIZE - 1; i > 0; i--) 
	   {
	     if (result[i] == 13) {
	        break;
	     }
	   }
	   //no carriage return found
	   if (result[i] != 13) 
	   {
	     for (int k = 0; k < 8; k++) 
	     {
	        coords[k] = 0;
	        return;
	     }
	   }
	   //process 8 integers (each integer has max. 3 characters)
	   for (int j = 7; j >= 0; j--)
	   {
	     byte[] temp=new byte[3];
	     int k;
	     int l;
	     String help_string = "";
	     do
	     {
	       i--;
	     } while (result[i] != ' ' && i > 0);
	     if (i == 0) {
	       for (k = 0; k < 8; k++) 
	       {
	          coords[k] = 0;
	          return;
	       }
	     }
	     l = i;
	     k = 0;
	     do
	     {
	       temp[k++] = result[++l];
	     } while (result[l + 1] != 13 && result[l + 1] != ' ');
	     coords[j] =(byte) Integer.parseInt(help_string);
	   }
	}
	/**
	  * main. reset the camera and drive towards a colored object.
	 * @param position 
	  */
	public static String[] start(String position)
	{
	   CAM_SET_TRACK_WINDO=position;
       CHANNEL[0]=RED;
       CHANNEL[1]=GREEN;
       CHANNEL[2]=BLUE;
	   int button_count;
	   int counter = 0;
	   byte[] result= new byte[BUF_SIZE];
	   String[] histogram= new String[3];
	   byte[] coords= new byte[8];
	   boolean tracking_enabled = false;
	   resetCamera();
       pulisci.clearDisplay();
       startBufferI2C();
       sendCommandI2C(CAM_GET_VERSION);
       sendCommandI2C(CAM_TRACK_PARAMS);
       Delay.msDelay(200);
       getResultI2C(result);
       tracking_enabled = false;
       if (!tracking_enabled)
       {
          startBufferI2C();
          sendCommandI2C(CAM_SET_TRACK_WINDO);
          for(int i=0;i<3;i++)
          {
        	  CAM_GET_HISTOG=CHANNEL[i];
        	  sendCommandI2C(CAM_GET_HISTOG);
        	  Delay.msDelay(200);
        	  getResultI2C(result);
        	   for(int k = 0; k < result.length; k++)
        	    {
        		   histogram[i] +=(char)result[k];
        	    }
        	  CAM_GET_HISTOG="";
          }
          Delay.msDelay(200);
      }
      pulisci.clearDisplay();
      return histogram;	  
	}
}


