package portalgun.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.Level;
import portalgun.PortalGunMod;
import portalgun.config.PortalGunConfig;
import portalgun.items.PortalGunItem;
import portalgun.misc.BlockList;

import java.util.Collection;

public class PortalGunRechargeRecipe extends CustomRecipe {
    public static final RecipeSerializer<PortalGunRechargeRecipe> SERIALIZER =
        new SimpleCraftingRecipeSerializer<>(PortalGunRechargeRecipe::new);
    
    public static void init() {
        Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            new ResourceLocation("portalgun:portal_gun_recharge"),
            SERIALIZER
        );
    }
    
    public PortalGunRechargeRecipe(CraftingBookCategory category) {
        super(category);
    }
    
    @Override
    public boolean matches(CraftingContainer container, Level level) {
        return container.countItem(PortalGunMod.PORTAL_GUN) == 1 &&
            container.countItem(Items.NETHER_STAR) == 1;
    }
    
    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        ItemStack portalGun =
            container.getItems().stream().filter(i -> i.getItem() == PortalGunMod.PORTAL_GUN)
                .findFirst().orElse(null);
        
        if (portalGun == null) {
            return ItemStack.EMPTY;
        }
        
        PortalGunItem.ItemInfo itemInfo = PortalGunItem.ItemInfo.fromTag(portalGun.getOrCreateTag());
        itemInfo.remainingEnergy = itemInfo.maxEnergy;
        return itemInfo.toStack();
    }
    
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }
    
    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}
