package com.creativearts.muthomi.timetable1;

/**
 * Created by Eduh_mik on 6/18/2017.
 */

public class MainClass {
    String date_of_withdraw, amount_withdrawn,description,option_withdraw;
    String date_of_deposit, amount_deposited,option_deposit;

    public String getDate_of_withdraw() {
        return date_of_withdraw;
    }

    public String getOption_withdraw() {
        return option_withdraw;
    }

    public void setOption_withdraw(String option_withdraw) {
        this.option_withdraw = option_withdraw;
    }

    public String getOption_deposit() {
        return option_deposit;
    }

    public void setOption_deposit(String option_deposit) {
        this.option_deposit = option_deposit;
    }

    public void setDate_of_withdraw(String date_of_withdraw) {
        this.date_of_withdraw = date_of_withdraw;
    }

    public String getAmount_withdrawn() {
        return amount_withdrawn;
    }

    public void setAmount_withdrawn(String amount_withdrawn) {
        this.amount_withdrawn = amount_withdrawn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_of_deposit() {
        return date_of_deposit;
    }

    public void setDate_of_deposit(String date_of_deposit) {
        this.date_of_deposit = date_of_deposit;
    }

    public String getAmount_deposited() {
        return amount_deposited;
    }

    public void setAmount_deposited(String amount_deposited) {
        this.amount_deposited = amount_deposited;
    }
}
