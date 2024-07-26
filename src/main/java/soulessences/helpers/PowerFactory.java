package soulessences.helpers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.lang.reflect.Constructor;

public class PowerFactory {
    public static AbstractPower createPower(Class<? extends AbstractPower> powerClass, AbstractCreature owner, int amount, boolean isSourceMonster) {
        try {
            // Try constructor with (AbstractCreature, int, boolean)
            try {
                Constructor<? extends AbstractPower> constructor = powerClass.getConstructor(AbstractCreature.class, int.class, boolean.class);

                System.out.println("Using constructor with (AbstractCreature, int, boolean)");

                return constructor.newInstance(owner, amount, isSourceMonster);

            } catch (NoSuchMethodException e) {
                // Try constructor with (AbstractCreature, int)
                Constructor<? extends AbstractPower> constructor = powerClass.getConstructor(AbstractCreature.class, int.class);

                System.out.println("Using constructor with (AbstractCreature, int)");

                return constructor.newInstance(owner, amount);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to create power instance for " + powerClass.getName());
            return null;
        }
    }
}
