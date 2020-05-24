package Enums;

import java.awt.event.KeyEvent;

public enum ClickType {
	Build, Remove;
	
	public static ClickType getType(int keyCode) {
		switch (keyCode) {
			case KeyEvent.VK_1:
			case KeyEvent.VK_2:
			case KeyEvent.VK_3: return ClickType.Build;
			case KeyEvent.VK_4: return ClickType.Remove;
			default: return null;
		}
	}
	
	public static int getCost(ClickType type) {
		switch(type) {
		case Remove: return 5;
		default: return 0;
		}
	}
}
