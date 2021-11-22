package demo.device;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;


public class MosquittoProducer {


    private String broker = "tcp://mosquitto:1883";
    private String topic;
    private int qos;
    private MqttClient client;


    public MosquittoProducer(String topic, int qos) throws MqttException {

        //this.num_msg = num_msg;
        this.topic = topic;
        this.qos = qos;
        this.client = new MqttClient(broker, MqttClient.generateClientId());

    }

    public void connect()
    {
        try{
          //  System.out.print(ANSI_RESET);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setMaxInflight(100000);
            options.setCleanSession(false);

            client.connect(options);
        }catch(MqttException e)
        {
            e.printStackTrace();
           // System.out.println(ANSI_BLUE+"Unable to connect to mosquitto!");
        }
    }
    public boolean publish(byte[] mess) {
        try {
           // System.out.print(ANSI_RESET);
            client.publish(topic,mess,qos, false);
            return true;
        }catch(MqttException e)
        {
            //System.out.println(ANSI_CYAN+"Unable to publish");
            e.printStackTrace();
            return false;
        }

    }

    public boolean disconnect()
    {
        try{
            //System.out.print(ANSI_RESET);
            client.disconnect();
            return true;
        }catch (MqttException e)
        {
            e.printStackTrace();
           // System.out.println(ANSI_RED +"Unable to disconnect");
            return false;
        }
    }



}
