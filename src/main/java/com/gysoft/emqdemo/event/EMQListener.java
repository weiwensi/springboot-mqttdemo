package com.gysoft.emqdemo.event;


import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 魏文思
 * @date 2019/11/15$ 15:10$
 */
@WebListener
public class EMQListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        // TODO Auto-generated method stub
        System.out.println("======requestDestroyed 请求销毁========");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("======requestInitialized 请求到达========");
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(() -> {

        });
    }
}
