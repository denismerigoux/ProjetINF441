

public class TestPiece {

	public static void main(String[] args) {
		//int[][] tab={{0,0,1},{0,0,1},{1,1,1}};
		//int [][] tab2={{0,1,1},{0,0,1},{1,1,1}};
		//int[][] petittab={{1,0},{1,0},{1,1}};
		//int[][] line={{1},{1},{1},{1},{1},{1}};
		//Piece r=new Piece(petittab);
		//Piece p=new Piece(3, tab);
		//Piece q=new Piece(3,tab2);
		int[][] L={{1,0,0,0},{1,0,0,0},{1,0,0,0},{1,1,1,0}};
		int[][] poi={{1}};
	//	System.out.println(q);
		//System.out.println(q.rotation());
		//System.out.println(q.rotation().rotation());
		//System.out.println(q.rotation().rotation().rotation());
		//System.out.println(q.rotation().rotation().rotation().rotation());
		//System.out.println(q.mirrorH());
		//System.out.println(q.mirrorV());
		//System.out.println(p);
		//System.out.println(p.equals(q));
		//System.out.println(r);
		//System.out.println(r.mirrorH());
		
		//Piece ligne=new Piece(line);
		//System.out.println(ligne);
		//System.out.println(ligne.numberOfDifferentTransformations());
		
		//System.out.println(p);
		//System.out.println(p.rotation());
		
		//System.out.println(p.mirrorH());
		//System.out.println(p.mirrorV());
		//System.out.println(p.mirrorH().mirrorV());
		//System.out.println(p.mirrorV().mirrorH());
		Piece point=new Piece(poi);
		Piece point2=new Piece(poi);
		System.out.println(point);
		System.out.println(point.numberOfDifferentTransformations());
		Piece l=new Piece(L);
		System.out.println(l);
		System.out.println(l.numberOfDifferentTransformations());
		//for(Piece pp : l.getListOfTransformations())
		//	System.out.println(pp);
		int[][] trou={{0,0,0},{0,2,0},{0,0,0}};
		Grille g=new Grille(3, 3, trou);
		Piece[] pieces=new Piece[2];
		pieces[0]=point;
		pieces[1]=point2;
		DebugUtils.affTab(PavageParsing.generateLinesFromPieces(pieces, g));
		g.getCoveredPositions(l, 1, 2);
		
	}
}
