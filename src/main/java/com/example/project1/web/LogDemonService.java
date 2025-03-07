package com.example.project1.web;

import com.example.project1.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Scope;

@Scope
@RequiredArgsConstructor
public class LogDemonService {

    private final MyLogger myLogger;

    public void logic(String id) {
        myLogger.log("service id = "+ id);
    }
}
