package com.jm.projectunion.mine.entity;

import com.jm.projectunion.entity.Result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Young on 2017/11/8.
 */

public class IncomeListResult extends Result {

    private List<IncomeBean> data;

    public List<IncomeBean> getData() {
        return data;
    }

    public void setData(List<IncomeBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "IncomeListResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class IncomeBean implements Serializable {
        /**
         * accountLogId:	integer ($int32) 日志ID
         * ctime:	string ($date-time)时间
         * name:	string名称
         * money:	number ($double)金额
         * moneyStr:	string展示金额
         */
        private String accountLogId;
        private String ctime;
        private String name;
        private String money;
        private String moneyStr;

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getAccountLogId() {
            return accountLogId;
        }

        public void setAccountLogId(String accountLogId) {
            this.accountLogId = accountLogId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMoneyStr() {
            return moneyStr;
        }

        public void setMoneyStr(String moneyStr) {
            this.moneyStr = moneyStr;
        }

        @Override
        public String toString() {
            return "IncomeBean{" +
                    "accountLogId='" + accountLogId + '\'' +
                    ", ctime='" + ctime + '\'' +
                    ", name='" + name + '\'' +
                    ", money='" + money + '\'' +
                    ", moneyStr='" + moneyStr + '\'' +
                    '}';
        }
    }
}
