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
        return getMapDirection(NORTHWEST, WEST, SOUTHWEST, SOUTH, SOUTHEAST, EAST, NORTHEAST, NORTH);

    }

    public MapDirection previous() {
        return getMapDirection(NORTHEAST, NORTH, NORTHWEST, WEST, SOUTHWEST, SOUTH, SOUTHEAST, EAST);
    }

    private MapDirection getMapDirection(MapDirection northeast, MapDirection north, MapDirection northwest, MapDirection west, MapDirection southwest, MapDirection south, MapDirection southeast, MapDirection east) {
        switch (this) {
            case NORTH:
                return northeast;
            case NORTHWEST:
                return north;
            case WEST:
                return northwest;
            case SOUTHWEST:
                return west;
            case SOUTH:
                return southwest;
            case SOUTHEAST:
                return south;
            case EAST:
                return southeast;
            case NORTHEAST:
                return east;
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