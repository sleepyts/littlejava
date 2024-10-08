package Test;

public class CreatureFactory {
    public static Creature createCreature(Class<? extends Creature> creatureClass) {
        try {
            return (Creature) creatureClass.newInstance();
        }

        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
