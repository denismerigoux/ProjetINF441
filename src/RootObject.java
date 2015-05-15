
public class RootObject extends LinkObject {
	
	public RootObject() {
		//on commence par construire le RootObject, au depart il est tout seul
		this.L= this;
		this.R = this;
		this.U = null;
		this.D = null;
		this.C = null;
	}
	
	@Override
	public String toString(){
		String s="root :: ";
		s+="root ;  L : "+((ColumnObject)(this.L)).N+" R : "+((ColumnObject)this.R).N;
		return s;

	}
}
