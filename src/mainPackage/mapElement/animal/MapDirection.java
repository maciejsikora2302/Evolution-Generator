package mainPackage.mapElement.animal;

import mainPackage.main.Vector2d;

public enum MapDirection {
    NORTH,
    NORTHWEST,
    WEST,
    SOUTHWEST,
    SOUTH,
    SOUTHEAST,
    EAST,
    NORTHEAST,

    SKIP;

    public String toString() {
        switch (this) {
            case NORTH:
                return "North";
            case NORTHWEST:
                return "North-West";
            case WEST:
                return "West";
            case SOUTHWEST:
                return "South-West";
            case SOUTH:
                return "South";
            case SOUTHEAST:
                return "South-East";
            case EAST:
                return "East";
            case NORTHEAST:
                return "North-East";

            default:
                return "";
        }
    }

    public MapDirection next() {
        switch (this) {
            case NORTH:
                return MapDirection.NORTHWEST;
            case NORTHWEST:
                return MapDirection.WEST;
            case WEST:
                return MapDirection.SOUTHWEST;
            case SOUTHWEST:
                return MapDirection.SOUTH;
            case SOUTH:
                return MapDirection.SOUTHEAST;
            case SOUTHEAST:
                return MapDirection.EAST;
            case EAST:
                return MapDirection.NORTHEAST;
            case NORTHEAST:
                return MapDirection.NORTH;
            default:
                return SKIP;
        }
    }

    public Vector2d toUnitVector() {
        switch (this) {
            case NORTH:
                return new Vector2d(0, 1);
            case NORTHWEST:
                return new Vector2d(1, 1);
            case WEST:
                return new Vector2d(1, 0);
            case SOUTHWEST:
                return new Vector2d(1, -1);
            case SOUTH:
                return new Vector2d(0, -1);
            case SOUTHEAST:
                return new Vector2d(-1, -1);
            case EAST:
                return new Vector2d(-1, 0);
            case NORTHEAST:
                return new Vector2d(-1, 1);
            default:
                return new Vector2d(0, 0);
        }
    }
}