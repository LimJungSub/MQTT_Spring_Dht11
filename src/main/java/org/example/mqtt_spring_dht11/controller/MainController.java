package org.example.mqtt_spring_dht11.controller;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
@Controller
public class MainController implements MqttCallback {

    private Boolean isFirstRootRequest = true;

    private MqttClient mqttClient = null;

    private final String startServerFileDir = "/Users/imjeongseob/WebWorkspace/MQTT_Spring_dht11/src/main/java/org/example/mqtt_spring_dht11/shellCommand/mosquitto_Start.sh";
    private final String subFileDir = "/Users/imjeongseob/WebWorkspace/MQTT_Spring_dht11/src/main/java/org/example/mqtt_spring_dht11/shellCommand/mosquitto_sub.sh";
    private final String pubFileDir = "/Users/imjeongseob/WebWorkspace/MQTT_Spring_dht11/src/main/java/org/example/mqtt_spring_dht11/shellCommand/mosquitto_pub.sh";


    @GetMapping("/") //**getmappingㅇㅣ니 요청먼저 보내봐야 아마 뷰로 리턴해주면 될듯?
    public String init() {
        if(isFirstRootRequest){
            //chmod로 쉘스크립트 파일들에 대한 권한을 먼저 줘야했다.
            //Open Server
            ProcessBuilder serverProcess = new ProcessBuilder();
            serverProcess.command(startServerFileDir);
            ProcessBuilder subProcess = new ProcessBuilder();
            subProcess.command(subFileDir);
            ProcessBuilder pubProcess = new ProcessBuilder();
            pubProcess.command(pubFileDir);
            try {

                Process serverStart = serverProcess.start();
                log.info(serverStart.info().toString());

                Process subStart = subProcess.start();
                log.info(subStart.info().toString());

                isFirstRootRequest = false;
                //우선 이렇게까지만 해보고, 터미널에서 pub를 통해 정상적으로 메시지가 도착하는지 확인하자
                //여기선 아마 subscriber 등록을 해야할것같은데, 이러면 별도 프로세스를 만들어야한다.
                //여기서 아래것들을 출력하는 것은 별로 올바르지 않은것 같고, messgaeArrived쪽에 처리해야하는것같기도.
                //BufferedReader logReader = new BufferedReader(new InputStreamReader(serverStart.getInputStream()));
                //String line;
                //if((line = logReader.readLine()) != null){
                //    //log.info("[Main Mosquitto Server Log]:"+line); -> _sub에서 출력해주면 된다.
                //    //항상 json으로 요청이 들어온다고 가정, 프론트에도 뿌릴 것이기에 추가
                //    new mainInfo(,, LocalDateTime.now());
                //}
            } catch (IOException e){ //| InterruptedException e) {
                e.printStackTrace();
                return "redirect:/error";
            }
        }
        return "/mainControllView";
    }

    //사실상 로그에다가 그냥 요청타임만 붙인거긴하다. 고로 아마 필요없을듯.
    @Getter
    @AllArgsConstructor
    private class MainInfo{
        //외부클래스에서 접근할수도 있으므로 public
        public double humidity;
        public double temperature;
        public LocalDateTime requestTime;
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    //messageArrived도 따지고보면 요청을 처리하는 컨트롤러니까 ResponseBody를 붙여도 되지 않을까?
    //@ResponseBody -> 안되는 이유 메모 참고
    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        String payload = new String(mqttMessage.getPayload(), StandardCharsets.UTF_8);
        //json으로 변환 후 http 요청 생성하기- @ResponseBody를 사용못하기에 수동변환
        //우선 온도와 습도를 입력하는 프론트창을 만들자.
        MainInfo mainInfo = new Gson().fromJson(payload, MainInfo.class);
        System.out.println(mainInfo.toString());
    }


    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
