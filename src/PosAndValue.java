public class PosAndValue {

    String value;
    int x;
    int y;
    public boolean flag_not_use;

    public PosAndValue(Board board, int x, int y) {
        value = board.get_cell_x_y(x, y);
        this.x = x;
        this.y = y;
        flag_not_use = false;
    }

    public PosAndValue(String value, int x,int y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public String get_value() {
        return value;
    }

    public int get_x() {
        return x;
    }

    public int get_y() {
        return y;
    }

    public void toPrint() {
        System.out.println("["+x+","+y+"]:"+value+";");
    }

    public String toNameCell() {
        return Static.get_abc_from_x(x) + Static.get_num_from_y(y);
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void clear_value() {
        flag_not_use = true;
    }

    public Boolean active() {
        return !flag_not_use;
    }

    public Boolean is_space_cell() {
        return ( value == Static.sp2 );
    }

    public Coordinates toCoordinates() {
        return new Coordinates(get_x(), get_y());
    }
}
