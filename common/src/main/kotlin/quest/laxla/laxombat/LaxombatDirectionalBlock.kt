package quest.laxla.laxombat

import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.DirectionalBlock
import net.minecraft.world.level.block.Mirror
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition

class LaxombatDirectionalBlock(properties: Properties) : DirectionalBlock(properties) {
	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		super.createBlockStateDefinition(builder)
		builder.add(FACING)
	}

	override fun rotate(state: BlockState, rotation: Rotation): BlockState {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)))
	}

	override fun mirror(state: BlockState, mirror: Mirror): BlockState {
		return state.rotate(mirror.getRotation(state.getValue(FACING)))
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
		return defaultBlockState().setValue(FACING, context.clickedFace.let {
			if (context.player?.isShiftKeyDown == true) it.opposite else it
		})
	}
}