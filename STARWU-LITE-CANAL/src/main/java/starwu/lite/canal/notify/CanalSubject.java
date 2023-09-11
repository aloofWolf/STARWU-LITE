package starwu.lite.canal.notify;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import starwu.lite.metadata.bean.canal.CanalDataBean;
import starwu.lite.metadata.config.canal.CanalConfig;
import starwu.lite.design.observer.SubjectApi;

@RequiredArgsConstructor
@Component
public class CanalSubject extends SubjectApi<CanalDataBean> {

    private final CanalConfig config;

    @Override
    public String getAsyncThreadName() {
        return config.getNotifyDataAsyncThreadName();
    }
}
