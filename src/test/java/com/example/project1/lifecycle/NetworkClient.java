package com.example.project1.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url =" + url);

    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작 시 호출
    public void connect() {
        System.out.println("connect : " + url);
    }

    public void call(String msg) {
        System.out.println("call : " + url + " msg : " + msg);
    }

    public void disconnect() {}

    public void init(){
        connect();
        call("초기화 연결 메시지");
    }

    public void close() {
        disconnect();
    }

}
