package org.example.mqtt_spring_dht11.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.tools.javac.Main;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.example.mqtt_spring_dht11.controller.MainController;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

//빈으로 사용해야되기 때문에 서비스로 역할하게하자.
//아마 싱글턴으로 동작해서 상관은 없을 것 같긴 하다?
@Service
@Slf4j
public class Mq_publisher{

    private MqttClient mqttClient;
    private final String broker="tcp://172.30.1.53:1883"; //같은 컴퓨터를 브로커로 사용하기 떄문에 사설ip주소 입력
    private final String clientId="webIot";

    public Mq_publisher() throws MqttException {
            log.info("[Mq_publisher Init]");
            //client와 callback을 set
            mqttClient = new MqttClient(broker, clientId);
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttClient.setCallback(new myPahoMqttClient());
            mqttClient.subscribe("webIot");
            log.info("[Setting Finisih] topic:webIot / clientId:"+mqttClient.getClientId()+" / serverUri:"+mqttClient.getServerURI());
    }

    private class myPahoMqttClient implements MqttCallback {

        @Override
        public void connectionLost(Throwable throwable) {

        }

        //messageArrived도 따지고보면 요청을 처리하는 컨트롤러니까 ResponseBody를 붙여도 되지 않을까?
        //@ResponseBody -> 안되는 이유 메모 참고
        @Override
        public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
            //들어온 Message(payload)를 String으로 변환
            String payload = new String(mqttMessage.getPayload(), StandardCharsets.UTF_8);
            //해당 메시지를 분석하여 데이터타입인 MainInfo()로 만들어준다.

            //이제 로그 파일에 저장해주면 된다.

            //여기선 로그파일에 기록해주면 된다. 기본적인 toString()사용시 출력되는 형태 그대로 저장하자.
            //아오 씨발 또 고민되는게, 그냥 json형식으로 로그파일에 저장해놓으려면, mosquitto_pub요청을 json으로만 통일시켜놓으면된다.
            //아마 터미널상에서 팅겨주는 로직이 필요한데 어떻게해야할까...

            /* 아래는 요청 개념 잡기 전, messageArrived()에서 json으로 처리하는 것은 올바르지 않다.
            String payload = new String(mqttMessage.getPayload(), StandardCharsets.UTF_8);
            //json으로 변환 후 http 요청 생성하기- @ResponseBody를 사용못하기에 수동변환
            //우선 온도와 습도를 입력하는 프론트창을 만들자.
            Mq_publisher.MainInfo mainInfo = new Gson().fromJson(payload, Mq_publisher.MainInfo.class);
            System.out.println(mainInfo.toString());
            */
        }


        @Override
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

        }

        public static boolean isValidJson(String json) {
            try {
                new Gson().fromJson(json, Object.class);
                return true;
            } catch (JsonSyntaxException e) {
                return false;
            }
        }
    }

    //데이터베이스는 필요 없다. 어차피 로그파일에서 저장하고(->messageArrived에서 처리), 읽어오기(->파일에서 처리) 두 기능밖에 안하기 때문이다. -> 모델 분리하지 않음.
    //1번요청(http form->json->mqtt)에서는, 마지막 mqtt를 처리하는 messageArrived에서 처리 / 2번요청(mqtt)도 마찬가지.
    //@Getter
    //@AllArgsConstructor
    //private class MainInfo {
    //    //외부클래스에서 접근할수도 있으므로 public
    //    public double humidity;
    //    public double temperature;
    //    public LocalDateTime requestTime;
    //}
}

