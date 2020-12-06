package minecraftbyexample.mbe21_tileentityspecialrenderer;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class PassthroughItemStackHandler implements IItemHandler {
    public PassthroughItemStackHandler(TileEntityMBE21 tileEntity) {
        this.tileEntity = tileEntity;
    }

    @Override
    public int getSlots() {
        return getCurrentHandler().getSlots();
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return getCurrentHandler().getStackInSlot(slot);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        ItemStack toReturn = getCurrentHandler().insertItem(slot, stack, simulate);
        tileEntity.incrementDestination();
        return toReturn;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return getCurrentHandler().extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return getCurrentHandler().getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return getCurrentHandler().isItemValid(slot, stack);
    }

    private IItemHandler getCurrentHandler() {
        return tileEntity.getDestinationItemStackHandler();
    }

    private TileEntityMBE21 tileEntity;
}
