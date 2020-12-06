package minecraftbyexample.mbe30_inventory_basic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * User: brandon3055
 * Date: 06/01/2015
 *
 * This is a simple tile entity implementing IInventory that can store 9 item stacks
 */
public class TileEntityInventoryBasic extends TileEntity {
	// Create and initialize the items variable that will store store the items
	private final int NUMBER_OF_SLOTS = 9;

	private ItemStackHandler itemStackHandler = new ItemStackHandler(NUMBER_OF_SLOTS) {
		@Override
		protected void onContentsChanged(int slot) {
			TileEntityInventoryBasic.this.markDirty();
		}
	};

    // This is where you save any data that you don't want to lose when the tile entity unloads
    // In this case, it saves the itemstacks stored in the container
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound); // The super call is required to save and load the tileEntity's location
        compound.setTag("items", itemStackHandler.serializeNBT());
        return compound;
    }

    // This is where you load the data that you saved in writeToNBT
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if (compound.hasKey("items")) {
            itemStackHandler.deserializeNBT((NBTTagCompound)compound.getTag("items"));
        }
    }

    public boolean canInteractWith(EntityPlayer player) {
        return !isInvalid() && player.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }

        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);
        }

        return super.getCapability(capability, facing);
    }

    // will add a key for this container to the lang file so we can name it in the GUI
	public String getName() {
		return "container.mbe30_inventory_basic.name";
	}

	public boolean hasCustomName() {
		return false;
	}

	// standard code to look up what the human-readable name is
	@Override
	public ITextComponent getDisplayName() {
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
	}
}
