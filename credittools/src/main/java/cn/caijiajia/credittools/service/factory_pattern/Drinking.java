package cn.caijiajia.credittools.service.factory_pattern;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Drinking implements Provider {

    @Override
    public void action() {
        System.out.println("drinking....");
    }
}
