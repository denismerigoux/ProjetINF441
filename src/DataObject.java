
public class DataObject extends LinkObject {
	public LinkObject L;
	public LinkObject R;
	public LinkObject U;
	public LinkObject D;
	public final ColumnObject C;//La colonne a laquelle appartient l'objet ne change pas
	
	public DataObject(LinkObject L, LinkObject R, LinkObject U, LinkObject D, ColumnObject C) {
		this.L=L;
		this.R=R;
		this.U=U;
		this.D=D;
		this.C=C;
	}
}
