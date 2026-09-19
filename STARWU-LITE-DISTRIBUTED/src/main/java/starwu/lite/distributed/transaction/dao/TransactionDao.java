package starwu.lite.distributed.transaction.dao;

import org.apache.ibatis.annotations.Update;
import starwu.lite.metadata.entity.distributed.transaction.Transaction;
import starwu.lite.dao.core.BaseDao;

public interface TransactionDao extends BaseDao<Transaction> {

    @Update("UPDATE starwu_lite_transaction SET call_time_Type = 0 WHERE call_time_Type = 1 limit 2000")
    int updateStatus();

}
