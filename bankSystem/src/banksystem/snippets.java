/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banksystem;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import static java.lang.Integer.parseInt;
import java.time.LocalDate;
import java.time.Period;
import java.util.Random;

/**
 *
 * @author ricar
 */
public class snippets {
    String iban;
    String prefix = "PT50";
    private boolean existe;

    public snippets() {}

    dbQuerys db = new dbQuerys();
    
    public void closeProgramm() {
        System.out.println("Bem, parece que vais embora :/");
        System.exit(0);
    }


    public final static void clear() {
        try {
            Robot robot = new Robot();
            robot.setAutoDelay(10);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_L);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_L);
        } catch (AWTException ex) {}
    }
  
    
public String gerar() {
        
        long num1;
        long num2;
        long num3;
        long ibanNum;

        int t1 = 10;
        int t2 = 10;
        int t3 = 1;

        do{
            
           int m1 = (int) Math.pow(10, t1 - 1);
           int m2 = (int) Math.pow(10, t2 - 1);
           int m3 = (int) Math.pow(10, t3 - 1);

           num1 = m1 + new Random().nextInt(9 * m1);
           num2 = m2 + new Random().nextInt(9 * m2);
           num3 = m3 + new Random().nextInt(9 * m3);
           
           iban= prefix + num1 + num2 + num3;
           
           existe = db.verificaIban(iban);    
        }
        while(existe==true);
   
        return iban;
    }


public int calcularIdade(String dt) {

        int barraInicial = 0;
        int barraFinal = 0;

        barraInicial = dt.indexOf("/");
        barraFinal = dt.lastIndexOf("/");

        String dia = "";
        String mes = "";
        String ano = "";

        dia = dt.substring(0, barraInicial);
        mes = dt.substring(barraInicial + 1, barraFinal);
        ano = dt.substring(barraFinal + 1);

        LocalDate dtNasc = LocalDate.of(parseInt(ano), parseInt(mes), parseInt(dia));
        LocalDate atual = LocalDate.now();
        Period diff = Period.between(dtNasc, atual);

        return diff.getYears();
    }
    
}
