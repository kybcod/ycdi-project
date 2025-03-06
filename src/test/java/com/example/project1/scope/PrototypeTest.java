package com.example.project1.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeTest {

    @Test
    void prototypeBeanFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        System.out.println("find prototype bean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);

        System.out.println("find prototype bean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("singletonBean1 = " + prototypeBean1);
        System.out.println("prototypeBean1 = " + prototypeBean2);

        assertThat(prototypeBean1).isSameAs(prototypeBean2);

//        ac.close(); 죵료 메서드 호출 되지 않음 -> 클라이언트가 직접 해줘야 함
        prototypeBean1.destroy();
        prototypeBean2.destroy();
    }

    @Scope("prototype")
    static class PrototypeBean{

        @PostConstruct
        public void init(){
            System.out.println("prototype init");
        }

        @PreDestroy
        public void destroy(){
            System.out.println("prototype destroy");
        }
    }
}
