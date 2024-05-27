package org.example;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;



abstract class Main {
    private static int NEED_XP_TO_NEXT_LEVEL = 100;
    private static final int GIVE_XP = 20;
    public static String[] monsterNames = {"Гоблін", "Троль", "Орк", "Дракон"};
    private static final String SAVE_FILE = "gladiator_save.txt";
    private static int monsterDamage;
    private static int Runaway;
    private static int Friendly;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Gladiator gladiator = null;

        System.out.println("1. Почати нову гру\n2. Жавантажити гру");
        int choice = scanner.nextInt();

        if (choice == 1) {
            gladiator = chooseGladiator(scanner);
        } else if (choice == 2) {
            try {
                gladiator = Gladiator.loadFromFile(SAVE_FILE);
                System.out.println("Гра завантажена без порблем");
            } catch (IOException e) {
                System.out.println("Не вдалося завантажити гру, прченаємо нову.");
                gladiator = chooseGladiator(scanner);
            }
        }

        while (true) {
            System.out.println("\n1. Подорож\n2. Показати стати\n3. Сохранити гру та вийти");
            System.out.print("Вибери що робити: ");
            int actionChoice = scanner.nextInt();

            switch (actionChoice) {
                case 1 -> travel(gladiator);
                case 2 -> showAttributes(gladiator);
                case 3 -> {
                    try {
                        gladiator.saveToFile(SAVE_FILE);
                        System.out.println("Гру сохранено без проблем");
                    } catch (IOException e) {
                        System.out.println("Не вдалося завантажити гру.");
                    }
                    return;
                }
            }
        }
    }

    private static Gladiator chooseGladiator(Scanner scanner) throws IOException {
        System.out.println("1. Галус (Танк) - більше здоров'я, харизми\n2. Сігітаріус (Лучник) - більше спритності, шанса втечі\n3. Спартак (Воїн) - більше сили, стійкості");
        System.out.print("Вибери гладіатора: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> {
                Gallus gallus = new Gallus(2,5, 2, 0, 1, 1, 3, 10, 30, true, 5);
                System.out.println(" ");
                System.out.println("Вітаю, ти вибрав Галуса (Танка). Ти маєш: " + (gallus.getBasicHp() + gallus.getConstitution() * 5) + "хп та макс атака " + (gallus.getBasicAttack() + gallus.getStrength() * 3));
                System.out.println(" ");
                gallus.saveToFile(SAVE_FILE);
                return gallus;
            }
            case 2 -> {
                Sagittarius sagittarius = new Sagittarius(3,2, 2, 0, 5, 1, 2, 8, 20, false, 5);
                System.out.println(" ");
                System.out.println("Вітаю, ти вибрав Сігітаріуса (Лучник). Ти маєш: " + (sagittarius.getBasicHp() + sagittarius.getConstitution() * 5) + "хп та макс атака " + (sagittarius.getBasicAttack() + sagittarius.getStrength() * 3));
                System.out.println(" ");
                return sagittarius;
            }
            case 3 -> {
                Spartac spartac = new Spartac(1,2, 5, 0, 2, 1, 1, 14, 15, false, 5);
                System.out.println(" ");
                System.out.println("Вітаю, ти вибрав Спартака (Воїна). Ти маєш: " + (spartac.getBasicHp() + spartac.getConstitution() * 5) + "хп та макс атака " + (spartac.getBasicAttack() + spartac.getStrength() * 3));
                System.out.println(" ");
                return spartac;
            }
            default -> throw new IllegalArgumentException("Невірний вибір!");

        }
    }
    private static void showAttributes(Gladiator gladiator) {
        System.out.printf(
                """
                        1. Конституція (збільшене здоров'я)(%d)
                        2. Міцність (Збільшений урон)(%d)
                        3. Спритність (Шанс втечі)(%d)
                        4. Харізма (Шанс задобрити ворога)(%d)
                        5. Критичний шанс (%d)
                        6. Вампиризм (Додаткова атака яка зозволяє красти ХП твого ворога коли атакуєш) (%d)
                        7. LVL (%d)
                        8. Досівд (%d)
                        9. Всього ХП (%d)
                        10. Весь Урон (%d)
                        """,
                gladiator.getConstitution(),
                gladiator.getStrength(),
                gladiator.getDexterity(),
                gladiator.getCharisma(),
                gladiator.getCritChance(),
                gladiator.getLifesteal(),
                gladiator.getLevel(),
                gladiator.getXp(),
                gladiator.getBasicHp() + gladiator.getConstitution() * 5,
                gladiator.getBasicAttack() + gladiator.getStrength() * 3
        );
    }
    private static void upgradeAttributes(Gladiator gladiator) {
        System.out.printf(
                """
                        1. Конституція (Заробити більше досвіду) (%d)
                        2. Міцність (%d)
                        3. Спритність (Шанс втечі)(%d)
                        4. Харізма (Шанс задобрити ворога)(%d)
                        5. Критичний шанс (%d)
                        6. Вампиризм (Додаткова атака яка зозволяє красти ХП твого ворога коли атакуєш) (%d)
                        Що ти хочеш прокачати? """,
                gladiator.getConstitution(),
                gladiator.getStrength(),
                gladiator.getDexterity(),
                gladiator.getCharisma(),
                gladiator.getCritChance(),
                gladiator.getLifesteal()
        );
        System.out.println(" ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> gladiator.setConstitution(gladiator.getConstitution() + 1);
            case 2 -> gladiator.setStrength(gladiator.getStrength() + 1);
            case 3 -> gladiator.setDexterity(gladiator.getDexterity() + 1);
            case 4 -> gladiator.setCharisma(gladiator.getCharisma() + 1);
            case 5 -> gladiator.setCritChance(gladiator.getCritChance() + 1);
            case 6 -> gladiator.setLifesteal(gladiator.getLifesteal() + 1);
        }
    }

    private static void levelUp(Gladiator gladiator) {
        if (gladiator.getXp() >= NEED_XP_TO_NEXT_LEVEL) {
            gladiator.setLevel(gladiator.getLevel() + 1);
            System.out.println(" ");
            System.out.println("Мої вітання! Ти повисив свій LVL до " + gladiator.getLevel());
            System.out.println(" ");
            int points = 5;
            NEED_XP_TO_NEXT_LEVEL *= 1.4;
            while (points > 0) {
                upgradeAttributes(gladiator);
                points--;
            }
        }
    }
    private static int calculateCombatEffect(int health, int attack) {
        return (int) Math.sqrt(health * attack);
    }

    public static void Charisma (Monster monster, Gladiator gladiator) {
        int charismaCheck = randNumber(0, 50) + gladiator.getCharisma();
        int monsterReaction = randNumber(0, 100);
        if (charismaCheck > monsterReaction) {
            Runaway = 1;
            System.out.println(" ");
            System.out.println("Ти задобрив ворога, ти отримав 1 досвід");
            System.out.println(" ");
            gladiator.setXp(gladiator.getXp() + 1);
        } else {
            Runaway = 2;
            System.out.println(" ");
            System.out.println("Ви спробували задобрити/домовитись, але монстр атакує!");
            System.out.println(" ");
        }
    }
    public static void Dexterity (Monster monster, Gladiator gladiator) {
        int number = randNumber(0, 100);
        int number1 = randNumber(0, 50) +  (gladiator.getDexterity() * 10);
        if (number1 >= number) {
            Friendly = 1;
            System.out.println(" ");
            System.out.println("Ви втекли від монстра.");
            System.out.println(" ");
            gladiator.setXp(gladiator.getXp() + 1);
        } else {
            Friendly = 2;
            System.out.println(" ");
            System.out.println("Не вдалось втекти :(");
            System.out.println(" ");
        }
    }
    public static int randNumber ( int min, int max){
        return new Random().nextInt(max - min) + min;
    }
    private static void travel(Gladiator gladiator) {
        Monster monster = new Monster(
                randNumber(40, 100 * gladiator.getLevel()),
                randNumber(5, 30 + gladiator.getLevel()),
                monsterNames[randNumber(0, monsterNames.length)]
        );
        System.out.println(" ");
        System.out.printf("Ти зустрів %s, воно має %d ХП і %d урон(а)\n", monster.getName(), monster.getHp(), monster.getAttack());
        System.out.println(" ");
        System.out.println("1. битися\n2. договір/дружба\n3. втеча");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Вибери: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> fight(monster, gladiator);
            case 2 -> Charisma(monster, gladiator);
            case 3 -> Dexterity(monster, gladiator);
        }
    }

    private static void fight(Monster monster, Gladiator gladiator) {
        Runaway = 3;
        Friendly = 3;
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int gladHp = gladiator.getBasicHp() + gladiator.getConstitution() * 5;
        int giveXP = (int) Math.sqrt(monster.getAttack() * monster.getHp());
        while (monster.getHp() > 0 && gladiator.getBasicHp() > 0) {
            System.out.println(" ");
            System.out.print("1. атакувати\n2. блок\n3. атака вампіра\nТвоя дія: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                monsterDamage = monster.getAttack();
                int attack = randNumber(
                        gladiator.getBasicAttack(),
                        gladiator.getBasicAttack() + gladiator.getStrength()
                );
                if (random.nextInt(50) < gladiator.getCritChance()) {
                    attack *= 2;
                    System.out.println(" ");
                    System.out.println("Критична атака!");
                    System.out.println(" ");
                }
                monster.setHp(monster.getHp() - attack);
                System.out.println(" ");
                System.out.println("Ти наніс"+ attack +"урон(а) ворогу");
                System.out.println(" ");
                if (monster.getHp() <= 0) {
                    monster.setHp(0);
                    System.out.println(" ");
                    System.out.println("Ти виграв. ти маєш " + giveXP + " довіду. І ти відхилився на"+ monster.getHp() / 5 +"ХП");
                    int xpGain = calculateCombatEffect(monster.getHp(), monster.getAttack()) / 10;
                    gladiator.setXp(gladiator.getXp() + xpGain);
                    gladiator.setBasicHp(gladiator.getBasicHp() + monster.getHp() / 5);
                    levelUp(gladiator);
                    return;
                }
            }
            if (choice == 2) {
                monsterDamage = monster.getAttack() / 2;
                return;
            }
            if (choice == 3) {
                monsterDamage = monster.getAttack();
                int attack = randNumber(
                        gladiator.getBasicAttack(),
                        gladiator.getBasicAttack() + gladiator.getStrength()
                );
                if (random.nextInt(50) < gladiator.getCritChance()) {
                    attack *= 2;
                    System.out.println(" ");
                    System.out.println("Критична атака!");
                    System.out.println(" ");
                }
                monster.setHp(monster.getHp() - attack);
                System.out.println(" ");
                System.out.println("Ти наніс"+ attack +"урон(а) ворогу. І відхилився на "+ attack / 3 * gladiator.getLifesteal() +"ХП");
                System.out.println(" ");
                gladiator.setBasicHp(gladiator.getBasicHp() + attack / 3 * gladiator.getLifesteal());
                if (monster.getHp() <= 0) {
                    monster.setHp(0);
                    System.out.println(" ");
                    System.out.println("Ти виграв. ти маєш " + giveXP + " довіду. І ти відхилився на"+ monster.getHp() / 5 +"ХП");
                    int xpGain = calculateCombatEffect(monster.getHp(), monster.getAttack()) / 10;
                    gladiator.setXp(gladiator.getXp() + xpGain);
                    gladiator.setBasicHp(gladiator.getBasicHp() + monster.getHp() / 5);
                    levelUp(gladiator);
                    return;
                }
            }
            }


            int damage = randNumber(monster.getAttack() / 2, monster.getAttack());

            if (gladiator.isHaveShield()) {
                    damage /= 2;
            }
            if (Runaway == 2 || Friendly == 2 || Runaway == 3 || Friendly == 3 ){
                gladHp -= damage;
                gladiator.setBasicHp(gladHp);
                gladiator.setBasicHp(gladiator.getBasicHp() - monsterDamage);
                System.out.println(" ");
                System.out.println(monster.getName() + " наніс " + monsterDamage + " урона по тобі.");
                System.out.println(" ");
                if (gladiator.getBasicHp() <= 0) {
                    gladiator.setBasicHp(0);
                    System.out.println("Ти програв!!!");
                    int lossXp = calculateCombatEffect(monster.getHp(), monster.getAttack()) / 10;
                    gladiator.setXp(gladiator.getXp() - lossXp);
                }
            }
            System.out.println(" ");
            System.out.println("Твій ХП: " + gladiator.getBasicHp());
            System.out.println("Ворожий ХП: " + monster.getHp());
            System.out.println(" ");
        }
      }


