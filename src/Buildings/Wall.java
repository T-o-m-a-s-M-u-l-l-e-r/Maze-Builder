package Buildings;

public class Wall extends Building {

	public Wall(int x, int y) {
		super(x, y);
	}
	
	@Override
	public BuildType getBuildingType() {
		return BuildType.Wall;
	}

}
