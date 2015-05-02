
public abstract class LinkObject {
	//La classe abstraite LinkObject sera utilisée par les 3 types d'objetcs qui interviennent dans l'algorithme :
	//* les DataObjects qui sont les 1 dans la matrice
	//* les ColumnObjects qui sont les en-têtes de colonne
	//* les RootObject qui sont uniques
	//Les champs sont publics parce que l'algorithme va directement les modifier : c'est comme ça que marchent
	//les liens dansants.
	public LinkObject L;//maillon à gauche
	public LinkObject R;//maillon à droite
	public LinkObject U;//maillon en haut
	public LinkObject D;//maillon en bas
	public ColumnObject C;//colonne à laquelle le maillon appartient
}
