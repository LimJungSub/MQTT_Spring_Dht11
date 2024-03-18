package org.example.mqtt_spring_dht11.controller;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.example.mqtt_spring_dht11.service.Mq_publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
@Controller
public class MainController {

    //컨트롤러에서 서버를 띄우는게 맞을까? SpringBootApplication이 실행되자마자 아래 init과 같이 초기화 작업을 해주어야 할 것이다.
    //생성자주입을 통해 컨트롤러에서 서비스빈을 주입받는다 해도 미리 서버가 정의되어있지않기때문 (서비스빈에서는 클라이언트 연결을 시도한다)

    private Mq_publisher mqPublisher;

    @Autowired
    public MainController(Mq_publisher mqPublisher){
        this.mqPublisher = mqPublisher;
    }

    //CommandLineRunner 작성 후, init에서는 뷰리턴만 중요시보면 될것이다.
    @GetMapping("/")
    public String init() {
        return "mainControlView";
    }

    @PostMapping("/record")
    public void recordByWebJsonRequest(){
        //여기서 들어온 Json형태의 데이터를 -> MainInfo타입으로 변환해준다.
        Gson gson = new Gson();
        double humidity;
        double temperature;
        LocalDateTime requestTime;
    }

}
