package li.cil.oc.common.item

import li.cil.oc.OpenComputers
import li.cil.oc.common.GuiType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.{ActionResult, ActionResultType, Hand}
import net.minecraft.world.World

class DiskDriveMountable(val parent: Delegator) extends traits.Delegate {
  override def maxStackSize = 1

  override def use(stack: ItemStack, world: World, player: PlayerEntity) = {
    // Open the GUI immediately on the client, too, to avoid the player
    // changing the current slot before it actually opens, which can lead to
    // desynchronization of the player inventory.
    OpenComputers.openGui(player, GuiType.DiskDriveMountable.id, world, 0, 0, 0)
    player.swing(Hand.MAIN_HAND)
    new ActionResult(ActionResultType.sidedSuccess(world.isClientSide), stack)
  }
}
