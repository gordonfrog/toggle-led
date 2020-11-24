package be.webtechie.javaspringrestgpio.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

import be.webtechie.javaspringrestgpio.domain.Status;

@RestController
public class RaspberryPiController {
	
	public static GpioPinDigitalOutput pin;
	
	@GetMapping("/raspberry/light")
	public Status light(){
		Status status = new Status();		
		if(pin==null){
		  	GpioController gpio = GpioFactory.getInstance();	        
	        pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "MyLED", PinState.HIGH);
		}
		if(pin.isHigh()) {
			pin.low();
			status.setCode("OFF");
		} else if (pin.isLow()) {
			pin.high();
			status.setCode("ON");
		}		
		return status;
	}
}
