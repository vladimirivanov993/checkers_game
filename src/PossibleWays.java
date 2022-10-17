import java.util.Iterator;
import java.util.Vector;

public class  PossibleWays{
public Coordinates a;
public Vector<Way> freeway = new Vector<Way>();
public Vector<Way> fireway = new Vector<Way>();
        String s_freeway = "";
        String s_fireway = "";
public PossibleWays(Coordinates a2, Vector<Way> subset_freeway, Vector<Way> subset_fireway) {
        a = a2;
        freeway = subset_freeway;
        fireway = subset_fireway;
        get_string_represent();
        }


public PossibleWays(Coordinates a, Board board) {
        this.a = a;
        if(a == null) {
        freeway.clear();
        fireway.clear();
        return;
        }
        Vector<Way> vw = a.getWays();
        Iterator<Way> iw = vw.iterator();
        Way way_temp;
        while(iw.hasNext()){
        way_temp = iw.next();
        freeway.addAll(way_temp.subset_freeway(board));
        fireway.addAll(way_temp.subset_fireway(board));
        }
        get_string_represent();
        }

private void get_string_represent() {
        Iterator<Way> i_fire = freeway.iterator();
        while(i_fire.hasNext()) {
        s_fireway.concat(i_fire.next().toStep()+",");
        }
        Iterator<Way> i_free = freeway.iterator();
        while(i_free.hasNext()) {
        s_freeway.concat(i_free.next().toStep()+",");
        }
        }

public Coordinates get_coord() {
        return a;
        }

public Vector<Way> get_way_attack() {
        return fireway;
        }

public Vector<Way> get_way_free() {
        return freeway;
        }


        }
