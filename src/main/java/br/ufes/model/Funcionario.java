package br.ufes.model;

import java.util.ArrayList;

public class Funcionario {

    private String nome;
    private double salarioBase;
    private double salarioTotal;
    private int distanciaMoradia;
    private int faltas;
    private String cargo;
    private final ArrayList<Bonus> bonusRecebidos;

    public Funcionario(String nome, double salarioBase, String cargo) throws Exception {
        String exceptions = "";

        if (nome == null || nome == "" || nome.matches("[0-9]*")) {
            exceptions = exceptions.concat("#1 Informe um nome válido");
        }

        if (cargo == null || !((cargo.toUpperCase().equals("GERENTE"))
                || (cargo.toUpperCase().equals("SUPERVISOR"))
                ||(cargo.toUpperCase().equals("PROGRAMADOR")))) {
            exceptions = exceptions.concat("#2 Informe um cargo válido");
        }
        if (salarioBase < 998.0) {
            exceptions = exceptions.concat("#3 O salário base deve ser >= R$ 998,00");
        }

        if (exceptions.length() > 0) {
            throw new Exception(exceptions);
        }
        this.nome = nome;
        this.cargo = cargo;
        this.salarioBase = salarioBase;
        this.bonusRecebidos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSalarioBase() {
        return this.salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) throws Exception {
        if (faltas < 0  ){
            throw new Exception("#4 A quantidade de faltas deve ser >= 0");
        }
        
        this.faltas = faltas;
    }

    public int getDistanciaMoradia() {
        return distanciaMoradia;
    }

    public void setDistanciaMoradia(int distanciaMoradia) throws Exception {
            if (distanciaMoradia < 0  ){
            throw new Exception("#5 A distancia deve ser >= 0");
        }
        
        this.distanciaMoradia = distanciaMoradia;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return this.calculaSalario();
    }

    private double calculaSalario() {
        this.salarioTotal = this.salarioBase + this.calculaTotalBonus();
        return this.salarioTotal;
    }

    public void addBonus(Bonus bonus) {
        this.bonusRecebidos.add(bonus);
    }

    public double calculaTotalBonus() {
        double totalBonus = 0;
        for (Bonus bonus : bonusRecebidos) {
            totalBonus += bonus.getValor();
        }

        return totalBonus;
    }

}
