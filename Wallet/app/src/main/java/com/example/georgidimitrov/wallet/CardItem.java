package com.example.georgidimitrov.wallet;

/**
 * Created by georgidimitrov on 15-05-12.
 */
public class CardItem {

    private String cardName;
    private String cardExtra1;
    private String cardExtra1_1;
    private String cardExtra2;
    private String cardExtra2_1;
    private String cardNumber;

    public void add(String name, String extra1, String extra1_1, String extra2, String extra2_1, String number)
    {
        cardName = name;
        cardExtra1 = extra1;
        cardExtra1_1 = extra1_1;
        cardExtra2 = extra2;
        cardExtra2_1 = extra2_1;
        cardNumber = number;
    }

    public String getName() {return cardName; }
    public void setName(String newName) { cardName = newName; }

    public String getExtra1() {return cardExtra1; }
    public void setExtra1(String newExtra1) { cardExtra1 = newExtra1; }

    public String getExtra1_1() {return cardExtra1_1; }
    public void setExtra1_1(String newExtra1_1) { cardExtra1_1 = newExtra1_1; }

    public String getExtra2() {return cardExtra2; }
    public void setExtra2(String newExtra2) { cardExtra2 = newExtra2; }

    public String getExtra2_1() {return cardExtra2_1; }
    public void setExtra2_1(String newExtra2_1) { cardExtra2_1 = newExtra2_1; }

    public String getNumber() {return cardNumber; }
    public void setNumber(String newNumber) { cardNumber = newNumber; }
}