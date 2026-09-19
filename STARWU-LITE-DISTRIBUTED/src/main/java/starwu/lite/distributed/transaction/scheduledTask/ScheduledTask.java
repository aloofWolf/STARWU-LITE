package starwu.lite.distributed.transaction.scheduledTask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import starwu.lite.distributed.transaction.dao.TransactionDao;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTask {

    private final TransactionDao transactionDao;

    @PostConstruct
    public void start(){
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            try{
                updateStatus();
            }catch(Throwable e){
                log.error(e.getMessage());
            }
        }, 0, 5, TimeUnit.MINUTES);

    }

    private void  updateStatus(){
        while(true){
            int result = transactionDao.updateStatus();
            if(result <= 0){
                return;
            }
        }
    }
}
