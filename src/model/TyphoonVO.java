package model;


public class TyphoonVO {
    private int typ_seq;
    private String typ_en;
    private String tm_st;
    private String tm_ed;
    private int typ_ps;
    private int typ_ws;
    private String typ_name;
    private int eff;

    // Getters and Setters
    public int getTyp_seq() {
        return typ_seq;
    }

    public void setTyp_seq(int typ_seq) {
        this.typ_seq = typ_seq;
    }

    public String getTyp_en() {
        return typ_en;
    }

    public void setTyp_en(String typ_en) {
        this.typ_en = typ_en;
    }
    
    public String getTm_st() {
		return tm_st;
	}

	public void setTm_st(String tm_st) {
		this.tm_st = tm_st;
	}

	public String getTm_ed() {
        return tm_ed;
    }

    public void setTm_ed(String tm_ed) {
        this.tm_ed = tm_ed;
    }

    public int getTyp_ps() {
        return typ_ps;
    }

    public void setTyp_ps(int typ_ps) {
        this.typ_ps = typ_ps;
    }

    public int getTyp_ws() {
        return typ_ws;
    }

    public void setTyp_ws(int typ_ws) {
        this.typ_ws = typ_ws;
    }

    public String getTyp_name() {
        return typ_name;
    }

    public void setTyp_name(String typ_name) {
        this.typ_name = typ_name;
    }

    public int getEff() {
        return eff;
    }

    public void setEff(int eff) {
        this.eff = eff;
    }


	@Override
	public String toString() {
		return String.format(
			    "%-5s %-15s %-12s %-12s %-10s %-10s %-15s %-12s",
			    typ_seq, typ_en, tm_st, tm_ed, typ_ps, typ_ws, typ_name, eff
			);
	}


}
