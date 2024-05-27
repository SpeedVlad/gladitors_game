package org.example;

public class Monster {
    public int hp;
    public int attack;
    public static String[] monsterNames = {"Гоблін", "Троль", "Орк", "Дракон"};
    public String name;

    public Monster(int hp, int attack, String name) {
        this.hp = hp;
        this.attack = attack;
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}