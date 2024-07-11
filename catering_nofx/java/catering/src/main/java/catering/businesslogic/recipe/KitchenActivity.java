package catering.businesslogic.recipe;

import catering.businesslogic.user.User;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class KitchenActivity {
    private static Map<Integer, KitchenActivity> loadedActivities = new HashMap<>();
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

    public static KitchenActivity loadActivityById(int id) {
        if(loadedActivities.containsKey(id)) {
            return loadedActivities.get(id);
        }
        KitchenActivity load = new Recipe("");
        String userQuery = "SELECT * FROM recipes WHERE id='"+id+"'";
        PersistenceManager.executeQuery(userQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                load.id = rs.getInt("id");
                load.name = rs.getString("name");
                load.difficulty = Difficulty.fromInt(rs.getInt("difficulty"));
            }
        });
        if(load.id > 0) {
            loadedActivities.put(load.id, load);
        }
        return load;
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
