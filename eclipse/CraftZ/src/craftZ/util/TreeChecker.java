package craftZ.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class TreeChecker {
	
	public boolean isTree(Block block) {

		List<Block> logList = new ArrayList<Block>();
		int i = 0;
		
		while (true) {
			
			Block above = block.getRelative(0, i, 0);
			if (above == null) {
				break;
			}
			
			if (above.getType() == Material.LOG) {
				logList.add(above);
			} else if (above.getType() != Material.LEAVES) {
				return false;
			} else {
				return true;
			}
			
			i++;
			
		}

		return false;
		
	}
	
}
