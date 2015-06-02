import javax.swing.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class Fenetre extends JFrame
{
	Grille grid;
	DataObject[] solution;
	
	public Fenetre(final Grille grid, final DataObject[] solution)
	{
		setTitle("Pavage solution");
		setSize(600, 850);
		this.grid=grid;
		this.solution=solution;
		
		JPanel Panneau = new JPanel(){
			public void paintComponent(Graphics g)
			{
				//System.out.println(solution.length+" lignes");
				super.paintComponent(g);
				setBackground(Color.white);
				
				int nbOfGoodColumns=grid.numberOfValidCases();
				for(int i=0;i<solution.length;i++){
					DataObject first=solution[i];
					DataObject o=first;
					double hue = Math.random();
					int rgb = Color.HSBtoRGB((float)hue,(float)0.5,(float)0.5);
					Color c = new Color(rgb);
					g.setColor(c);
							do{
								int colnb=o.C.N-1;
								
								if(colnb<nbOfGoodColumns){
									int[] coords=grid.convertToTwoCoord(colnb);
									int y=coords[0];
									int x=coords[1];
									//System.out.println(x+","+y+" <- "+colnb);
									g.fillRect(50+50*x, 50+ 50*y, 50,50);
								}
								o=(DataObject)o.R;
							}
							while(o!=first);
				}
			
				
			}
		};
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		Panneau.setBackground(Color.gray);
		this.setVisible(true);
		this.getContentPane().add(Panneau);
	}
}
