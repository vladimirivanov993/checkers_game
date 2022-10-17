

        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.PrintWriter;
        import java.util.Iterator;
        import java.util.Scanner;
        import java.util.Vector;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {



/** запуск примера игры. можно расскоментировать запуск метода, и пример истории загрузится */
//     run_sample_history();

/** запуск игры за белых. можно расскоментировать запуск метода */
//     exe_new_chekers_game(true); //точка старта игры

        exe_interactive_game();
    }

    public static void run_sample_history() {
        /** блок инициализации игровой доски */
        String s = "";
        String b_e = Static.b_e;
        String w_e = Static.w_e;
        String sp2 = Static.sp2;
        String[][] board_table = {
                {sp2,sp2,sp2,b_e,sp2,sp2,sp2,sp2},
                {sp2,sp2,b_e,sp2,b_e,sp2,b_e,sp2},
                {sp2,sp2,sp2,sp2,sp2,sp2,sp2,sp2},
                {sp2,sp2,sp2,sp2,sp2,sp2,b_e,sp2},
                {sp2,sp2,sp2,sp2,sp2,sp2,sp2,sp2},
                {sp2,sp2,sp2,sp2,b_e,sp2,sp2,sp2},
                {sp2,sp2,sp2,w_e,sp2,sp2,sp2,sp2},
                {sp2,sp2,sp2,sp2,sp2,sp2,sp2,sp2} };
        Board board = new Board(board_table);
        s =  board.boardToConsole();
        System.out.printf(s);
        String hist_steps = "d2-f4,f4-h6,h6-f8,f8-d6,d6-b8,d8-e7,b8-a7,e7-f6,a7-b6,f6-g5,b6-a7,g5-h4,a7-g1,h4-g3,g1-h2,g3-f2,h2-g1,f2-e1,g1-e3,e1-c3,e3-b6,c3-e1,b6-a7,e1-g3,a7-g1,g3-e1,g1-f2,e1-g3";
        run_history(hist_steps, board);
    }

    public static void exe_new_chekers_party() throws FileNotFoundException {
        String b_e = Static.b_e;
        String w_e = Static.w_e;
        String sp2 = Static.sp2;

        String[][] board_table = {
                {sp2,b_e,sp2,b_e,sp2,b_e,sp2,b_e},
                {b_e,sp2,b_e,sp2,b_e,sp2,b_e,sp2},
                {sp2,b_e,sp2,b_e,sp2,b_e,sp2,b_e},
                {sp2,sp2,sp2,sp2,sp2,sp2,sp2,sp2},
                {sp2,sp2,sp2,sp2,sp2,sp2,sp2,sp2},
                {w_e,sp2,w_e,sp2,w_e,sp2,w_e,sp2},
                {sp2,w_e,sp2,w_e,sp2,w_e,sp2,w_e},
                {w_e,sp2,w_e,sp2,w_e,sp2,w_e,sp2} };

        Board board = new Board(board_table);
        do {
            board.clear_vpw_fire_and_free();
            board.define_vector_any_ways();
            try_make_step_simple(get_random_step_new(board), board);
        } while (!board.there_is_no_steps());
        String s_history = board.printHistoryLine();
        PrintWriter pw = new PrintWriter(new File("C:\\Users\\admin\\Downloads\\checkers_home\\file_chess.txt"));
        pw.write(s_history+"\n");
        pw.close();
        System.out.println(s_history);
        board = new Board(board_table);
    }

    /** блок выполнения заданной последовательности шагов */
    public static void run_history(String hist_steps, Board board) {
        Vector<String> vs = new Vector<String>();
        String[] vs_s = hist_steps.split(",");
        for(int i = 0; i<vs_s.length; i++) {
            vs.add(vs_s[i]);
        }
        Iterator<String> in_v = vs.iterator();
        String step = new String();
        while(in_v.hasNext()) {////
            System.out.print("Input your step: ");
            step = in_v.next();
            try_make_step_simple(step, board);
        }
        System.out.println(board.printHistoryLine());
    }

    public static String get_random_step_new(Board board){

        Way attack = null;
        Way simple = null;
        String step = "";
        if(Math.random() < 0.9) {
            if(board.vpw_fire.size() > 0) {
                Integer i1 = (int) (Math.random() * (board.vpw_fire.size() + 1));
                i1 = Math.min(i1, board.vpw_fire.size() - 1);
                attack = board.vpw_fire.elementAt(i1);
            }
            if(board.vpw_free.size() > 0) {
                Integer i2 = (int) (Math.random() * (board.vpw_free.size() + 1));
                i2 = Math.min(i2, board.vpw_free.size() - 1);
                simple  = board.vpw_free.elementAt(i2);
            }
        } else {
            Integer i1 = (int) (Math.random() * (board.vpw_both.size() + 1));
            i1 = Math.min(i1, board.vpw_both.size() - 1);
            i1 = Math.max(i1, 0);
            if(board.vpw_both.size() > 0) {
                attack = board.vpw_both.elementAt(i1);
            }

        }

        if(attack != null) {
            step = attack.toStep();
        } else {
            if(simple != null) {
                step = simple.toStep();
            } else {
                step = "";
            }
        }
        return step;
    }


    public static Boolean try_make_step_simple(String step, Board board){

        String s;
        if(Static.step_limit_board(step) && board.is_coorrect_fire_restriction(step)){
            board.make_step(step);
            board.check_double_attack(step);
            if(board.is_need_change_motion()) {
                board.change_motion();
            }
            if(board.step_was_succeess()) {
                System.out.printf("You made step: "+step+"\n");
                s = board.boardToConsole();
                System.out.printf(s);
                return true;
            } else {
                System.out.printf("You attemp for step was error: Step "+step+"is forbidden\n");
                return false;
            }
        }
        System.out.printf("You attemp for step was error: Step "+step+" is forbidden\n");
        return false;
    } //попытка априори неразрешенного хода не приводит к изменению на доске - этот ход игнорируется


    public static void exe_new_chekers_game(Boolean user_motion) throws FileNotFoundException {
        String b_e = Static.b_e;
        String w_e = Static.w_e;
        String sp2 = Static.sp2;

        String[][] board_table = {
                {sp2,b_e,sp2,b_e,sp2,b_e,sp2,b_e},
                {b_e,sp2,b_e,sp2,b_e,sp2,b_e,sp2},
                {sp2,b_e,sp2,b_e,sp2,b_e,sp2,b_e},
                {sp2,sp2,sp2,sp2,sp2,sp2,sp2,sp2},
                {sp2,sp2,sp2,sp2,sp2,sp2,sp2,sp2},
                {w_e,sp2,w_e,sp2,w_e,sp2,w_e,sp2},
                {sp2,w_e,sp2,w_e,sp2,w_e,sp2,w_e},
                {w_e,sp2,w_e,sp2,w_e,sp2,w_e,sp2} };

        Board board = new Board(board_table);
        String user_step = "";
        System.out.println(board.boardToConsole());
        Scanner in = new Scanner(System.in);
        do  {//пока есть возможность совершать ходы
            board.clear_vpw_fire_and_free();
            board.define_vector_any_ways();
            if(board.motion == user_motion) { // ход делает человек
                System.out.println("Make_your_step:");
                user_step = in.next();
                try_make_step_simple(user_step, board);
            } else { //ход делает компьютер
                System.out.printf("Computer step: ");
                try_make_step_simple(get_random_step_new(board), board);
            }
        } while((!board.there_is_no_steps()));
        in.close();
        String s_history = board.printHistoryLine();
        PrintWriter pw = new PrintWriter(new File("file_checkers.txt"));
        pw.write(s_history+"\n");
        pw.close();
        System.out.println(s_history);
    }


    public static void exe_interactive_game() throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.println("input_color: B, W");
        String user_color = in.next();
        if(user_color.equals("W")) {
            exe_new_chekers_game(true);
        } else {
            if((user_color.equals("B"))) {
                exe_new_chekers_game(false);
            }
        }
        in.close();
    }

}
