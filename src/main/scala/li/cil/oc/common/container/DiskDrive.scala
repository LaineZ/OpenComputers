package li.cil.oc.common.container

import li.cil.oc.common.Slot
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.IInventory

class DiskDrive(id: Int, playerInventory: PlayerInventory, drive: IInventory) extends Player(null, id, playerInventory, drive) {
  addSlotToContainer(80, 35, Slot.Floppy)
  addPlayerInventorySlots(8, 84)
}
