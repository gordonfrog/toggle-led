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
			pin.low();
			pin.toggle();
			//pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "MyLED", PinState.LOW);
			status.setCode("OFF");
		} else if (pin.isLow()) {
			pin.high();
			pin.toggle();
			//pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "MyLED", PinState.HIGH);
			status.setCode("ON");
		}
		logger.info("returning status: "+status);
		
		return status;
	}
	
	@GetMapping("/raspberry/test")
	public void test() {
		logger.info("<--Pi4J--> GPIO Example ... started.");
        
        // create gpio controller
        GpioController gpio = GpioFactory.getInstance();
        
        // provision gpio pin #02 as an output pin and turn on
        if (pin==null) { logger.info("pin is null, setting to low"); pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "MyLED-2", PinState.LOW); }
        else {
        	logger.info("STATE: "+pin.getState());
        	if (pin.getState().isHigh()) {logger.info("pin is high, setting to low"); pin.low();}
        	else {logger.info("pin is low, setting to high"); pin.high();}
        }
        // continuous loop
//        while(true)
//        {
//            pin.setState(true);
//            pin.setState(false);
//        }
	}
}
