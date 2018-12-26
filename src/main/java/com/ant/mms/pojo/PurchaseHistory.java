package com.ant.mms.pojo;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by wolf   2018/12/23
 */
@Entity
@Table
@Data
@DynamicUpdate
public class PurchaseHistory {

    @Id
    private String Id;
    /*买家Id.*/
    private String UserId;
    /*当前次消费总金额.*/
    private String account;
    /*返利金额.*/
    private String  rebate;

}
