package demo;



import demo.device.DeviceTelepass;
import org.eclipse.paho.client.mqttv3.MqttException;

//@SpringBootApplication
public class MosquittosimuApplication {
	public static final int EVENTS_PER_WINDOW = 5;
//	public static final long DELAY = WINDOW_SIZE.toMilliseconds() / EVENTS_PER_WINDOW;

	public static void main(String[] args) throws MqttException {
		//SpringApplication.run(MosquittosimuApplication.class, args);
		System.out.println("Starting Device");
		Thread device =new DeviceTelepass(1,"Queue", "Queue");
		device.start();


	}

}
