package cn.lyk.learn.springboot.utils;

import org.eclipse.paho.client.mqttv3.*;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class MqttClient {

    /**MQTT服务端ip及端口*/
    private static String host = "tcp://120.27.235.176:1883";

    /**账号*/
    private static String username = "root";

    /**密码*/
    private static String password = "root";

    /**clientID*/
    private static String clientId = "bs_monitor_clinet_stu"+System.currentTimeMillis();


    /**MQTT-Client*/
    private static org.eclipse.paho.client.mqttv3.MqttClient client;

    /**MQTT-Client*/
    private static int uuid = 0;

    /**
     * @throws InterruptedException
     * @throws MqttException */
    public static void main(String[] args) throws InterruptedException, MqttException {

//        subscribe();
//
    }


    public static Map<String, String> dataMap = new HashMap<>();

    /**
     *
     * 描述：订阅信息
     * @since
     * @return
     */
    public static void subscribe(final String code,MqttCallback mqttCallback) {
        try {
            // 创建MqttClient
            org.eclipse.paho.client.mqttv3.MqttClient client = MqttClient.getClient();
            client.setCallback(mqttCallback);
            client.subscribe(code, 0);
            System.out.println("上位机系统链接到MQTT服务器的状态为:" + client.isConnected());
			if(client.isConnected()){
				System.out.println("上位机系统启动成功，可以通过网页访问系统，访问默认地址：http://localhost:8080   ←←←←可点击直接开），正在等待硬件连接中...");
				System.out.println("当下面出现《 硬件数据接收成功 》的字样时，就是硬件和软件连接成功了。");
			}
        } catch (Exception e) {
            
        }
    }

    /**
     *
     * 描述：获取MqttClient
     * @author mao2080@sina.com
     * @created 2017年7月6日 上午9:56:37
     * @since
     * @return
     * @throws MqttException
     */
    public static org.eclipse.paho.client.mqttv3.MqttClient getClient( ) throws MqttException{
        try {
            if(client == null){
                client = new org.eclipse.paho.client.mqttv3.MqttClient(host,clientId);
                MqttConnectOptions conOptions = new MqttConnectOptions();
                conOptions.setUserName(username);
                conOptions.setPassword(password.toCharArray());
                conOptions.setCleanSession(true);
                client.connect(conOptions);
            }
            if(!client.isConnected()){
                client.reconnect();
            }
        } catch (Exception e) {
             e.printStackTrace();
        }
        return client;
    }

    /**
     *
     * @since
     */
    public static void publish(String code,String cmd) throws MqttException {
        try {
            org.eclipse.paho.client.mqttv3.MqttClient client = MqttClient.getClient();
            MqttTopic topic = client.getTopic(code);
            MqttMessage message = new MqttMessage(cmd.getBytes());
            message.setQos(0);
            topic.publish(message);
            System.out.println("MQTT Send:" + cmd);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}