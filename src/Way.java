import java.util.Iterator;
import java.util.Vector;

public class Way {

    Coordinates a;
    Coordinates b;
    Boolean allow_use_way_for_step;
    protected int quan_steps;
    protected String direction;
    protected char dir_y;
    protected char dir_x;
    protected int dy;
    protected int dy_norm;
    protected int dx;
    protected int dx_norm;

    public Way(PosAndValue pnv_from, PosAndValue pnv_to) { //16.03
        a = new Coordinates(pnv_from.get_x(), pnv_from.get_y());
        b = new Coordinates(pnv_to.get_x(), pnv_to.get_y());
        calc_direction();
    }

    /** создает путь из хода. вызывать только для корректного пути */
    public Way(String step) { //b4-f8
        step = Static.toDigital(step); //2,4-5,8
        String[] a_b = step.split(Static.sep_step); //{"2,4","5,8"}
        if(a_b.length == 2) {
            this.a = new Coordinates(a_b[0]);
            this.b = new Coordinates(a_b[1]);;
            calc_direction();
        }
    }

    public Way(Coordinates a, Coordinates b) {
        this.a = a;
        this.b = b;
        calc_direction();
    }

    public Coordinates get_first_coord(){
        return a;
    }

    public Coordinates get_last_coord(){
        return b;
    }

    private void calc_direction() {
        //вверх или вниз
        dx = b.get_x() - a.get_x();
        dy = b.get_y() - a.get_y();
        if (dx == 0) {
            dx_norm = 0;
        } else {
            dx_norm = dx / Math.abs(dx);
        }
        if (dy == 0) {
            dy_norm = 0;
        } else {
            dy_norm = dy / Math.abs(dy);
        }
        quan_steps = Math.min(Math.abs(dx), Math.abs(dy));
        if(Math.abs(dx) != Math.abs(dy)|| dx == 0 || dy == 0 ) {
            allow_use_way_for_step = false;
        } else {
            allow_use_way_for_step = true;
        }
        direction = toStep();
    }

    public int get_steps() {
        return quan_steps;//
    }

    public Boolean allow_use_way_for_step() {
        return allow_use_way_for_step;
    }

    /** построение списка позиций и значений  по пути*/
    public Vector<PosAndValue> get_massive_figure(Board board) {
        Vector<PosAndValue> s_mass = new Vector<PosAndValue>();
        int x;
        int y;
        String value = "";
        for(int i = 0;i <= quan_steps;i++) {
            x = a.get_x()+i*dx_norm;
            y = a.get_y()+i*dy_norm;
            value = board.get_cell_x_y(x, y);
            PosAndValue pv = new PosAndValue(value, x, y);
            s_mass.add(pv);
        }
        return s_mass;
    }

    public boolean accessible_dir(String checkers) {
        if(checkers == Static.w_d || checkers == Static.b_d) {
            return true;
        }
        if(checkers != Static.w_e && checkers != Static.b_e && checkers != Static.sp2) {
            return true;
        } else {
            if(checkers == Static.w_e && this.dy < 0 || checkers == Static.b_e && this.dy > 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    public Vector<Way> subset_fireway(Board board) {
        Vector<PosAndValue> s_mass = new Vector<PosAndValue>();
        int x;
        int y;
        Way way_candidate;
        Coordinates second_coord;
        Vector<Way> vw = new Vector<Way>();
        for(int i = 1;i <= quan_steps;i++) {//нулевой ход не ход
            x = a.get_x()+i*dx_norm;
            y = a.get_y()+i*dy_norm;
            second_coord = new Coordinates(x, y);
            way_candidate = new Way(a, second_coord);
            s_mass = way_candidate.get_massive_figure(board);
            if(board.possible_attack_stat(s_mass)) {
                vw.add(way_candidate);
            };
        }
        return vw;
    }

    public Boolean way_is_clear(Board board) {//предполагается анализ значений, начиная со второй.
        Vector<PosAndValue> vpnv = get_massive_figure(board);
        return board.is_clear_move(vpnv);
    }

    public Vector<Way> subset_freeway(Board board) {
        int x;
        int y;
        Way way_candidate;
        Coordinates second_coord;
        Vector<Way> vw = new Vector<Way>();
        for(int i = 1;i <= quan_steps;i++) {//нулевой ход не ход
            x = a.get_x()+i*dx_norm;
            y = a.get_y()+i*dy_norm;
            second_coord = new Coordinates(x, y);
            way_candidate = new Way(a, second_coord);
            if(way_candidate.way_is_clear(board) && way_candidate.accessible_dir(board.get_cell_x_y(a))) {
                vw.add(way_candidate);
            } else {
                break;
            }
        }
        return vw;
    }

    public String toStep() {
        String s1 = Static.get_abc_from_x(a.get_x());
        String s2 = Static.get_num_from_y(a.get_y());
        String s3 = Static.get_abc_from_x(b.get_x());
        String s4 = Static.get_num_from_y(b.get_y());
        return s1 + s2 + Static.sep_step + s3 + s4;
    }

    public Boolean allowed_by_vector_attack(Vector<Way> possible_ways_second_attack) {
        Iterator<Way> i = possible_ways_second_attack.iterator();
        String step_way = toStep();
        while(i.hasNext()) {
            String step_vector = i.next().toStep();
            if(step_way.equals(step_vector)) {
                return true; //наш путь среди допустимых
            }
            return false; //если не встретил путь среди допустимых
        }
        return true; //если пуст, то считаем, что ограничений нет(это не следующий огневой ход то же фигурой)
    }
}
