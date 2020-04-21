package Enums;

import java.awt.event.KeyEvent;

public enum ClickType {
	Build, Remove;
	
	public static ClickType getType(int keyCode) {
		switch (keyCode) {
			case KeyEvent.VK_1: return ClickType.Build;
			case KeyEvent.VK_2: return ClickType.Build;
			case KeyEvent.VK_3: return ClickType.Build;
			case KeyEvent.VK_4: return ClickType.Remove;
			default: return null;
		}
	}
}
