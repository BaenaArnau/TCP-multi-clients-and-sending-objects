package org.example;

public class SecretNum {
    /* Classe que genera numeros aleatoris per jugar a adivinar-los i els comprova
     * tant si rep un numero o una string.
     */

    private int num;

    public SecretNum() {
        num = 0;
    }

    public SecretNum(int n) {
        pensa(n);
    }

    public void pensa(int max) {
        setNum((int) ((Math.random()*max)+1));
        //System.out.println("He pensat el " + getNum());
    }

    public int comprova(int n) {
        if(num==n) return 0;
        else if(num<n) return 1;
        else return 2;
    }

    public String comprova(String s) {
        int n = Integer.parseInt(s);
        if(num==n) return "Correcte";
        else if(num<n) return "Més petit";
        else return "Més gran";
    }


    public int getNum() {
        return num;
    }

    private void setNum(int num) {
        this.num = num;
    }


}
