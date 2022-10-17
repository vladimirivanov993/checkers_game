import java.util.Iterator;
import java.util.Vector;

public class Static {

    protected static String b_e = "|⛂ ";
    protected static String w_e = "|⛀ ";
    protected static String b_d = "|⛃ ";
    protected static String w_d = "|⛁ ";
    protected static String sep_step = "-";
    protected static String sp2 = "|  ";
    protected static String a_h = "abcdefgh";
    protected static String s1_8 = "12345678";
    protected static int y_white_limit = 0;
    protected static int max_val_coord = Math.min(a_h.length(), s1_8.length()) - 1;
    protected static int y_black_limit = max_val_coord;

    /** переводит буквы и цифры в цифровое представление
     * например, g3 в 6,3  */
    public static String toDigital(String step) {
        step = step.replaceAll("a", "1,");
        step = step.replaceAll("b", "2,");
        step = step.replaceAll("c", "3,");
        step = step.replaceAll("d", "4,");
        step = step.replaceAll("e", "5,");
        step = step.replaceAll("f", "6,");
        step = step.replaceAll("g", "7,");
        step = step.replaceAll("h", "8,");
        return step;
    }

    public static Vector<Vector<PosAndValue>> get_massive_figure(Vector<Way> v_way, Board board) {
        Iterator<Way> i = v_way.iterator();
        Vector<Vector<PosAndValue>> vv_pnv = new Vector<Vector<PosAndValue>>();
        while(i.hasNext()) {
            Vector<PosAndValue> v_pnv = i.next().get_massive_figure(board);
            vv_pnv.add(v_pnv);
        }
        return vv_pnv;
    }

    public static void vv_pnv_toPrint(Vector<Vector<PosAndValue>> vv_pnv) {
        Iterator<Vector<PosAndValue>> i = vv_pnv.iterator();
        while(i.hasNext()){
            Iterator<PosAndValue> j = i.next().iterator();
            while(j.hasNext()) {
                j.next().toPrint();
            }
        }
    }

    public static boolean isNotClearField(String value) {
        if(value != sp2) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isSimpleCheckers(String chekers) {
        if(chekers == w_e || chekers == b_e) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean step_limit_board(String s) {
        String[] sm = s.split(Static.sep_step);
        Boolean b = false;
        if(sm.length == 2) {
            String s0 = sm[0];
            String s1 = sm[1];
            if(s0.length() == 2 && s1.length() == 2) {
                String s0_sub01 = s0.substring(0, 1);
                String s0_sub12 = s0.substring(1, 2);
                String s1_sub01 = s1.substring(0, 1);
                String s1_sub12 = s1.substring(1, 2);
                int i1 = a_h.indexOf(s0_sub01);
                int i2 = s1_8.indexOf(s0_sub12);
                int i3 = a_h.indexOf(s1_sub01);
                int i4 = s1_8.indexOf(s1_sub12);
                if(i1 >= 0 && i2 >= 0 && i3  >= 0 && i4 >= 0){
                    b = true;
                }
            }
        }
        return b;
    }

    public static String get_abc_from_x(int x) {
        if(0 <= x && x <= max_val_coord) {
            return a_h.substring(x, x+1);
        } else {
            return "not 0 <= x && x <= max_val_coord";
        }
    }

    public static String get_num_from_y(int y) {
        Integer i;
        if(0 <= y && y <= max_val_coord) {
            i = max_val_coord + 1 - y;
            return i.toString();
        } else {
            return "  not 0 <= x && x <= max_val_coord";
        }
    }

    public static String VectorWayToPrint(Vector<Way> vw) {
        Iterator<Way> i = vw.iterator();
        String s = "";
        while(i.hasNext()) {
            s=s.concat(i.next().toStep()+",");
        }
        return s;

    }

    /** пустая ли ячейка. на вход значение позиции доски*/
    public static Boolean is_clear(String checkers) {
        if (checkers == sp2) {
            return true;
        } else {
            return false;
        }
    }

}