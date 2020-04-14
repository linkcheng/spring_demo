package cn.xyf.config;

import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApolloConfig {
    @ApolloConfigChangeListener
    private void elementPublished(ConfigChangeEvent event){
        ConfigChange ch = event.getChange("element");
        if(null == ch){
            return;
        }
        log.info("getOldValue="+ch.getOldValue());
        log.info("getNewValue="+ch.getNewValue());
    }
}
