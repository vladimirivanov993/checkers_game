import java.util.Vector;

public class Coordinates {
    int y;
    int x;

    public Coordinates(String position) {
        calc_y(position);
        calc_x(position);
    }

    public Coordinates(int x, int y) {
        // TODO Auto-generated constructor stub
        this.y = y;
        this.x = x;
    }

    /**
     * @param step_part на вход числовые координаты, разделенные запятой
     * @return возвращает позицию буквенной координаты (столбец у строки, внутренний массив)
     * перемещается влево-вправо на доске
     * пример данных:   1,6
     */
    public int calc_x(String step_part) {
        String[] x_y = step_part.split(",");
        x = Integer.parseInt(x_y[0]);
        x--;
        return x;
    }

    /** цифровая координата - строка
     *  сначала адресуемся к строке - внешний массив
     *  перемещаемся вверх-вниз
     *  */
    public int calc_y(String step_part) {
        String[] x_y = step_part.split(",");
        y = Integer.parseInt(x_y[1]);
        y = Static.max_val_coord + 1 - y;
        return y;
    }

    public int get_x() {
        return x;
    }

    public int get_y() {
        return y;
    }


    public Vector<Way> getWays(){
        int x_new = this.x;
        int y_new = this.y;
        int x_old = this.x;
        int y_old = this.y;
        Way way;
        Vector<Way> v_way = new Vector<Way>();
        for(int dx = -1;dx <2; dx = dx+2) {
            for(int dy = -1; dy < 2; dy = dy+2) {
                while(0 <= x_new && x_new <= Static.max_val_coord &&
                        0 <= y_new && y_new <= Static.max_val_coord) {
                    x_old = x_new;
                    y_old = y_new;
                    x_new = x_new + dx;
                    y_new = y_new + dy;
                }
                if(!( 0 <= x_new && x_new <= Static.max_val_coord &&
                        0 <= y_new && y_new <= Static.max_val_coord)) {
                    x_new = x_old;
                    y_new = y_old;
                }
                if(x != x_new || y != y_new) {
                    way = new Way(new Coordinates(x,y), new Coordinates(x_new,y_new));
                    v_way.add(way);
                }

                x_new = this.x;
                y_new = this.y;
            }
        }
        return v_way;

    }

    public Boolean equal(Coordinates c2) {
        if(x == c2.get_x() && y == c2.get_y()) {
            return true;
        } else {
            return false;
        }
    }

}
