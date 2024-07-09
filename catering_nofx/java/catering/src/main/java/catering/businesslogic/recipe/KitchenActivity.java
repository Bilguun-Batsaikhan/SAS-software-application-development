package catering.businesslogic.recipe;

public abstract class KitchenActivity {
    String name;

    public KitchenActivity(String name) {
        this.name = name;
    }

    public static enum Difficulty {
        EASY, MEDIUM, HARD
    }

    public String getName() {
        return name;
    }
}
