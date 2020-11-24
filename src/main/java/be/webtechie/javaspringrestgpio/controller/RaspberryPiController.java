package be.webtechie.javaspringrestgpio.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static GpioPinDigitalOutput pin;
	
	@GetMapping("/raspberry/light")
	public Status light(){
		logger.info("/raspberry/light");
		GpioController gpio = GpioFactory.getInstance();
		Status status = new Status();	
		if(pin==null){	        
	        pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "MyLED", PinState.HIGH);
	        logger.info("pin: "+pin);
		}
		else logger.info("pin: "+pin);
		
		logger.info("is pin high? "+pin.isHigh());
		logger.info("is pin low? "+pin.isLow());
		if(pin.isHigh()) {
			pin.toggle();
			pin.low();
			pin.setState(PinState.LOW);
			//pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "MyLED", PinState.LOW);
			status.setCode("OFF");
		} else if (pin.isLow()) {
			pin.toggle();
			pin.high();
			pin.setState(PinState.HIGH);
			//pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "MyLED", PinState.HIGH);
			status.setCode("ON");
		}
		logger.info("returning status: "+status);
		
		return status;
	}
}
