import java.util.Iterator;
import java.util.Vector;

public class SetPossibleWays {
    Vector<PossibleWays> vpw =  new Vector<PossibleWays>();
    Vector<Way> vpw_fire =  new Vector<Way>();
    Vector<Way> vpw_free =  new Vector<Way>();


    public SetPossibleWays(Board board) {
        Vector<PosAndValue> vpnv = board.get_set_current_army();
        Iterator<PosAndValue> ipv = vpnv.iterator();
        PosAndValue pv;
        while (ipv.hasNext()) {
            pv = ipv.next();
            Coordinates a = pv.toCoordinates();
            PossibleWays pw = new PossibleWays(a, board);
            vpw_free.addAll(pw.get_way_free());
            vpw_fire.addAll(pw.get_way_attack());
            vpw.add(pw);
        }
    }

    public String toPrintFireAndFree() {
        return "vpw_fire:"+ Static.VectorWayToPrint(vpw_fire) + "\n" +
                "vpw_free:"+ Static.VectorWayToPrint(vpw_free) + "\n";
    }

    public Vector<Way> get_way_attak(Coordinates coord){
        Iterator<PossibleWays> ipw = vpw.iterator();
        PossibleWays pw;
        Vector<Way> vw = new Vector<Way>();
        while(ipw.hasNext()) {
            pw = ipw.next();
            if(coord.equal(pw.get_coord())){
                return pw.get_way_attack();
            }
        }
        return vw;
    }

    public Vector<Way> get_way_free(Coordinates coord){
        Iterator<PossibleWays> ipw = vpw.iterator();
        PossibleWays pw;
        Vector<Way> vw = new Vector<Way>();
        while(ipw.hasNext()) {
            pw = ipw.next();
            if(coord.equal(pw.get_coord())){
                return pw.get_way_free();
            }
        }
        return vw;
    }

    public Vector<Way> get_vpw_free(){
        return vpw_free;
    }

    public Vector<Way> get_vpw_fire(){
        return vpw_fire;
    }

    public Vector<PossibleWays> get_vpw(){
        return vpw;
    }

}
