package com.ant.mms.respository;

import com.ant.mms.pojo.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * 说明：订单业务结束后需要记录的数据.
 * Created by wolf
 */
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory,String> {


}
