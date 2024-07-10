package catering.businesslogic.recipe;

public abstract class KitchenActivity {
    int id;
    String name;
    Difficulty difficulty;
    public KitchenActivity(String name) {
        this.name = name;
    }

    public KitchenActivity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static enum Difficulty {
        EASY(0), MEDIUM(1), HARD(2);

        private final int level;

        Difficulty(int level) {
            this.level = level;
        }
        public int getLevel() {
            return level;
        }

        public static Difficulty fromInt(int level) {
            for (Difficulty difficulty : Difficulty.values()) {
                if (difficulty.getLevel() == level) {
                    return difficulty;
                }
            }
            throw new IllegalArgumentException("Invalid difficulty level: " + level);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KitchenActivity that = (KitchenActivity) o;

        if (!name.equals(that.name)) return false;
        return difficulty == that.difficulty;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + difficulty.hashCode();
        return result;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public String getName() {
        return name;
    }
}
