//для анализа ходов и разного представления в данных
public class  Step{
        String step;
        Way w;
        Coordinates a;
        Coordinates b;
        Boolean step_is_fire = false; //этот шаг был атакой. если не помечен, не атака
        Boolean flag_initial = true;

public Step(String s) {
        if(s != "") {
        w = new Way(s);
        a = w.get_first_coord();
        b = w.get_last_coord();
        step = w.toStep();
        } else {
        flag_initial = false;
        }
        }

public Boolean isInitial() {
        return flag_initial;
        }

public Boolean thisStepWasAttack() {
        return step_is_fire;
        }

public void markStepAsAttack() {
        step_is_fire = true;
        }

public Coordinates getSecondPartStep() {
        return b;
        }

public String getNameStep() {
        return w.toStep();

        }
        }
