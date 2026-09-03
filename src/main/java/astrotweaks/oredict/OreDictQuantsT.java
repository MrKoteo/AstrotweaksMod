package astrotweaks.oredict;

import net.minecraftforge.oredict.OreDictionary;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

import astrotweaks.item.*;



public class OreDictQuantsT {
    public OreDictQuantsT() {}

    public static void init() {
        registerQuantItems("singleQuant",
            ItemAlphaQuant.block, ItemBetaQuant.block, ItemGammaQuant.block, ItemDeltaQuant.block,
            ItemStrangeQuant.block,
            ItemNullQuant.block
        );

        //registerQuantItems("doubleQuant",
        //);

        registerQuantItems("tripleQuant",

			ItemTripleABGQuant.block,
            ItemTripleABDQuant.block,
            ItemTripleAGDQuant.block,
			ItemTripleBGDQuant.block
        );

        registerQuantItems("quadQuant",

            ItemQuadABGDQuant.block,
            ItemQuadAAAAQuant.block,
            ItemQuadBBBBQuant.block,
            ItemQuadGGGGQuant.block,
            ItemQuadDDDDQuant.block
        );
    }

    private static void registerQuantItems(String oreName, Item... items) {
        for (Item item : items) {
            OreDictionary.registerOre(oreName, new ItemStack(item, 1));
        }
    }
}