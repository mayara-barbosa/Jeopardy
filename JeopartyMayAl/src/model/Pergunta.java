/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author alexandre.chaves
 */
 
public class Pergunta{
    
    private Integer show_number;
    private String category;
    private String question;
    private String value;
    private String answer;
    private String round;
    private Date air_date;

    
    public Integer getShow_number() {
        return show_number;
    }

    public void setShow_number(Integer show_number) {
        this.show_number = show_number;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public Date getAir_date() {
        return air_date;
    }

    public void setAir_date(Date air_date) {
        this.air_date = air_date;
    }
      
}

