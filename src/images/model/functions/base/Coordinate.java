package images.model.functions.base;

import java.util.List;

/**
 * Coordinate class.
 * 
 * @author Sunwu Choi
 *
 */
public class Coordinate {
  private int row;
  private int col;

  /**
   * Constructor for the coordinate.
   * 
   * @param row row of the pixel
   * @param col column of the pixel
   */
  public Coordinate(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public int getCol() {
    return this.col;
  }

  public int getRow() {
    return this.row;
  }

  /**
   * Get the closest index of the pixel in the list.
   * 
   * @param list list of pixels to find the closest coordinate
   * @return the closest index of the pixel in the list
   */
  public int closest(List<Coordinate> list) {
    if (list == null) {
      throw new IllegalArgumentException("Null list inserted");
    }

    int index = 0;
    double distance;
    double min = distanceTo(list.get(0));

    for (int i = 1; i < list.size(); i++) {
      distance = distanceTo(list.get(i));
      if (distance < min) {
        index = i;
        min = distance;
      }
    }
    return index;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Coordinate)) {
      return false;
    }
    Coordinate other = (Coordinate) obj;
    return (this.row == other.row && this.col == other.col);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  private double distanceTo(Coordinate other) {
    double xDiff = Math.pow((this.row - other.getRow()), 2);
    double yDiff = Math.pow((this.col - other.getCol()), 2);
    return Math.sqrt(xDiff + yDiff);
  }
}
