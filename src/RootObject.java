
public class RootObject extends LinkObject {
	public LinkObject L;
	public LinkObject R;
	public LinkObject U;
	public LinkObject D;
	public final ColumnObject C;//La colonne a laquelle appartient l'objet ne change pas
	
	public RootObject() {
		//on commence par construire le RootObject, au depart il est tout seul
		this.L= this;
		this.R = this;
		this.U = null;
		this.D = null;
		this.C = null;
	}
}
