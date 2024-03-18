package org.example.mqtt_spring_dht11.shellCommand;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class serverSettings implements CommandLineRunner {

    private static final String startServerFileDir = "/Users/imjeongseob/WebWorkspace/MQTT_Spring_dht11/src/main/java/org/example/mqtt_spring_dht11/shellCommand/mosquitto_Start.sh";
    private static final String subFileDir = "/Users/imjeongseob/WebWorkspace/MQTT_Spring_dht11/src/main/java/org/example/mqtt_spring_dht11/shellCommand/mosquitto_sub.sh";
    private static final String pubFileDir = "/Users/imjeongseob/WebWorkspace/MQTT_Spring_dht11/src/main/java/org/example/mqtt_spring_dht11/shellCommand/mosquitto_pub.sh";

    @Override
    public void run(String... args) throws Exception {
        ProcessBuilder serverProcess = new ProcessBuilder();
        serverProcess.command(startServerFileDir);
        ProcessBuilder subProcess = new ProcessBuilder();
        subProcess.command(subFileDir);
        ProcessBuilder pubProcess = new ProcessBuilder();
        pubProcess.command(pubFileDir);

        Process serverStart = serverProcess.start();
        log.info(serverStart.info().toString());

        Process subStart = subProcess.start();
        log.info(subStart.info().toString());
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
    }
}
