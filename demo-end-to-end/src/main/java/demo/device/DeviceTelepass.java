package demo.device;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.entities.*;
import demo.service.LicencePlate;
import demo.service.LicencePlateGenerator;
import org.eclipse.paho.client.mqttv3.MqttException;
import scala.concurrent.java8.FuturesConvertersImpl;

import java.util.Date;

public class DeviceTelepass extends Thread{
    private MosquittoProducer telepass;
    private MosquittoProducer totem;


    public DeviceTelepass(int qos, String telepass, String totem) throws MqttException {
        this.telepass = new MosquittoProducer(telepass, qos);
        this.telepass.connect();
        this.totem = new MosquittoProducer(totem, qos);
        this.totem.connect();
    }
    @Override
    public void run()
    {
        for(int i = 0; i<20; i++)
        {
            Telepass telepassIterator = new TelepassIterator().next();
            ParentClass telepassMap = new ParentClass();
            telepassMap.setType("telepass");
            telepassMap.setPayload("userId",telepassIterator.getUserId());
            telepassMap.setPayload("licencePlate",telepassIterator.getLicencePlate());
            telepassMap.setPayload("timestamp", telepassIterator.getTimestamp());


            byte[] serializedTelepass = new ParentClassSerializationSchema().serializeMQTT(telepassMap, null);
            telepass.publish(serializedTelepass);

            Totem totemIterator = new TotemIterator().next(telepassIterator);
            ParentClass totemMap = new ParentClass();
            totemMap.setType("totem");
            totemMap.setPayload("telepass", totemIterator.getTelepass());
            totemMap.setPayload("totemId", totemIterator.getTotemId());
            totemMap.setPayload("accessTime", totemIterator.getAccessTime());

            ObjectMapper mapper = new ObjectMapper();

            byte[] serializedTotem = new ParentClassSerializationSchema().serializeMQTT(totemMap, null);
            totem.publish(serializedTotem);
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    static class TelepassIterator{

        private static int i = 1;
        Telepass next()
        {
            String userId = nextUserId(i);
            String telepassId = userId;
            LicencePlate newLicencePlate = new LicencePlate();
            i++;
            return new Telepass(userId, telepassId, new Date(), new LicencePlate().generateLicensePlate());
        }

        private String nextUserId(int i)
        {
            return "telepass-"+i;
        }
    }
    static class TotemIterator{
        private static int i = 1;
        Totem next(Telepass telepass1){
            Telepass telepass = telepass1;
            String totemId = nextTotemId();

            return  new Totem(telepass, totemId, telepass.getTimestamp());
        }

        private String nextTotemId(){
            return "totem-"+ 1;
        }

    }
}
